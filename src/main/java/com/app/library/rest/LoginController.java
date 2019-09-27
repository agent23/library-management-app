package com.app.library.rest;

import com.app.library.interfaces.impl.PasswordManager;
import com.app.library.models.User;
import com.app.library.services.RegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class LoginController {
    private RegistrationService registrationService;


    @Autowired
    public LoginController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestParam String username, @RequestParam String password) {
        PasswordManager passwordManager = new PasswordManager();

        if (!validateParams(username, password))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and Password must not be empty");

        Optional<User> user = registrationService.getUser(username);

        if (user.isPresent()) {
            if (passwordManager.comparePasswords(password, user.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body("Logged In");
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password or Username is incorrect!");
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password or Username is incorrect!");
    }

    private boolean validateParams(String username, String password) {
        boolean flag = true;
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            flag = false;
        }
        return flag;
    }
}
