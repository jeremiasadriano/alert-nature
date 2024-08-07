package com.jeremias.beprepared.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    private final String CORS_MAPPING = "/**";
    private final String[] CORS_ORIGEN = {"*"};
    private final String[] CORS_METHOD = {"*"};
    private final String[] CORS_HEADERS = {"*"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(CORS_MAPPING)
                .allowedOrigins(CORS_ORIGEN)
                .allowedMethods(CORS_METHOD)
                .allowedHeaders(CORS_HEADERS);
    }
}
