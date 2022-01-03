package com.reactive.userservice.service;

import com.reactive.userservice.dto.TransactionRequestDto;
import com.reactive.userservice.dto.TransactionResponseDto;
import com.reactive.userservice.dto.TransactionStatus;
import com.reactive.userservice.model.UserTransaction;
import com.reactive.userservice.repository.UserRepository;
import com.reactive.userservice.repository.UserTransactionRepository;
import com.reactive.userservice.util.TransactionUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private UserRepository userRepository;
    private UserTransactionRepository userTransactionRepository;

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto){
        return userRepository
                .updateUserBalance(requestDto.getUserId(),requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b-> TransactionUtils.fromDto(requestDto))
                .flatMap(userTransactionRepository::save)
                .map(userTransaction -> TransactionUtils.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(TransactionUtils.toDto(requestDto, TransactionStatus.DECLINED));

    }
    public Flux<UserTransaction> getByUserId(int userId){
        return userTransactionRepository.findByUserId(userId);
    }


}
