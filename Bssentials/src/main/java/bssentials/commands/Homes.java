package bssentials.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true, aliases = {"homelist"})
public class Homes extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {

        User user = User.getByName(sender.getName());
        Set<String> homes = user.getHomes();
        message(sender, ChatColor.GREEN + "Home Name: (World, X, Y, Z)");
        for (String home : homes) {
            Location loc = user.getHome(home);
            message(sender, "- Home \"" + home + "\": " + "(" + loc.getWorld().getName() + "," + (int)loc.getX() + "," + loc.getY() + "," + (int)loc.getZ() + ")");
        }

        return false;
    }

}