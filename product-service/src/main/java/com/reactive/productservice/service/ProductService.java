package com.reactive.productservice.service;

import com.reactive.productservice.dto.ProductDto;
import com.reactive.productservice.repository.ProductRepository;
import com.reactive.productservice.util.ProductUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

private ProductRepository productRepository;

    public Flux<ProductDto> getAll(){
       return productRepository
               .findAll()
               .map(ProductUtil::convertToDto);
    }
    public Flux<ProductDto> getAllFromRange(int min, int max){
        return productRepository
                .findByPriceBetween(Range.closed(min,max))
                .map(ProductUtil::convertToDto);
    }
    public Mono<ProductDto> getProductById(String id) {
        return productRepository
                .findById(id)
                .map(ProductUtil::convertToDto);
    }
    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(ProductUtil::convertFromDto)
                .flatMap(productRepository::insert)
                .map(ProductUtil::convertToDto);
    }

    /**
     * updating product, getting id and Mono<ProductDto> from client
     * updating the product based on @RequestBody we get
     * @param id
     * @param productDtoMono
     * @return
     */
    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return productRepository.findById(id)
                .flatMap(p-> productDtoMono
                        .map(ProductUtil::convertFromDto) //converting from dto to save entity
                        .doOnNext(product->product.setId(id))) //
                .flatMap(productRepository::save)
                .map(ProductUtil::convertToDto);
    }
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}
