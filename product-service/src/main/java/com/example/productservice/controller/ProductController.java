package com.example.productservice.controller;

import com.example.productservice.dto.ProductDTO;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for managing products in the e-commerce system")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(
        summary = "Get all products",
        description = "Retrieves a list of all available products in the system"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved all products",
        content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Product.class))
    )
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get product by ID",
        description = "Retrieves a specific product by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product found successfully",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Product.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Product not found",
            content = @Content
        )
    })
    public ResponseEntity<Product> getProduct(
        @Parameter(description = "ID of the product to retrieve", required = true)
        @PathVariable Long id
    ) {
        Product product = productService.getProduct(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
        summary = "Create new product",
        description = "Creates a new product in the system with the provided details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product created successfully",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Product.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content
        )
    })
    public Product createProduct(
        @Parameter(description = "Product details", required = true)
        @Valid @RequestBody ProductDTO productDTO
    ) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update existing product",
        description = "Updates an existing product's information by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product updated successfully",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Product.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Product not found",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content
        )
    })
    public ResponseEntity<Product> updateProduct(
        @Parameter(description = "ID of the product to update", required = true)
        @PathVariable Long id,
        @Parameter(description = "Updated product details", required = true)
        @Valid @RequestBody ProductDTO productDTO
    ) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        Product updatedProduct = productService.updateProduct(id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete product",
        description = "Deletes a product from the system by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Product not found"
        )
    })
    public ResponseEntity<Void> deleteProduct(
        @Parameter(description = "ID of the product to delete", required = true)
        @PathVariable Long id
    ) {
        return productService.deleteProduct(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
} 