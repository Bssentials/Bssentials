package com.earth2me.essentials.spawn;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class EssentialsSpawn extends JavaPlugin implements IEssentialsSpawn {

    @Override
    public void onEnable() {
        getLogger().info("EssentialsSpawn is included in Bssentials itself.");
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args) {
        return true;
    }

    @Override
    public void setSpawn(Location loc, String group) {;
    }

    @Override
    public Location getSpawn(String group) {
        return null;
    }
}
