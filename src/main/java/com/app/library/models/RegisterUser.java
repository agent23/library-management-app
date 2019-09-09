package com.app.library.models;

import lombok.Data;

@Data
public class RegisterUser {
    private String username;
    private String firstName;
    private String lastName;
    private long phone;
}
