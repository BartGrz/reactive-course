package com.reactive.userservice.controller;

import com.reactive.userservice.dto.UserDto;
import com.reactive.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("user")
class UserController {

    private UserService userService;

    @GetMapping
    public Flux<UserDto> readAll(){
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    Mono<UserDto> readById(@PathVariable int id) {
       return userService.getById(id);
    }
    @PostMapping
    Mono<UserDto> createNewUser(@RequestBody Mono<UserDto> userDtoMono){
        return userService.createUser(userDtoMono);
    }
    @PutMapping("{id}")
    Mono<UserDto> updateUser(@PathVariable int id, @RequestBody Mono<UserDto> userDtoMono) {
        return userService.updateUser(id,userDtoMono);
    }
    @DeleteMapping("{id}")
    Mono<Void> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

}
