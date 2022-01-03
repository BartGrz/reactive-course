package com.reactive.orderservice.repository;

import com.reactive.orderservice.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Integer> {


    List<PurchaseOrder> findByUserId(int userId);

}
