package com.douglas.hub.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.douglas.hub.config.GithubFeignConfig;
import com.douglas.hub.dto.github.RepositorioDTO;

@FeignClient(                                    //Configuração geral
    name = "github",                             //Define o nome lógico do cliente no contexto do Spring Boot.
    url = "${github.base-url}",                  //Lê a URL base dinamicamente do arquivo "application.yml"
    /**
     * Associa uma classe de configuração para este cliente especifico
     * onde costumam ser definidos interceptadores para enviar tokens de autenticação(Authorization: Bearer)
     * timeouts ou tratamento de erros(ErrorDecoder)
     */
    configuration = GithubFeignConfig.class      
)
public interface GithubClient {

    @GetMapping("/users/{usuario}/repos")       //Mapeia o caminho da requisição
    /**
     * List<RepositorioDTO>: O Jackson desserializa automaticamente o array JSON de 
     * resposta do GitHub em uma lista de objetos RepositorioDTO.
     */
    List<RepositorioDTO> buscarRepositorios(
        @PathVariable("usuario") String usuario,    //Substitui dinamicamente o trecho {usuario} da URL pela variavel informada
        /**
         * @RequestParam: converte os parametros do método em query parameters na URL, aplicando valores padrão caso não sejam
         * informados explicitamente
         */
        @RequestParam(name = "page", defaultValue = "1") int page,   
        @RequestParam(name = "per_page", defaultValue = "10") int per_page,
        @RequestParam(name = "sort", defaultValue = "update") String sort
    );

    // Faz a busca dos dados detalhados do perfil de um usuário
    @GetMapping("/users/{usuario}") 
    //Return Object: Ao usar Object, o Feing converte o JSON em uma estrutura genérica( Map<String, Object>)
    Object buscarPerfil(@PathVariable("usuario") String usuario);

}
