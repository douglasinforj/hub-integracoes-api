package com.douglas.hub.client;

import com.douglas.hub.dto.EnderecoDTO;
import com.douglas.hub.exception.CepNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClient {

    // Lê do application.yml — não precisa mais mexer no código para trocar a URL
    @Value("${viacep.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public ViaCepClient(RestTemplate restTemplate) {  // DI por construtor
        this.restTemplate = restTemplate;
    }

    public EnderecoDTO buscarEndereco(String cep) {
        try {
            EnderecoDTO endereco = restTemplate.getForObject(baseUrl, EnderecoDTO.class, cep);

            if (endereco == null || Boolean.TRUE.equals(endereco.erro())) {
                throw new CepNotFoundException("CEP não encontrado: " + cep);     //Exception personalizar para criar
            }

            return endereco;

        } catch (HttpClientErrorException e) {
            throw new CepNotFoundException("CEP inválido ou não encontrado: " + cep);   //Exception personalizar para criar
        }
    }
}