package com.earth2me.essentials.api;

public class UserDoesNotExistException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserDoesNotExistException(String name) {
        super("Essentials Bridge: userDoesNotExist: " + name);
    }
}
