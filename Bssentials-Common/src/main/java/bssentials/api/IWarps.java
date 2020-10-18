package bssentials.api;

import java.io.File;
import java.io.IOException;

public interface IWarps {

    boolean teleportToWarp(User user, String warpName);

    int getCount();

    boolean isSpawnSet();

    File getWarpFile(String name);

    boolean removeWarp(String name);

    void setWarp(String name, Location location) throws IOException;

    Location getWarp(String name);

    File[] getWarpFiles();

    boolean isEmpty();

}