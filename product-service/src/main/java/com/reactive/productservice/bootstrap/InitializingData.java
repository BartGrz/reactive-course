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

@Component
@Slf4j
public class InitializingData implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

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

        Product prod_1 = Product.builder()
                .description("product_1")
                .price("200")
                .build();
        Product prod_2 = Product.builder()
                .description("product_2")
                .price("220")
                .build();
        Product prod_3 = Product.builder()
                .description("product_3")
                .price("240")
                .build();

        Flux.just(prod_1,prod_2,prod_3)
                .flatMap(product -> productRepository
                        .insert(Mono.just(product)))
                .subscribe(productDto -> log.info("product loaded : {}" + productDto));

    }
}
