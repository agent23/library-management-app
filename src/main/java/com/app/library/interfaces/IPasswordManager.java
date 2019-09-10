package com.app.library.interfaces;


public interface IPasswordManager {
    String encodePassword(String password);
    boolean comparePasswords();
}
