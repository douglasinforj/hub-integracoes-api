package com.douglas.hub.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Ignora campos que a VIaCEP retorna, que não vamos precisar
@JsonIgnoreProperties(ignoreUnknown = true)
public record EnderecoDTO(String cep, 
    String logradouro, 
    String complemento, 
    String bairro, 
    String localidade,
    String uf,
    Boolean erro   //ViaCep retorna este campo quando o CEP não existe
) {}
