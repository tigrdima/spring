package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.UserDto;

import java.util.Optional;

public interface UserStorage {
    UserDto createUser(UserDto userDto);

    Optional<UserDto> updateUser(UserDto userDto);

    Optional<UserDto> getUserById(Long id);

    void deleteUserById(Long id);
}
