package com.reactive.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PurchaseOrderResponseDto {

    private int orderId;
    private int userId;
    private String productId; //mongodb has only strings
    private int amount;
    private OrderStatus status;

}
