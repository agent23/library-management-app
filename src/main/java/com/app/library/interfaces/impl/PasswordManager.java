package com.app.library.interfaces.impl;

import com.app.library.interfaces.IPasswordManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordManager implements IPasswordManager {

    private PasswordEncoder passwordEncoder;

    public PasswordManager() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean comparePasswords(String plainPass, String encodedPass) {
        return passwordEncoder.matches(plainPass, encodedPass);
    }
}
