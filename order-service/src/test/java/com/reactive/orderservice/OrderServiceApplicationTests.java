package com.reactive.orderservice;

import com.reactive.orderservice.client.ProductClient;
import com.reactive.orderservice.client.UserClient;
import com.reactive.orderservice.dto.ProductDto;
import com.reactive.orderservice.dto.PurchaseOrderRequestDto;
import com.reactive.orderservice.dto.UserDto;
import com.reactive.orderservice.service.OrderFulfillmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@Slf4j
@Disabled
class OrderServiceApplicationTests {

    @Autowired
    private UserClient userClient;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private OrderFulfillmentService service;

    @Test
    void contextLoads() {

     var fluxDto =   Flux.zip(userClient.getAllUsers(),productClient.getAllProducts())
                .map(t-> buildDto(t.getT1(),t.getT2()))
                .flatMap(dto->service.processOrder(Mono.just(dto)))
                .doOnNext(responseDto -> log.info("response {} ",responseDto));

        StepVerifier.create(fluxDto).expectNextCount(3).verifyComplete();

    }
    private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto){
        return PurchaseOrderRequestDto.builder()
                .userId(userDto.getId())
                .productId(productDto.getId())
                .build();
    }

}
