package com.app.library;

import com.app.library.interfaces.impl.RegistrationServiceImpl;
import com.app.library.interfaces.repos.UserRepository;
import com.app.library.models.UserRequest;
import com.app.library.rest.RegistrationController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("unit")
@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RegistrationServiceImpl registrationService;

    @InjectMocks
    RegistrationController registrationController;

    @Test
    public void ensure_mandatory_fields_are_not_null() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("");
        userRequest.setPassword("");
        userRequest.setPhone("");
        userRequest.setEmail("");

        ResponseEntity response = registrationController.registerUser(userRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void successfulRegistration() {
        UserRequest userRequest = getUser();

        Mockito.when(registrationService.registerNewUserAccount(userRequest)).thenReturn(ResponseEntity.ok().body(userRequest));

        ResponseEntity response = registrationService.registerNewUserAccount(userRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void validate_mobile_phone_length_to_be_10_and_without_letters() {
        UserRequest userRequest = getUser();

        userRequest.setPhone("07812jw4");
        ResponseEntity response = registrationService.registerNewUserAccount(userRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void when_interface_failed_to_save_a_user() {
        UserRequest userRequest = getUser();

        ResponseEntity response = registrationService.registerNewUserAccount(userRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    private UserRequest getUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Libenyane");
        userRequest.setPassword("#Mohale95");
        userRequest.setPhone("0781234567");
        userRequest.setEmail("email@gmail.com");
        // userRequest.setAddress(new Address("ZA", 2194, 166, "Bram"));
        //userRequest.setContactDetails(new ContactDetails("+27", "0781234567", "email@gmail.com"));
        return userRequest;
    }
}
