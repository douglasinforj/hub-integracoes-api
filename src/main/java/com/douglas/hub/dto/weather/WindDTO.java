package com.douglas.hub.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WindDTO(
    Double speed  // velocidade em m/s
) {}