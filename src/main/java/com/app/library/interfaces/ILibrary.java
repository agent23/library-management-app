package com.app.library.interfaces;

public interface ILibrary {
    void save();
    void delete();
    void get(String ISBN);
    void getAll();
    void update();
}
