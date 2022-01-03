package com.reactive.userservice.mappers;

import com.reactive.userservice.dto.UserDto;
import com.reactive.userservice.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);

}
