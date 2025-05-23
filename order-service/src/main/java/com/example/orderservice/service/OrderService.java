package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.OrderItemRequestDTO;
import com.example.orderservice.dto.OrderRequestDTO;
import com.example.orderservice.dto.ProductDTO;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderItem;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final ProductClient productClient;

    public OrderService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public Order getOrder(Long id) {
        return orders.get(id);
    }

    public Mono<Order> createOrder(OrderRequestDTO orderRequest) {
        List<Mono<OrderItem>> orderItems = orderRequest.getItems().stream()
                .map(this::createOrderItem)
                .collect(Collectors.toList());

        return Mono.zip(orderItems, items -> {
            Order order = new Order();
            order.setId(idGenerator.incrementAndGet());
            order.setItems(List.of(items).stream()
                    .map(item -> (OrderItem) item)
                    .collect(Collectors.toList()));
            order.setTotalPrice(calculateTotalPrice(order.getItems()));
            order.setStatus("CREATED");
            orders.put(order.getId(), order);
            return order;
        });
    }

    private Mono<OrderItem> createOrderItem(OrderItemRequestDTO itemRequest) {
        return productClient.getProduct(itemRequest.getProductId())
                .map(product -> createOrderItemFromProduct(itemRequest, product));
    }

    private OrderItem createOrderItemFromProduct(OrderItemRequestDTO itemRequest, ProductDTO product) {
        OrderItem item = new OrderItem();
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setQuantity(itemRequest.getQuantity());
        item.setPrice(product.getPrice());
        return item;
    }

    private Double calculateTotalPrice(List<OrderItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
} 