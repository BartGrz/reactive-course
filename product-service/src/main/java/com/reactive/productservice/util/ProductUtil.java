package com.reactive.productservice.util;

import com.reactive.productservice.dto.ProductDto;
import com.reactive.productservice.mappers.ProductMapperImpl;
import com.reactive.productservice.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductUtil {

    /**
     * Converting ProductDto to Product object via mapstruckt
     * @param productDto
     * @return
     */
    public static Product convertFromDto(ProductDto productDto) {
        return new ProductMapperImpl().productDtoToProduct(productDto);
    }
    /**
     * Converting Product to ProductDto object via mapstruckt
     * @param product
     * @return
     */
    public static ProductDto convertToDto(Product product) {
        return new ProductMapperImpl().productToProductDto(product);
    }

}
