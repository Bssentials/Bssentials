package bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import bssentials.api.User;

@CmdInfo(aliases = {"sun", "rain"}, onlyPlayer = true)
public class Weather extends BCommand {

    @Override
    public boolean onCommand(User user, String l, String[] args) {
        Player p = (Player) (user.getBase());
        if (l.equalsIgnoreCase("sun")) {
            p.getWorld().setStorm(false);
            message(user, "Set weather to CLEAR in " + p.getWorld().getName());
            return true;
        }
        if (l.equalsIgnoreCase("rain")) {
            p.getWorld().setStorm(true);
            message(user, "Set weather to STORM in " + p.getWorld().getName());
            return true;
        }

        if (args.length < 1) {
            message(user, ChatColor.DARK_RED + "Usage: /weather <sun|rain> [world]");
            return true;
        }
        World w = p.getWorld();
        if (args.length > 1) w = Bukkit.getWorld(args[1]);

        if (args[0].equalsIgnoreCase("sun")) {
            w.setStorm(false);
            message(user, "Set weather to CLEAR in " + w.getName());
            return true;
        }
        if (args[0].equalsIgnoreCase("rain")) {
            w.setStorm(true);
            message(user, "Set weather to STORM in " + w.getName());
            return true;
        }
        return true;
    }

}