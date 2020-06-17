package com.app.library.services;

import com.app.library.models.User;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {
    ResponseEntity registerNewUserAccount(User accountDto);

    void deleteUser(String username);

    ResponseEntity updateExistingUser(User accountDto);

    ResponseEntity getUsers();

    ResponseEntity getUser(String username);

    ResponseEntity loginUser(String username, String password);
}
