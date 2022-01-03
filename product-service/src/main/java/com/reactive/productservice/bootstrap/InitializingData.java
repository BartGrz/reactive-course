package com.reactive.productservice.bootstrap;

import com.reactive.productservice.dto.ProductDto;
import com.reactive.productservice.model.Product;
import com.reactive.productservice.repository.ProductRepository;
import com.reactive.productservice.service.ProductService;
import com.reactive.productservice.util.ProductUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class InitializingData implements CommandLineRunner {

    @Autowired
    private ProductService service;
    @Autowired
    ProductRepository productRepository;

    @Override
    public void run(final String... args) throws Exception {

        long [] size_of = new long[1] ;
        productRepository.count().subscribe(size->{
            size_of[0]=size;//this is non blocking way of getting repo size value
        });
        if(size_of[0]==0) {
            initialize();
        }else {
            log.info("Repository is not empty");
        }
    }

    /**
     * loading 3 Product objects
     */
    private void initialize(){

        ProductDto prod_1 = ProductDto.builder()
                .description("product_1")
                .price(200)
                .build();
        ProductDto prod_2 = ProductDto.builder()
                .description("product_2")
                .price(220)
                .build();
        ProductDto prod_3 = ProductDto.builder()
                .description("product_3")
                .price(240)
                .build();

        Flux.just(prod_1,prod_2,prod_3)
                .concatWith(newProducts())
                .flatMap(productDto -> service.insertProduct(Mono.just(productDto)))
                .subscribe(productDto -> log.info("product loaded : {}" + productDto));

    }
    private Flux<ProductDto> newProducts(){
        return Flux.range(1,1000).delayElements(Duration.ofSeconds(4))
                .map(i->
                        ProductDto.builder()
                                .description("product_"+i)
                                .price(ThreadLocalRandom
                                        .current()
                                        .nextInt(10,100))
                                .build()
                );
    }
}
