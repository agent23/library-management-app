package com.app.library.services;

import com.app.library.models.User;

import java.util.List;
import java.util.Optional;

public interface RegistrationService {
    User registerNewUserAccount(User accountDto);

    void deleteUser(String username);

    User updateExistingUser(User accountDto);

    List<User> getUsers();

    Optional<User> getUser(String username);
}
