package com.app.library.interfaces.impl;

import com.app.library.interfaces.repos.UserRepository;
import com.app.library.models.User;
import com.app.library.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerNewUserAccount(User accountDto) {
        return userRepository.insert(setUserDetails(accountDto));
    }

    @Override
    public User updateExistingUser(User accountDto) {
        return userRepository.save(setUserDetails(accountDto));
    }

    @Override
    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
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
