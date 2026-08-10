package com.douglas.hub.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MainDTO(
    Double temp,
    Double feels_like,  // sensação térmica
    Integer humidity    // umidade %
) {}