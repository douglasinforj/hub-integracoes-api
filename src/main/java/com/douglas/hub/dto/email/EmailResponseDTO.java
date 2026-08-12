package com.douglas.hub.dto.email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EmailResponseDTO(

    @JsonProperty("request_id")
    String requestId,

    // data contém o resultado — simplificamos para String
    Object data
) {}