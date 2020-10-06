package bssentials.api;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;

public interface IWarps {

    boolean teleportToWarp(User user, String warpName);

    int getCount();

    boolean isSpawnSet();

    File getWarpFile(String name);

    boolean removeWarp(String name);

    void setWarp(String name, Location loc) throws IOException;

    Location getWarp(String name);

    File[] getWarpFiles();

    boolean isEmpty();

}