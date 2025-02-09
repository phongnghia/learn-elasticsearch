package com.myapp.converter;

import com.myapp.dto.ProductDto;
import com.myapp.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductConverter {
    ProductConverter INSTANCE = Mappers.getMapper(ProductConverter.class);

    ProductDto productToDto(Product product);

    Product productToEntity(ProductDto productDto);
}