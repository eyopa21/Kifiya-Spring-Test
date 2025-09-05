package com.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI skyMenuOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kifiya-Test API")
                        .description("Spring Boot API for Products, Promotions, and Cart")
                        .version("v1.0.0"));
    }
}
