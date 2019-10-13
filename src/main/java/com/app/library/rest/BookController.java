package com.app.library.rest;

import com.app.library.models.Book;
import com.app.library.services.LibraryService;
import com.app.library.utils.UtilHelpers;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private LibraryService libraryService;

    @Autowired
    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/save")
    @ApiOperation(value = "Rest API to add a book", notes = "An api endpoint that creates a book entry")
    public ResponseEntity save(@RequestBody Book book) {
        if (!UtilHelpers.validBook(book))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provide all book properties!");
        return ResponseEntity.status(HttpStatus.OK).body(libraryService.save(book));
    }

    @DeleteMapping("/delete/{isbn}")
    @ApiOperation(value = "Rest API to delete a book", notes = "An api endpoint that delete a book entry")
    public ResponseEntity delete(@PathVariable String isbn) {
        if (!UtilHelpers.checkIsbn(isbn))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ISBN can not be empty!");
        libraryService.deleteBook(isbn);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/update")
    @ApiOperation(value = "Rest API to update a book", notes = "An api endpoint that update a book entry")
    public ResponseEntity update(@RequestBody Book book) {
        if (!UtilHelpers.validBook(book))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provide all book properties!");
        return ResponseEntity.status(HttpStatus.OK).body(libraryService.update(book));
    }

    @GetMapping("/book")
    @ApiOperation(value = "Rest API to get all books", notes = "An api endpoint that gets all book entries")
    public List<Book> getAll() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/book/{isbn}")
    @ApiOperation(value = "Rest API to get a book", notes = "An api endpoint that get a book entry")
    public ResponseEntity get(@PathVariable String isbn) {
        if (!UtilHelpers.checkIsbn(isbn))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ISBN can not be empty!");
        if (libraryService.search(isbn).isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(libraryService.search(isbn));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
