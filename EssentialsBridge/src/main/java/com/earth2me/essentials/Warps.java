package com.earth2me.essentials;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.Server;
import com.earth2me.essentials.commands.WarpNotFoundException;
import bssentials.Bssentials;
import net.ess3.api.InvalidNameException;
import net.ess3.api.InvalidWorldException;

public class Warps implements IConf, net.ess3.api.IWarps {

    private final File warpsFolder;
    private final Bssentials bss;

    public Warps(Server server, File dataFolder) {
        warpsFolder = Bssentials.warpdir;
        warpsFolder.mkdirs(); // Bssentials: Fixed memory leak
        bss = Bssentials.get();

        reloadConfig();
    }

    @Override
    public boolean isEmpty() {
        return bss.getWarps().isEmpty();
    }

    @Override
    public Collection<String> getList() {
        return Arrays.asList(warpsFolder.list());
    }

    @Override
    public Location getWarp(String warp) throws WarpNotFoundException, InvalidWorldException {
        return bss.getWarps().getWarp(warp);
    }

    @Override
    public void setWarp(String name, Location loc) throws Exception {
        bss.getWarps().setWarp(name, loc);
    }

    @Override
    public void removeWarp(String name) throws Exception {
        bss.getWarps().removeWarp(name);
    }

    @Override
    public final void reloadConfig() {
    }

    // This is here for future 3.x api support. Not implemented here because storage is handled differently
    // BSSENTIALS: how is storage different? This should return the warpname.yml in warps dir.
    @Override
    public File getWarpFile(String name) throws InvalidNameException {
        return bss.getWarps().getWarpFile(name);
    }

    @Override
    public int getCount() {
        return bss.getWarps().getCount();
    }

    private static class StringIgnoreCase {
    }

}