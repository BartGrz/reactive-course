package com.reactive.orderservice.service;

import com.reactive.orderservice.client.ProductClient;
import com.reactive.orderservice.client.UserClient;
import com.reactive.orderservice.dto.PurchaseOrderRequestDto;
import com.reactive.orderservice.dto.PurchaseOrderResponseDto;
import com.reactive.orderservice.dto.RequestContext;
import com.reactive.orderservice.repository.PurchaseOrderRepository;
import com.reactive.orderservice.util.ModelDtoUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@AllArgsConstructor
@Slf4j
public class OrderFulfillmentService {

    private UserClient userClient;
    private ProductClient productClient;
    private PurchaseOrderRepository repository;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
       return requestDtoMono
                .map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(ModelDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(ModelDtoUtil::getPurchaseOrder)
                .map(repository::save)//blocking
                .map(ModelDtoUtil::getPurchaseOrderResponseDto)
               .subscribeOn(Schedulers.boundedElastic()); // will wait until blocking operation will end

    }


    private Mono<RequestContext> productRequestResponse(RequestContext requestContext) {
        return  productClient
                .getProductById(requestContext.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(requestContext::setProductDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1))) //retries request 5 times with 1 s delay 
                .thenReturn(requestContext);
    }
    private Mono<RequestContext> userRequestResponse(RequestContext requestContext) {
        return userClient
                .authorizeTransaction(requestContext.getTransactionRequestDto())
                .doOnNext(requestContext::setTransactionResponseDto)
                .thenReturn(requestContext);
    }
}
