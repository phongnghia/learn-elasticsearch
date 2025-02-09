package com.myapp.implement;

import com.myapp.entity.Product;
import com.myapp.repository.ProductRepository;
import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository m_productRepository;

    @Override
    public Product saveProduct(Product product) {
        return m_productRepository.save(product);
    }

    @Override
    public Product getProductById(String id) {
        UUID uuid = UUID.fromString(id);
        return m_productRepository.findProductById(uuid);
    }

    @Override
    public List<Product> listProducts() {
        return m_productRepository.findAll();
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return m_productRepository.findByName(name);
    }

    @Override
    public void deleteProduct(String id) {
        UUID uuid = UUID.fromString(id);
        m_productRepository.deleteById(uuid);
    }
}
