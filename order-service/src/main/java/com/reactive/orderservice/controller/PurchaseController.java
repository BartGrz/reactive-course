package com.reactive.orderservice.controller;

import com.reactive.orderservice.dto.PurchaseOrderRequestDto;
import com.reactive.orderservice.dto.PurchaseOrderResponseDto;
import com.reactive.orderservice.model.PurchaseOrder;
import com.reactive.orderservice.service.OrderFulfillmentService;
import com.reactive.orderservice.service.OrderQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("order")
@AllArgsConstructor
class PurchaseController {


    private OrderFulfillmentService service;
    private OrderQueryService queryService;

    @PostMapping
    Mono<ResponseEntity<PurchaseOrderResponseDto>> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return service.processOrder(requestDtoMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class,ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class,ResponseEntity.status(503).build());
    }
    @GetMapping("user/{id}")
    Flux<PurchaseOrderResponseDto> getOrderById(@PathVariable int id) {
        return queryService.getProductsByUserId(id);
    }

    @GetMapping
    ResponseEntity<List<PurchaseOrder>> getAllOrders(){
        var allOrders =  queryService.getAllPurchaseOrders();
        if(allOrders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allOrders);
    }
}
