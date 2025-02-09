package com.myapp.service;

import com.myapp.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product getProductById(String id);
    List<Product> listProducts();
    List<Product> searchProductsByName(String name);
    void deleteProduct(String id);
}
