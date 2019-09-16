package com.app.library.rest;

import com.app.library.models.User;
import com.app.library.services.RegistrationService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (validateRequest(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mandatory fields are empty!");
        if (!validateContactDetails(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verify your mobile number!");
        if (!validatePassword(userRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be 8 or more characters long, have " +
                    "special characters, at least 1 Lowercase and 1 Uppercase character!");

        response = registrationService.registerNewUserAccount(userRequest);

        if (response != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private boolean validateContactDetails(User userRequest) {
        if (userRequest.getContactDetails() != null) {
            if (userRequest.getContactDetails().getPhone() != null) {
                if (validateMobile(userRequest.getContactDetails().getPhone())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean validatePassword(String password) {
        if (password.length() >= 8) {
            Pattern letter = Pattern.compile("[a-zA-Z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();
        } else
            return false;
    }

    private boolean validateRequest(User userRequest) {
        if (StringUtils.isBlank(userRequest.getUsername()) || StringUtils.isBlank(userRequest.getPassword())
                || userRequest.getContactDetails() == null)
            return true;
        return false;
    }

    private boolean validateMobile(String mobile) {
        boolean valid = true;
        if (mobile.length() != 10) {
            valid = false;
        }
        for (int i = 0; i < mobile.length(); i++) {
            if (Character.isLetter(mobile.charAt(i))) {
                valid = false;
                break;
            }
        }
        return valid;
    }
}
