package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;

import java.util.List;

public interface BookStorage {
    BookDto createBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);

    List<BookDto> getUserBooks(Long userId);
}
