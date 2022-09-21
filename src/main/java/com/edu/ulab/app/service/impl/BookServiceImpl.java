package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.impl.BookStorageImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private final BookStorageImpl bookStorage;

    public BookServiceImpl(BookStorageImpl bookStorage) {
        this.bookStorage = bookStorage;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
            bookDto.setId(idRandom());
        log.info("Create bookDto: {}", bookDto);
        return bookStorage.createBook(bookDto);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        bookDto.setId(idRandom());
        log.info("Update bookDto: {}", bookDto);
        return bookStorage.updateBook(bookDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("GetBook bookDto: {}", bookStorage.getBookById(id));
        return bookStorage.getBookById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Delete BookDto by ID: {}", id);
        bookStorage.deleteBookById(id);
    }

    @Override
    public List<BookDto> getUserBooks(Long userId) {
        log.info("Get User books: {}", bookStorage.getUserBooks(userId));
        return bookStorage.getUserBooks(userId);
    }

    private long idRandom () {
        return Math.abs(new Random().nextLong());
    }
}
