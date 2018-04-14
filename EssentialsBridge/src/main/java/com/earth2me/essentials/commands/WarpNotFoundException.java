package com.earth2me.essentials.commands;

public class WarpNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public WarpNotFoundException() {
        super("Essentials API Execption: warpNotExist");
    }

    public WarpNotFoundException(String message) {
        super(message);
    }
}