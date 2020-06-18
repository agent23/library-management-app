package com.app.library.interfaces.impl;

import com.app.library.config.MailingConfig;
import com.app.library.interfaces.repos.UserRepository;
import com.app.library.models.UserRequest;
import com.app.library.models.UserResponse;
import com.app.library.services.RegistrationService;
import com.app.library.utils.UtilHelpers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ResponseEntity registerNewUserAccount(UserRequest userRequest) {

        if (UtilHelpers.validateRequest(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mandatory fields are empty!");
        if (!UtilHelpers.validatePassword(userRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be 8 or more characters long, have " +
                    "special characters, at least 1 Lowercase and 1 Uppercase character!");
        if (!UtilHelpers.validateEmailAdd(userRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address!");

        if (getUser(userRequest.getUsername()).getStatusCode().value() == 200)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist! Login?");

        UserRequest inserted = userRepository.insert(setUserDetails(userRequest));

        if (inserted != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(setUserResponse(inserted));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @Override
    public ResponseEntity updateExistingUser(UserRequest userRequest) {
        if (UtilHelpers.validateRequest(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mandatory fields are empty!");
        if (!UtilHelpers.validateContactDetails(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verify your mobile number!");
        if (!UtilHelpers.validatePassword(userRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be 8 or more characters long, have " +
                    "special characters, at least 1 Lowercase and 1 Uppercase character!");
        UserRequest saved = userRepository.save(setUserDetails(userRequest));

        if (saved != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(setUserResponse(saved));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity getUser(String username) {
        if (!UtilHelpers.isUsernameEmpty(username))
            throw new IllegalArgumentException("Username can not be empty");

        Optional<UserRequest> user = userRepository.findById(username);
        if (user.isPresent()) {
            UserRequest request = user.get();
            return ResponseEntity.ok().body(setUserResponse(request));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @Override
    public ResponseEntity loginUser(String username, String password) {
        PasswordManager passwordManager = new PasswordManager();

        if (!validateParams(username, password))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and Password must not be empty");

        Optional<UserRequest> user = userRepository.findById(username);

        if (user.isPresent()) {
            if (passwordManager.comparePasswords(password, user.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body(setUserResponse(user.get()));
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password or Username is incorrect!");
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password or Username is incorrect!");
    }

    @Override
    public ResponseEntity getUsers() {
        List<UserRequest> userRequests = userRepository.findAll();
        if (!userRequests.isEmpty()) {
            List<UserResponse> userResponses = new ArrayList<>();
            for (UserRequest request : userRequests) {
                userResponses.add(setUserResponse(request));
            }
            return ResponseEntity.status(HttpStatus.OK).body(userResponses);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found");
    }

    @Override
    public void deleteUser(String username) {
        if (!UtilHelpers.isUsernameEmpty(username))
            throw new IllegalArgumentException("Username can not be empty");
        userRepository.deleteById(username);
    }

    private UserRequest setUserDetails(UserRequest accountDto) {
        PasswordManager passwordManager = new PasswordManager();
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(accountDto.getEmail());
        userRequest.setPhone(accountDto.getPhone());
        userRequest.setPassword(passwordManager.encodePassword(accountDto.getPassword()));
        userRequest.setUsername(accountDto.getUsername());
        return userRequest;
    }

    private UserResponse setUserResponse(UserRequest request) {
        return new UserResponse(request.getUsername(), request.getEmail(), request.getPhone());
    }

    private boolean validateParams(String username, String password) {
        boolean flag = true;
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            flag = false;
        }
        return flag;
    }
}
