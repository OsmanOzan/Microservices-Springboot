package com.example.productservice.service;

import com.example.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Product getProduct(Long id) {
        return products.get(id);
    }

    public Product createProduct(Product product) {
        product.setId(idGenerator.incrementAndGet());
        products.put(product.getId(), product);
        return product;
    }

    public Product updateProduct(Long id, Product product) {
        if (products.containsKey(id)) {
            product.setId(id);
            products.put(id, product);
            return product;
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        return products.remove(id) != null;
    }
} 