package com.library.management.mapper;

import com.library.management.dto.BookDto;
import com.library.management.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .availableCopies(book.getAvailableCopies())
                .build();
    }

    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setAvailableCopies(bookDto.getAvailableCopies());
        return book;
    }
}