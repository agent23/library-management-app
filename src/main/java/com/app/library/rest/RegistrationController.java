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
        User response = null;
        if (validateRequest(userRequest))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mandatory fields are empty");
        response = validateContactDetails(userRequest, response);
        if (response != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private User validateContactDetails(User userRequest, User response) {
        if (userRequest.getContactDetails() != null) {
            if (userRequest.getContactDetails().getPhone() != null) {
                if (validateMobile(userRequest.getContactDetails().getPhone())) {
                    response = registrationService.registerNewUserAccount(userRequest);
                }
            }
        }
        return response;
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
