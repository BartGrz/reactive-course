package com.reactive.productservice.controller;

import com.reactive.productservice.dto.ProductDto;
import com.reactive.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;


@AllArgsConstructor
@RestController
@RequestMapping("/product")
@Slf4j
class ProductController {

    private ProductService productService;

    @GetMapping
    public Flux<ProductDto> all(){
        return productService.getAll();
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> all(@PathVariable String id){
        return productService
                .getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/price-range")
    Flux<ResponseEntity<ProductDto>> getAllFromRange(@RequestParam("min") int min, @RequestParam("max") int max){
        log.info("exposing data min {}, max {} ",min,max);
        return productService.getAllFromRange(min,max)
                .map(productDto -> ResponseEntity.ok(productDto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Mono<ResponseEntity<ProductDto>> saveProduct(@RequestBody Mono<ProductDto> productDtoMono){
        return productService.insertProduct(productDtoMono)
                .map(productDto -> ResponseEntity.created(URI.create("/"+productDto.getId()))
                .body(productDto))
                .onErrorReturn(ResponseEntity.badRequest().build());

    }
    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono) {
        return productService
                .updateProduct(id,productDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorReturn(ResponseEntity.badRequest().build());
    }
    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id){
        return productService
                .deleteProduct(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }
}
