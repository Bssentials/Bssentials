package bssentials.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true, aliases = {"homelist"})
public class Homes extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {

        Set<String> homes = user.getHomes();
        message(user, ChatColor.GREEN + "Home Name: (World, X, Y, Z)");
        for (String home : homes) {
            Location loc = user.getHome(home);
            message(user, "- Home \"" + home + "\": " + "(" + loc.getWorld().getName() + "," + (int)loc.getX() + "," + loc.getY() + "," + (int)loc.getZ() + ")");
        }

        return false;
    }

}