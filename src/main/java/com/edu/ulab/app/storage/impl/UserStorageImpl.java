package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.storage.UserStorage;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserStorageImpl implements UserStorage {
    private final Map<Long, UserDto> userDtoMap = new HashMap<>();

    @Override
    public UserDto createUser(UserDto userDto) {
        userDtoMap.putIfAbsent(userDto.getId(), userDto);
        return getUserById(userDto.getId()).get();
    }

    @Override
    public Optional<UserDto> updateUser(UserDto userDto) {
        Optional<UserDto> update = getUserById(userDtoMap.values()
                .stream()
                .filter(Objects::nonNull)
                .filter(userDto1 -> userDto1.getFullName().equals(userDto.getFullName()))
                .findFirst()
                .map(UserDto::getId).orElse(-1L));
        update.ifPresent(dto -> userDtoMap.computeIfPresent(dto.getId(), (k, v) -> dto));
        return update;
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return Optional.ofNullable(userDtoMap.get(id));
    }

    @Override
    public void deleteUserById(Long id) {
        userDtoMap.remove(id);
    }
}
