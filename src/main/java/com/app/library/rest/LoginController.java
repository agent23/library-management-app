package com.app.library.rest;

import com.app.library.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    private RegistrationService registrationService;


    @Autowired
    public LoginController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    public Object loginUser(@RequestParam String username, @RequestParam String password) {
        return registrationService.loginUser(username, password).getBody();
    }


}
