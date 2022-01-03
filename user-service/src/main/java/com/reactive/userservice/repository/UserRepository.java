package com.reactive.userservice.repository;


import com.reactive.userservice.model.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User,Integer> {

    @Query(value = "Update users set balance=balance - :amount where id = :userId AND balance>=:amount")
    @Modifying
    Mono<Boolean> updateUserBalance(@Param("userId") int userId, @Param("amount") int amount);

}
