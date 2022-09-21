package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.storage.BookStorage;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BookStorageImpl implements BookStorage {
    private final Map<Long, BookDto> bookDtoMap = new HashMap<>();

    @Override
    public BookDto createBook(BookDto bookDto) {
        bookDtoMap.putIfAbsent(bookDto.getId(), bookDto);
        return getBookById(bookDto.getId());
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        bookDtoMap.put(bookDto.getId(), bookDto);
        return getBookById(bookDto.getId());
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookDtoMap.get(id);
    }

    @Override
    public void deleteBookById(Long id) {
        bookDtoMap.remove(id);
    }

    @Override
    public List<BookDto> getUserBooks(Long userId) {
        return bookDtoMap.values().stream()
                .filter(Objects::nonNull)
                .filter(bookDto -> bookDto.getUserId().equals(userId))
                .toList();
    }
}
