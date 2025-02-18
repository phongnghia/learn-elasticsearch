package com.myapp.service;

import com.myapp.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto saveProduct(ProductDto productDto);
    ProductDto getProductById(String id);
    List<ProductDto> listProducts();
    List<ProductDto> searchProductsByName(String name);
    void deleteProduct(String id);
    List<ProductDto> processProduct(double price);
}
