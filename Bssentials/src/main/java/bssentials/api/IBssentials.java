package bssentials.api;

import bssentials.configuration.BssConfiguration;

public interface IBssentials {

    public IWarps getWarps();

    public BssConfiguration getConfig();

    public User getUser(Object base);

}