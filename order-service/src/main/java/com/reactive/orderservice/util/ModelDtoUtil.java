package com.reactive.orderservice.util;

import com.reactive.orderservice.dto.OrderStatus;
import com.reactive.orderservice.dto.PurchaseOrderResponseDto;
import com.reactive.orderservice.dto.RequestContext;
import com.reactive.orderservice.dto.TransactionRequestDto;
import com.reactive.orderservice.dto.TransactionStatus;
import com.reactive.orderservice.mappers.PurchaseOrderMapperImpl;
import com.reactive.orderservice.model.PurchaseOrder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelDtoUtil {

    public static void setTransactionRequestDto(RequestContext requestContext) {
        var trd = TransactionRequestDto.builder()
                .userId(requestContext.getPurchaseOrderRequestDto().getUserId())
                .amount(requestContext.getProductDto().getPrice())
                .build();
        requestContext.setTransactionRequestDto(trd);
    }
    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {

       var status = requestContext.getTransactionResponseDto().getTransactionStatus();
        return PurchaseOrder.builder()
                .userId(requestContext.getPurchaseOrderRequestDto().getUserId())
                .productId(requestContext.getProductDto().getId())
                .amount(requestContext.getProductDto().getPrice())
                .status(TransactionStatus.APPROVED==status ? OrderStatus.COMPLETED : OrderStatus.FAILED)
                .build();
    }

    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
        return new PurchaseOrderMapperImpl().purchaseOrderResponseDtoFromPurchaseOrder(purchaseOrder);
    }
}
