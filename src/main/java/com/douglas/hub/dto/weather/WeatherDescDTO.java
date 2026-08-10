package com.douglas.hub.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherDescDTO(
    String description              // "Céu Limpo", "chuva fraca", etc
){}

