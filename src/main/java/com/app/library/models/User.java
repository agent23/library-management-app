package com.app.library.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {
    @Id
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Address address;
    private ContactDetails contactDetails;
}
