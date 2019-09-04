package com.app.library.interfaces;

public interface IUser {
    void register(String username, String password, String phone);
    void update(String username, String password, String phone);
    void get(String username);
    void getAll();
}
