package com.reactive.orderservice.service;

import com.reactive.orderservice.dto.PurchaseOrderResponseDto;
import com.reactive.orderservice.model.PurchaseOrder;
import com.reactive.orderservice.repository.PurchaseOrderRepository;
import com.reactive.orderservice.util.ModelDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class OrderQueryService {

    @Autowired
    private PurchaseOrderRepository repository;

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId){
       return Flux.fromStream(()->repository.findByUserId(userId)
               .stream())
               .map(ModelDtoUtil::getPurchaseOrderResponseDto)
               .subscribeOn(Schedulers.boundedElastic()); //Jpa query is blocking, thats why subscribeOn method is needed
    }
    public List<PurchaseOrder> getAllPurchaseOrders(){
        return repository.findAll();
    }

}
