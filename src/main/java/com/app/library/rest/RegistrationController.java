package com.app.library.rest;

import com.app.library.models.User;
import com.app.library.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<User> registerUser(@RequestBody User userRequest) {
        return ResponseEntity.ok().body(registrationService.registerNewUserAccount(userRequest));
    }
}
