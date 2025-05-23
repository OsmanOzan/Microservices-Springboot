package com.example.orderservice.model;

import lombok.Data;
import java.util.List;

@Data
public class Order {
    private Long id;
    private List<OrderItem> items;
    private Double totalPrice;
    private String status;
} 