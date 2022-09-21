package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.UserStorage;
import com.edu.ulab.app.storage.impl.UserStorageImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserStorageImpl userStorage;

    public UserServiceImpl(UserStorageImpl userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        long id = new Random().nextLong();
        userDto.setId(Math.abs(id));
        userStorage.createUser(userDto);
        return userDto;
    }

    @Override
    public Optional<UserDto> updateUser(UserDto userDto) {
        return userStorage.updateUser(userDto);
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userStorage.deleteUserById(id);
    }
}
