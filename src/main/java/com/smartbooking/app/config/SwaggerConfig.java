package com.smartbooking.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI smartBookingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Smart Booking API")
                        .description("API documentation for Smart Booking System")
                        .version("1.0.0"));
    }
}
