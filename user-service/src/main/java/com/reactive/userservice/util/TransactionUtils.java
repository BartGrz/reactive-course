package com.reactive.userservice.util;

import com.reactive.userservice.dto.TransactionRequestDto;
import com.reactive.userservice.dto.TransactionResponseDto;
import com.reactive.userservice.dto.TransactionStatus;
import com.reactive.userservice.model.UserTransaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionUtils {

    public static UserTransaction fromDto(TransactionRequestDto transactionRequestDto) {
        return UserTransaction.builder()
                //.id(transactionRequestDto.getUserId())
                .userId(transactionRequestDto.getUserId())
                .transactionDate(LocalDateTime.now())
                .amount(transactionRequestDto.getAmount())
                .build();
    }
    public static TransactionResponseDto toDto(TransactionRequestDto transactionRequestDto, TransactionStatus status) {
        return TransactionResponseDto.builder()
                .transactionStatus(status)
                .userId(transactionRequestDto.getUserId())
                .amount(transactionRequestDto.getAmount())
                .build();
    }
}
