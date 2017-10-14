package ml.bssentials.api;

public enum ServerType {
    CRAFTBUKKT, SPIGOT, PAPER, UNKNOWN;

    public static ServerType get() {
        return valueOf(BssUtils.getServerMod());
    }
}