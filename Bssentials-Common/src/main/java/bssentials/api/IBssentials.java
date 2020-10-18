package bssentials.api;

import java.util.Collection;

import bssentials.configuration.BssConfiguration;

public interface IBssentials {

    public IWarps getWarps();

    public BssConfiguration getMainConfig();

    public User getUser(Object base);

    public String getVersion();

    public void broadcastMessage(String string);

    public Collection<IWorld> getWorlds();

    public int getOnlinePlayerCount();

    public IWorld getWorld(String string);

}