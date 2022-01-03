package com.reactive.productservice.mappers;

import com.reactive.productservice.dto.ProductDto;
import com.reactive.productservice.model.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);


}
