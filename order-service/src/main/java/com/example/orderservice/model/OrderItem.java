package com.example.orderservice.model;

import lombok.Data;

@Data
public class OrderItem {
    private Long productId;
    private Integer quantity;
    private Double price;
    private String productName;
} 