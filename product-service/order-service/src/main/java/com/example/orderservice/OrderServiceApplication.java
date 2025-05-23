package com.example.orderservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Order Service API",
        version = "1.0",
        description = "Order management endpoints for e-commerce microservices demo"
    )
)
public class OrderServiceApplication {
    
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient productServiceClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8085").build();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
} 