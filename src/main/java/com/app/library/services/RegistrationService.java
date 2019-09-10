package com.app.library.services;


import com.app.library.interfaces.UserRepository;
import com.app.library.interfaces.impl.PasswordManager;
import com.app.library.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    private UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerNewUserAccount(User accountDto) {
        return userRepository.insert(setUserDetails(accountDto));
    }

    public User updateExistingUser(User accountDto) {
        return userRepository.save(setUserDetails(accountDto));
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public Optional<User> finUser(String username) {
        return userRepository.findById(username);
    }

    private User setUserDetails(User accountDto) {
        PasswordManager passwordManager = new PasswordManager();
        User user = new User();
        user.setAddress(accountDto.getAddress());
        user.setContactDetails(accountDto.getContactDetails());
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordManager.encodePassword(accountDto.getPassword()));
        user.setUsername(accountDto.getUsername());
        return user;
    }
}
