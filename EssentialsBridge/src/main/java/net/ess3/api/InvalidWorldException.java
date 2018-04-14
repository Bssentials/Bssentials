package net.ess3.api;

// TODO import static com.earth2me.essentials.I18n.tl;

public class InvalidWorldException extends Exception {
    private static final long serialVersionUID = 1L; // Bssentials
    private final String world;

    public InvalidWorldException(final String world) {
        // TODO super(tl("invalidWorld"));
        super("Invalid world");
        this.world = world;
    }

    public String getWorld() {
        return this.world;
    }
}
