package com.app.library;

import com.app.library.models.Address;
import com.app.library.models.ContactDetails;
import com.app.library.models.User;
import com.app.library.rest.RegistrationController;
import com.app.library.services.RegistrationService;
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
    RegistrationService registrationService;

    @InjectMocks
    RegistrationController registrationController;

    @Test
    public void ensure_mandatory_fields_are_not_null() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setLastName("");
        user.setFirstName("");
        user.setAddress(null);
        user.setContactDetails(null);

        ResponseEntity response = registrationController.registerUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void successfulRegistration() {
        User user = getUser();

        Mockito.when(registrationService.registerNewUserAccount(user)).thenReturn(user);

        ResponseEntity response = registrationController.registerUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void when_interface_failed_to_save_a_user() {
        User user = getUser();

        ResponseEntity response = registrationController.registerUser(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    private User getUser() {
        User user = new User();
        user.setUsername("Libenyane");
        user.setPassword("Stephen");
        user.setLastName("Mohale");
        user.setFirstName("First Born");
        user.setAddress(new Address("ZA", 2194, 166, "Bram"));
        user.setContactDetails(new ContactDetails("+27", "0781234567", "email@gmail.com"));
        return user;
    }
}
