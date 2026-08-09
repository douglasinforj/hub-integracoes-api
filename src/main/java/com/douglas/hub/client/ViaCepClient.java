// client/ViaCepClient.java
package com.douglas.hub.client;

import com.douglas.hub.dto.EnderecoDTO;
import com.douglas.hub.exception.CepNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;  // final + construtor

    public ViaCepClient(
            RestTemplate restTemplate,
            @Value("${viacep.base-url}") String baseUrl  //@Value no construtor
    ) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public EnderecoDTO buscarEndereco(String cep) {
        try {
            EnderecoDTO endereco = restTemplate.getForObject(baseUrl, EnderecoDTO.class, cep);

            if (endereco == null || Boolean.TRUE.equals(endereco.erro())) {
                throw new CepNotFoundException("CEP não encontrado: " + cep);
            }

            return endereco;

        } catch (HttpClientErrorException e) {
            throw new CepNotFoundException("CEP inválido ou não encontrado: " + cep);
        }
    }
}