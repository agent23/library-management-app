package com.app.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "book")
public class Book implements Serializable {
    @Id
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private UserRequest userRequest;
}
