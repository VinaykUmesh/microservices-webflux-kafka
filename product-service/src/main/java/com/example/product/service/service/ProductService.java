package com.example.product.service.service;

import com.example.events.ProductDto;
import com.example.product.service.model.Product;
import com.example.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto addProduct(Product product) {
        return convertToProductDto(productRepository.save(product));
    }


    public List<ProductDto> all() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToProductDto).collect(Collectors.toList());
    }

    public ProductDto findById(Long productId) {
        Product found = productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not found: " + productId));
        return convertToProductDto(found);
    }

    private ProductDto convertToProductDto(Product products) {
        return ProductDto.builder()
                .id(products.getId())
                .productName(products.getProductName())
                .productPrice(products.getProductPrice())
                .build();
    }
}
