package com.douglas.hub.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class GithubClientTest {

    // porta aleatória — evita conflito com outros testes
    static WireMockServer wireMock = new WireMockServer(
        WireMockConfiguration.wireMockConfig().dynamicPort()
    );

    /**
     * é a forma correta de sobrescrever propriedades do application.yml em testes com @SpringBootTest
     */
    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        // usa a porta gerada dinamicamente
        registry.add("github.base-url", () -> "http://localhost:" + wireMock.port());
        registry.add("github.token", () -> "fake-token");
    }

    @Autowired
    GithubClient githubClient;


    /**
     * Inicia o mock do servidor HTTP uma única vez antes de rodar os testes 
     * da classe e o conecta ao cliente do WireMock (configureFor).
     */
    @BeforeAll
    static void start() {
        wireMock.start();
        // configura o cliente estático do WireMock para usar o servidor correto
        configureFor("localhost", wireMock.port());
    }

    /**
     * @AfterAll: Encerra o servidor ao final de todos os testes para liberar recursos de rede.
     */
    @AfterAll
    static void stop() { wireMock.stop(); }

    /*
    @BeforeEach: Executa wireMock.resetAll() para limpar as respostas simuladas (stubs) 
    criadas em cada teste, garantindo o isolamento total entre cenários.
    */
    @BeforeEach
    void resetMocks() {
        wireMock.resetAll(); // ✅ limpa stubs entre testes
    }

    @Test
    void deveRetornarListaDeRepositorios() {

        /**
         * Simulação de Respostas da API (stubFor)
         * Em vez de bater na API real do GitHub, o WireMock intercepta as requisições GET para caminhos específicos 
         * (como /users/douglas/repos) e devolve um JSON controlado com código 200 OK via Text Blocks (""").
         */
        wireMock.stubFor(get(urlPathEqualTo("/users/douglas/repos"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    [
                      {
                        "id": 1,
                        "name": "hub-integracoes-api",
                        "description": "Projeto de integrações",
                        "html_url": "https://github.com/douglas/hub-integracoes-api",
                        "stargazers_count": 5,
                        "language": "Java",
                        "private": false
                      }
                    ]
                """)));

        var repos = githubClient.buscarRepositorios("douglas", 1, 10, "updated");

        /**
         * O teste valida a desserialização do JSON em objetos Java (RepositorioDTO), 
         * garantindo que campos como quantidade de itens, nome do repositório e 
         * linguagem batem exatamente com o contrato esperado do client.
         */
        assertThat(repos).hasSize(1);
        assertThat(repos.get(0).name()).isEqualTo("hub-integracoes-api");
        assertThat(repos.get(0).language()).isEqualTo("Java");
    }

    @Test
    void deveRetornarListaVaziaQuandoUsuarioSemRepositorios() {
        wireMock.stubFor(get(urlPathEqualTo("/users/semrepos/repos"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("[]")));

        var repos = githubClient.buscarRepositorios("semrepos", 1, 10, "updated");

        assertThat(repos).isEmpty();
    }
}