package com.app.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String password;
    private String phone;
    private String email;

    //TODO:
    //private Address address;
    //private ContactDetails contactDetails;
    //private String firstName;
    //private String lastName;
}
