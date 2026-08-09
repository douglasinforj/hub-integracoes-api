package com.douglas.hub.client;

import com.douglas.hub.dto.EnderecoDTO;
import com.douglas.hub.exception.CepNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class ViaCepClientTest {

    private static final String BASE_URL = "https://viacep.com.br/ws/{cep}/json/";

    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private ViaCepClient client;

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        client = new ViaCepClient(restTemplate, BASE_URL);                 // URL passada direto
    }

    @Test
    void deveRetornarEnderecoQuandoCepValido() {
        mockServer.expect(requestTo("https://viacep.com.br/ws/01310100/json/"))
            .andRespond(withSuccess("""
                {
                  "cep": "01310-100",
                  "logradouro": "Avenida Paulista",
                  "bairro": "Bela Vista",
                  "localidade": "São Paulo",
                  "uf": "SP"
                }
                """, MediaType.APPLICATION_JSON));

        EnderecoDTO resultado = client.buscarEndereco("01310100");

        assertThat(resultado.localidade()).isEqualTo("São Paulo");
        assertThat(resultado.uf()).isEqualTo("SP");
        mockServer.verify();
    }

    @Test
    void deveLancarExcecaoQuandoCepInvalido() {
        mockServer.expect(requestTo("https://viacep.com.br/ws/00000000/json/"))
            .andRespond(withSuccess("""
                { "erro": true }
                """, MediaType.APPLICATION_JSON));

        assertThatThrownBy(() -> client.buscarEndereco("00000000"))
            .isInstanceOf(CepNotFoundException.class)
            .hasMessageContaining("00000000");
    }
}