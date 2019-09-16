package com.app.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Address address;
    private ContactDetails contactDetails;
}
