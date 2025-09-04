package com.example.mapper;

import com.example.dto.ProductDto;
import com.example.entity.Product;

public class ProductMapper {


    public static ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }


    public static Product mapToProduct(ProductDto dto) {
        return Product.builder()
                .id(dto.getId()) // null on create, present on updates
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();
    }
}
