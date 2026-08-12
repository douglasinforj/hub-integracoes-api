package com.douglas.hub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Smtp2GoWebClientConfig {

    private final String baseUrl;

    public Smtp2GoWebClientConfig(@Value("${smtp2go.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Bean
    public WebClient smtp2GoWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}