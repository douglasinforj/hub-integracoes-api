package com.douglas.hub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final String weatherBaseUrl;

    public WebClientConfig(@Value("${weather.base-url}") String weatherBaseUrl) {
        this.weatherBaseUrl = weatherBaseUrl;
        //log teste
        System.out.println(">>> WebClientConfig | baseUrl: " + weatherBaseUrl);
    }

    @Bean
    public WebClient weatherWebClient() {
        return WebClient.builder()
                .baseUrl(weatherBaseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
