package com.app.library.rest;

import com.app.library.models.User;
import com.app.library.services.RegistrationService;
import com.app.library.utils.UtilHelpers;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        response = registrationService.registerNewUserAccount(userRequest);

        if (response != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
