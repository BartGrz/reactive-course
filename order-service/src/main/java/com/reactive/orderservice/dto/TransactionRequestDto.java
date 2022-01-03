package com.reactive.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionRequestDto {

    private Integer userId;
    private Integer amount;
    private TransactionStatus transactionStatus;

}
