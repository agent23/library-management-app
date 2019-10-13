package com.app.library.interfaces.impl;

import com.app.library.interfaces.repos.LibraryRepository;
import com.app.library.models.Book;
import com.app.library.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private LibraryRepository libraryRepository;

    @Autowired
    public LibraryServiceImpl(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @Override
    public Book save(Book book) {

        return libraryRepository.insert(book);
    }

    @Override
    public Optional<Book> search(String isbn) {
        return libraryRepository.findById(isbn);
    }

    @Override
    public Book update(Book book) {
        return libraryRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return libraryRepository.findAll();
    }

    @Override
    public void deleteBook(String isbn) {
        libraryRepository.deleteById(isbn);
    }

    private Book setBookProp(Book book) {
        return book;
    }
}
