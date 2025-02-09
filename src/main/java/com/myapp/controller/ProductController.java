package com.myapp.controller;

import com.myapp.converter.ProductConverter;
import com.myapp.dto.ProductDto;
import com.myapp.entity.Product;
import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService m_productService;
    @Autowired
    private ProductConverter m_productConverter;

    @GetMapping(value = "/list")
    public ResponseEntity<List<ProductDto>> listProduct(){
        List<ProductDto> products = new ArrayList<>();
        List<Product> productEntities = m_productService.listProducts();
        for (Product product : productEntities){
            if (product != null){
                products.add(m_productConverter.productToDto(product));
            }
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto){
        Product product = m_productConverter.productToEntity(productDto);
        try{
            m_productService.saveProduct(product);
            productDto.setId(product.getId());
            return ResponseEntity.ok(productDto);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", "Cannot add product"));
        }
    }

    @DeleteMapping(value = "/del/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        try{
            ProductDto productDto = m_productConverter.productToDto(m_productService.getProductById(id));
            if (productDto == null){
                return ResponseEntity.ok("Product is not found");
            }
            m_productService.deleteProduct(id);
            return ResponseEntity.ok("Product is deleted");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Cannot delete Product");
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id){
        try{
            ProductDto productDto = m_productConverter.productToDto(m_productService.getProductById(id));
            if (productDto == null){
                return ResponseEntity.ok().body(Map.of("message", "Product not found"));
            }
            return ResponseEntity.ok(productDto);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", "Cannot get product"));
        }
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDtoUp, @PathVariable String id) {
        try {
            ProductDto productDto = m_productConverter.productToDto(m_productService.getProductById(id));
            if (productDto == null) {
                return ResponseEntity.ok().body(Map.of("message", "Product not found"));
            }
            m_productService.saveProduct(m_productConverter.productToEntity(productDto));
            return ResponseEntity.ok().body(productDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Missing body request"));
        }
    }
}
