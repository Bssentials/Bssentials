package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class Home extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        String home = "home";
        if (args.length > 0) home = args[0];

        Location l = user.getHome(home);
        if (null != l) {
            user.teleport(l);
        } else {
            message(user, ChatColor.RED + "Home not set!");
        }

        return false;
    }

}