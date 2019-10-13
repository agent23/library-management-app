package com.app.library.services;

import com.app.library.models.Book;

import java.util.List;
import java.util.Optional;

public interface LibraryService {

    Book save(Book book);

    Optional<Book> search(String isbn);

    List<Book> getAllBooks();

    void deleteBook(String isbn);

    Book update(Book book);
}
