package com.app.library.interfaces.impl;

import com.app.library.config.MailingConfig;
import com.app.library.interfaces.repos.UserRepository;
import com.app.library.models.User;
import com.app.library.services.RegistrationService;
import com.app.library.utils.UtilHelpers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;
    private MailingConfig mailingConfig;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, MailingConfig mailingConfig) {
        this.userRepository = userRepository;
        this.mailingConfig = mailingConfig;
    }

    @Override
    public ResponseEntity registerNewUserAccount(User userRequest) {

        if (UtilHelpers.validateRequest(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mandatory fields are empty!");
        if (!UtilHelpers.validatePassword(userRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be 8 or more characters long, have " +
                    "special characters, at least 1 Lowercase and 1 Uppercase character!");
        if (!UtilHelpers.validateEmailAdd(userRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address!");

        if (getUser(userRequest.getUsername()).getStatusCode().value() == 200)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist! Login?");

        User inserted = userRepository.insert(setUserDetails(userRequest));

        if (inserted != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(inserted);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @Override
    public ResponseEntity updateExistingUser(User userRequest) {
        if (UtilHelpers.validateRequest(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mandatory fields are empty!");
        if (!UtilHelpers.validateContactDetails(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verify your mobile number!");
        if (!UtilHelpers.validatePassword(userRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be 8 or more characters long, have " +
                    "special characters, at least 1 Lowercase and 1 Uppercase character!");
        User saved = userRepository.save(setUserDetails(userRequest));

        if (saved != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity getUser(String username) {
        if (!UtilHelpers.isUsernameEmpty(username))
            throw new IllegalArgumentException("Username can not be empty");

        Optional<User> user = userRepository.findById(username);
        if (user.isPresent())
            return ResponseEntity.ok().body(user.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not fount");
    }

    @Override
    public ResponseEntity loginUser(String username, String password) {
        PasswordManager passwordManager = new PasswordManager();

        if (!validateParams(username, password))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and Password must not be empty");

        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()) {
            if (passwordManager.comparePasswords(password, user.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body(user);
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password or Username is incorrect!");
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password or Username is incorrect!");
    }

    @Override
    public ResponseEntity getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Override
    public void deleteUser(String username) {
        if (!UtilHelpers.isUsernameEmpty(username))
            throw new IllegalArgumentException("Username can not be empty");
        userRepository.deleteById(username);
    }

    private User setUserDetails(User accountDto) {
        PasswordManager passwordManager = new PasswordManager();
        User user = new User();
        user.setEmail(accountDto.getEmail());
        user.setPhone(accountDto.getPhone());
        user.setPassword(passwordManager.encodePassword(accountDto.getPassword()));
        user.setUsername(accountDto.getUsername());
        return user;
    }

    private boolean validateParams(String username, String password) {
        boolean flag = true;
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            flag = false;
        }
        return flag;
    }
}
