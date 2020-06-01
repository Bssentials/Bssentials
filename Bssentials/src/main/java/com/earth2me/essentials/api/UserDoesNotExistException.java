package com.earth2me.essentials.api;

public class UserDoesNotExistException extends Exception {

    public UserDoesNotExistException(String name) {
        super(name);
    }

}