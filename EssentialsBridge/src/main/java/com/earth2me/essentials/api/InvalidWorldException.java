package com.earth2me.essentials.api;

public class InvalidWorldException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String world;

    public InvalidWorldException(final String world) {
        super("Invalid word: " + world);
        this.world = world;
    }

    public String getWorld() {
        return this.world;
    }
}
