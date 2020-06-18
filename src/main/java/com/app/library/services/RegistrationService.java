package com.app.library.services;

import com.app.library.models.UserRequest;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {
    ResponseEntity registerNewUserAccount(UserRequest accountDto);

    void deleteUser(String username);

    ResponseEntity updateExistingUser(UserRequest accountDto);

    ResponseEntity getUsers();

    ResponseEntity getUser(String username);

    ResponseEntity loginUser(String username, String password);
}
