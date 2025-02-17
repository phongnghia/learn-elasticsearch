package com.myapp.implement;

import com.myapp.controller.ProductController;
import com.myapp.converter.ProductConverter;
import com.myapp.dto.ProductDto;
import com.myapp.entity.Product;
import com.myapp.repository.ProductRepository;
import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository m_productRepository;

    @Autowired
    private ProductConverter m_productConverter;

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        Product product = m_productConverter.productToEntity(productDto);
        m_productRepository.save(product);
        productDto.setId(product.getId());
        return productDto;
    }

    @Override
    public ProductDto getProductById(String id) {
        UUID uuid = UUID.fromString(id);
        return m_productConverter.productToDto(m_productRepository.findProductById(uuid));
    }

    @Override
    public List<ProductDto> listProducts() {
        List<Product> products = m_productRepository.findAll();

        List<ProductDto> listProductDto = products.stream().map(m_productConverter::productToDto).collect(Collectors.toList());
        return listProductDto;
    }

    @Override
    public List<ProductDto> searchProductsByName(String name) {
        List<Product> products = m_productRepository.findByName(name);

        return products.stream().map(m_productConverter::productToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(String id) {
        UUID uuid = UUID.fromString(id);
        m_productRepository.deleteById(uuid);
    }

    @Override
    public List<ProductDto> processProduct(double price) {
        List<Product> products = m_productRepository.findAll();
        List<ProductDto> filterProduct = products.stream()
                .filter(p -> p.getPrice() > price)
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .map(m_productConverter::productToDto)
                .collect(Collectors.toList());
        return filterProduct;
    }
}
