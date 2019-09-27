package com.app.library.rest;

import com.app.library.models.User;
import com.app.library.services.RegistrationService;
import com.app.library.utils.UtilHelpers;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    @ApiOperation(value = "Rest API to register a user", notes = "An api endpoint that creates a profile entry")
    public ResponseEntity registerUser(@RequestBody User userRequest) {
        User response;
        if (UtilHelpers.validateRequest(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mandatory fields are empty!");
        if (!UtilHelpers.validateContactDetails(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verify your mobile number!");
        if (!UtilHelpers.validatePassword(userRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be 8 or more characters long, have " +
                    "special characters, at least 1 Lowercase and 1 Uppercase character!");
        if (!UtilHelpers.validateEmailAdd(userRequest.getContactDetails().getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address!");

        response = registrationService.registerNewUserAccount(userRequest);

        if (response != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "Rest API to delete a user", notes = "An api endpoint that delete a profile entry")
    public void deleteUser(@RequestParam String username) {
        if (!UtilHelpers.isUsernameEmpty(username))
            throw new IllegalArgumentException("Username can not be empty");
        try {
            registrationService.deleteUser(username);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Something went wrong: " + e.getMessage());
        }
    }

    @GetMapping("/getUser")
    @ApiOperation(value = "Rest API to delete a user", notes = "An api endpoint that delete a profile entry")
    public ResponseEntity getUser(@RequestParam String username) {
        if (!UtilHelpers.isUsernameEmpty(username))
            throw new IllegalArgumentException("Username can not be empty");
        try {
            Optional<User> user = registrationService.getUser(username);
            if (user.isPresent())
                return ResponseEntity.ok().body(user.get());
        } catch (Exception e) {
            throw new UnsupportedOperationException("Something went wrong: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not fount");
    }

    @GetMapping("/getUsers")
    @ApiOperation(value = "Rest API to get all registered users", notes = "An api endpoint that gets all profile entries")
    public ResponseEntity getUsers() {
        List<User> users = registrationService.getUsers();
        if (users.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/updateUser")
    @ApiOperation(value = "Rest API to update a user", notes = "An api endpoint that update profile entriy")
    public ResponseEntity updateUser(@RequestBody User userRequest) {
        User response;
        if (UtilHelpers.validateRequest(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mandatory fields are empty!");
        if (!UtilHelpers.validateContactDetails(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verify your mobile number!");
        if (!UtilHelpers.validatePassword(userRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be 8 or more characters long, have " +
                    "special characters, at least 1 Lowercase and 1 Uppercase character!");

        response = registrationService.updateExistingUser(userRequest);

        if (response != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
