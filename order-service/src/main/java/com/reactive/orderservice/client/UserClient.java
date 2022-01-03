package com.reactive.orderservice.client;

import com.reactive.orderservice.dto.TransactionRequestDto;
import com.reactive.orderservice.dto.TransactionResponseDto;
import com.reactive.orderservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient (@Value("${user.service.url}") String url) {
        webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto requestDto) {
        return webClient
                .post()
                .uri("transaction")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);

    }
    public Flux<UserDto> getAllUsers(){
       return webClient
               .get()
               .uri("/")
               .retrieve()
               .bodyToFlux(UserDto.class);
    }
}
