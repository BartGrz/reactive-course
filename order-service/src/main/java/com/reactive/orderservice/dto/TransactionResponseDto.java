package com.reactive.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

    private Integer userId;
    private Integer amount;
    private TransactionStatus transactionStatus;

}
