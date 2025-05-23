package com.example.orderservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Order entity representing a customer's purchase in the e-commerce system")
public class Order {
    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;

    @Schema(description = "List of items in the order", example = "[{\"productId\": 1, \"quantity\": 2}]")
    private List<OrderItem> items;

    @Schema(description = "Total price of the order in USD", example = "1999.98")
    private Double totalPrice;

    @Schema(description = "Current status of the order", example = "PENDING", allowableValues = {"PENDING", "CONFIRMED", "SHIPPED", "DELIVERED"})
    private String status;
} 