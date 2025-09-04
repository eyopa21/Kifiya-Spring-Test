package com.example.service;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.ProductMapper;
import com.example.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public List<Product> createProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public Product getById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();

    }


    public ProductDto createProduct(ProductDto productDto) {

        if (productRepository.existsByName(productDto.getName())) {
            throw new IllegalArgumentException("Product with name '" + productDto.getName() + "' already exists.");
        }

        Product product = ProductMapper.mapToProduct(productDto);
        Product savedProduct  = productRepository.save(product);

        return ProductMapper.mapToProductDto(savedProduct);
    }

    public void save(Product p) { productRepository.save(p); }



    public ProductDto getProductById(UUID productId) {
        Product product =  productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found id=" + productId));

        return ProductMapper.mapToProductDto(product);
    }
}
