package com.reactive.userservice.controller;

import com.reactive.userservice.dto.TransactionRequestDto;
import com.reactive.userservice.dto.TransactionResponseDto;
import com.reactive.userservice.model.UserTransaction;
import com.reactive.userservice.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
@AllArgsConstructor
@Slf4j
class userTransactionController {

    private TransactionService transactionService;

    @PostMapping
    Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> transactionRequestDtoMono) {
        return transactionRequestDtoMono
                .flatMap(transactionService::createTransaction);
    }
    @GetMapping
    Flux<UserTransaction> getByUserId(@RequestParam("userId") int userId) {
        return transactionService.getByUserId(userId);
    }

}
