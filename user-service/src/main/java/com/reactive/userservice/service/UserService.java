package com.reactive.userservice.service;

import com.reactive.userservice.dto.UserDto;
import com.reactive.userservice.repository.UserRepository;
import com.reactive.userservice.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Flux<UserDto> getAllUsers(){
        return userRepository
                .findAll()
                .map(UserUtils::toDto);
    }
    public Mono<UserDto> getById(int userId){
        return userRepository.findById(userId).map(UserUtils::toDto);
    }
    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
        return userDtoMono
                .map(UserUtils::fromDto)
                .flatMap(userRepository::save)
                .map(UserUtils::toDto);
    }
    public Mono<UserDto> updateUser(int id, Mono<UserDto> userDtoMono) {
        return userRepository.findById(id)
                .flatMap(user->userDtoMono
                                            .map(UserUtils::fromDto)
                                            .doOnNext(entity->entity.setId(id)))
                .flatMap(userRepository::save).map(UserUtils::toDto);
    }
    public Mono<Void> deleteUser(int userId) {
      return   userRepository.deleteById(userId);
    }

}
