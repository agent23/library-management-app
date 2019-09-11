package com.app.library.services;

import com.app.library.models.User;

public interface RegistrationService {
    User registerNewUserAccount(User accountDto);

    void deleteUser(String username);

    User updateExistingUser(User accountDto);
}
