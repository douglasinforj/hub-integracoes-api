package com.douglas.hub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        // Instancia única gerenciada pelo Spring
        // Aqui podemos configurar timout, interceptors, etc
        return new RestTemplate();
    }
}
