package com.douglas.hub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${weather.base-url}")
    private String weatherBaseUrl;

    @Bean
    public WebClient weatherWebClient() {
        return WebClient.builder()
                .baseUrl(weatherBaseUrl)
                // defaultHeader: todo request já vai com Accept: application/json
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
