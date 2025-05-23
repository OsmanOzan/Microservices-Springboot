package com.example.productservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product entity representing an item in the e-commerce system")
public class Product {
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    @Schema(description = "Name of the product", example = "iPhone 13")
    private String name;

    @Schema(description = "Detailed description of the product", example = "Latest model with 128GB storage")
    private String description;

    @Schema(description = "Price of the product in USD", example = "999.99")
    private Double price;

    @Schema(description = "Available quantity in stock", example = "50")
    private Integer stock;
} 