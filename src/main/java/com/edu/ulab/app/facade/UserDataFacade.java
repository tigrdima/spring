package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.BookRequest;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public Optional<UserBookResponse> createUserWithBooks(UserBookRequest userBookRequest) {
        UserBookResponse userBookResponse = null;

        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        if (userDto != null) {

            UserDto createdUser = userService.createUser(userDto);
            log.info("Created user: {}", createdUser);

            List<Long> bookIdList = userBookRequest.getBookRequests()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(bookMapper::bookRequestToBookDto)
                    .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                    .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                    .map(bookService::createBook)
                    .peek(createdBook -> log.info("Created book: {}", createdBook))
                    .map(BookDto::getId)
                    .toList();
            log.info("Collected book ids: {}", bookIdList);
            userBookResponse = UserBookResponse.builder()
                    .userId(createdUser.getId())
                    .booksIdList(bookIdList)
                    .build();
        }
            return Optional.ofNullable(userBookResponse);
        }

        public Optional<UserBookResponse> updateUserWithBooks (UserBookRequest userBookRequest){
            UserBookResponse userBookResponse = null;

            log.info("Got user book update request: {}", userBookRequest);
            UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
            log.info("Mapped user request: {}", userDto);

            Optional<UserDto> update = userService.updateUser(userDto);

            if (update.isPresent()) {

                log.info("Update user: {}", update);
                List<Long> bookIdList = userBookRequest.getBookRequests()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(bookMapper::bookRequestToBookDto)
                        .peek(bookDto -> bookDto.setUserId(update.get().getId()))
                        .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                        .map(bookService::updateBook)
                        .peek(updateBook -> log.info("Update book: {}", updateBook))
                        .map(BookDto::getId)
                        .toList();
                log.info("Collected book ids: {}", bookIdList);

                userBookResponse = UserBookResponse.builder()
                        .userId(update.get().getId())
                        .booksIdList(bookIdList)
                        .build();
            }

            return Optional.ofNullable(userBookResponse);
        }

        public Optional<UserBookResponse> getUserWithBooks (Long userId){
            UserBookResponse userBookResponse = null;

            Optional<UserDto> userDto = userService.getUserById(userId);

            if (userDto.isPresent()) {
                log.info("Got userId with book request: {}", userId);
                List<Long> bookIdList = bookService.getUserBooks(userId)
                        .stream()
                        .map(BookDto::getId)
                        .toList();
                log.info("Collected book ids: {}", bookIdList);

                userBookResponse = UserBookResponse
                        .builder()
                        .userId(userDto.get().getId())
                        .booksIdList(bookIdList)
                        .build();
            }
            return Optional.ofNullable(userBookResponse);
        }

        public void deleteUserWithBooks (Long userId){
            log.info("Got userId with book request: {}", userId);
            userService.deleteUserById(userId);
            log.info("Delete userId: {}", userId);
            bookService.getUserBooks(userId).stream()
                    .filter(Objects::nonNull)
                    .filter(bookDto -> bookDto.getUserId().equals(userId))
                    .map(BookDto::getId)
                    .peek(deleteBookDtoId -> log.info("mapped book: {}", deleteBookDtoId))
                    .forEach(bookService::deleteBookById);
        }
    }
