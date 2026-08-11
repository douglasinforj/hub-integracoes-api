
package com.douglas.hub.client;

import com.douglas.hub.dto.weather.ClimaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WeatherClient {

    private final WebClient weatherWebClient;
    private final String apiKey;


    public WeatherClient(WebClient weatherWebClient, @Value("${weather.api-key}") String apiKey){
        this.weatherWebClient = weatherWebClient;
        this.apiKey = apiKey;
    }

    public Mono<ClimaDTO> buscarClima(String cidade) {
        return weatherWebClient
                .get()                                  //prepara a requisção para utilizar método get
                // path + query params
                .uri(uri -> uri                         //COnstroi dinamicamente a URL da chamada
                        .path("/weather")               //Define o endpoint base da Api
                         //queryPAram: Insere os parâmentros na URL
                        .queryParam("q", cidade)         //q com nome da cidade
                        .queryParam("appid", apiKey)     // appi com a chave de acesso
                        .queryParam("units", "metric")   // Celsius
                        .queryParam("lang", "pt_br")     // descrição em português
                        .build())
                .retrieve()                             //Dispara a requisição para obter a resposta
                // onStatus: trata erros HTTP de forma reativa, antes do corpo da resposta
                .onStatus(
                        status -> status.value() == 404,    //Interromp o fluxo reativo e lança erro customizado
                        response -> Mono.error(new RuntimeException("Cidade não encontrada: " + cidade))
                )
                .onStatus(
                        status -> status.is5xxServerError(),  // Se o servidor API falhar lança exceção informando indisponibilidade
                        response -> Mono.error(new RuntimeException("OpenWeatherMap fora do ar"))
                )
                .bodyToMono(ClimaDTO.class); // deserializa o JSON para o DTO. 
                // Mapeia e desserializa o payload JSON retornado pela API 
                // diretamente para um objeto Java do tipo ClimaDTO.
    }
}