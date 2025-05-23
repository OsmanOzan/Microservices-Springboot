package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequestDTO;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "APIs for managing orders in the e-commerce system")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(
        summary = "Get all orders",
        description = "Retrieves a list of all orders in the system"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved all orders",
        content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Order.class))
    )
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get order by ID",
        description = "Retrieves a specific order by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Order found successfully",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Order.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Order not found",
            content = @Content
        )
    })
    public ResponseEntity<Order> getOrder(
        @Parameter(description = "ID of the order to retrieve", required = true)
        @PathVariable Long id
    ) {
        Order order = orderService.getOrder(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
        summary = "Create new order",
        description = "Creates a new order in the system with the provided product details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Order created successfully",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Order.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data or product not available",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "503",
            description = "Product service unavailable",
            content = @Content
        )
    })
    public Mono<Order> createOrder(
        @Parameter(description = "Order details including product IDs and quantities", required = true)
        @Valid @RequestBody OrderRequestDTO orderRequest
    ) {
        return orderService.createOrder(orderRequest);
    }
} 