package com.app.library.rest;

import com.app.library.models.UserRequest;
import com.app.library.services.RegistrationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    @ApiOperation(value = "Rest API to register a User", notes = "An api endpoint that creates a profile entry")
    public ResponseEntity registerUser(@RequestBody UserRequest userRequest) {
       return registrationService.registerNewUserAccount(userRequest);
    }

    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "Rest API to delete a User", notes = "An api endpoint that delete a profile entry")
    public void deleteUser(@RequestParam String username) {
        registrationService.deleteUser(username);
    }

    @GetMapping("/getUser")
    @ApiOperation(value = "Rest API to delete a User", notes = "An api endpoint that delete a profile entry")
    public ResponseEntity getUser(@RequestParam String username) {
        return registrationService.getUser(username);
    }

    @GetMapping("/getUsers")
    @ApiOperation(value = "Rest API to get all registered users", notes = "An api endpoint that gets all profile entries")
    @Cacheable(value = "Registration-getUsers", unless = "#result == null")
    public Object getUsers() {
        return registrationService.getUsers().getBody();

    }

    @PutMapping("/updateUser")
    @ApiOperation(value = "Rest API to update a User", notes = "An api endpoint that update profile entriy")
    public ResponseEntity updateUser(@RequestBody UserRequest userRequest) {
        return registrationService.updateExistingUser(userRequest);
    }
}
