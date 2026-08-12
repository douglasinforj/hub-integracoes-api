package com.douglas.hub.dto.email;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// Este é o DTO que será enviado para a API do SMTP2GO
public record EmailBodyDTO(

    @JsonProperty("api_key")
    String apiKey,

    @JsonProperty("to")
    List<String> to,

    @JsonProperty("sender")
    String sender,

    @JsonProperty("subject")
    String subject,

    @JsonProperty("text_body")
    String textBody
) {}