package com.reactive.userservice.util;

import com.reactive.userservice.dto.UserDto;
import com.reactive.userservice.mappers.UserMapperImpl;
import com.reactive.userservice.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

    public static User fromDto(UserDto userDto) {
        return new UserMapperImpl().userDtoToUser(userDto);
    }
    public static UserDto toDto(User user) {
        return new UserMapperImpl().userToUserDto(user);
    }


}
