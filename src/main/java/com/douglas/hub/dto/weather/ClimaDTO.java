package com.douglas.hub.dto.weather;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ClimaDTO(
    String name,
    MainDTO main,
    WindDTO wind,
    List<WeatherDescDTO> weather // array - pegamos o índice
) {}
