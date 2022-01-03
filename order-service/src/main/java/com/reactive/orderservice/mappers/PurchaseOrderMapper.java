package com.reactive.orderservice.mappers;

import com.reactive.orderservice.dto.PurchaseOrderResponseDto;
import com.reactive.orderservice.model.PurchaseOrder;
import org.mapstruct.Mapper;

@Mapper
public interface PurchaseOrderMapper {
    PurchaseOrderResponseDto purchaseOrderResponseDtoFromPurchaseOrder(PurchaseOrder purchaseOrder);

}
