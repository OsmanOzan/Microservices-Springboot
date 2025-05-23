package com.example.orderservice.client;

import com.example.orderservice.dto.ProductDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {
    private final WebClient productServiceClient;

    public ProductClient(WebClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    public Mono<ProductDTO> getProduct(Long productId) {
        return productServiceClient.get()
                .uri("/api/products/" + productId)
                .retrieve()
                .bodyToMono(ProductDTO.class);
    }
} 