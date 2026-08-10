package com.douglas.hub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.hub.client.WeatherClient;
import com.douglas.hub.dto.weather.ClimaDTO;

import reactor.core.publisher.Mono;


/**
 * @RestController Indica que a classe é um componente web REST e que todas as suas 
 * repostas serão serializadas diretamente no corpo da resposta HTTP (JSON PADRAO)
 */
@RestController                              
@RequestMapping("/clima")            //Define que tdas as urls gerenciadas por esta classe terão o prefixo /clima
public class ClimaController {

    private final WeatherClient weatherClient;

    public ClimaController(WeatherClient weatherClient) {     //Injeção via Construtor: Injeta o bean WeatherClient de forma imutável
        this.weatherClient = weatherClient;
    }

    /**
     * @GetMapping("/{cidade}"): Mapeia requisições HTTP GET no caminho /clima/{cidade} (ex: /clima/SaoPaulo).
     * @PathVariable String cidade: Extrai o valor enviado na URL e o atribui ao parâmetro cidade
     * Mono<ResponseEntity<ClimaDTO>>: Retorna um fluxo reativo que emitirá um objeto de resposta HTTP contendo o DTO de clima.
     */

    @GetMapping("/{cidade}")   //Mapeia requisições HTTP GET no caminho
    public Mono<ResponseEntity<ClimaDTO>> buscarClima(@PathVariable String cidade) {  
        /*
        .map(ResponseEntity::ok): Caso a busca no weatherClient ocorra com sucesso, 
        envolve o ClimaDTO retornado em um ResponseEntity com status HTTP 200 OK.
         */
        return weatherClient.buscarClima(cidade).map(ResponseEntity::ok)   
        // se der erro, retorna 404 com body vazio
        .onErrorReturn(ResponseEntity.notFound().build());
        /*
        .onErrorReturn(...):Intercepta qualquer exceção emitida pelo pipeline reativo (como a RuntimeException do 404 lançada no WebClient) 
        e retorna como fallback uma resposta HTTP 404 Not Found com corpo vazio, evitando que o erro se espalhe pela aplicação. 
        */
    }
}
