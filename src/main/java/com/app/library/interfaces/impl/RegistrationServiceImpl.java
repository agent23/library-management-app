package com.app.library.interfaces.impl;

import com.app.library.interfaces.repos.UserRepository;
import com.app.library.models.User;
import com.app.library.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository) {
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
