package bssentials.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class SetWarp extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 1)
            return message(user, ChatColor.RED + "Usage: /setwarp <warp>");

        Location loc = user.getLocation();

        try {
            getPlugin().getWarps().setWarp(args[0], loc);
            user.sendMessage(ChatColor.GREEN + "Warp set!");
        } catch (IOException e) {
            e.printStackTrace();
            user.sendMessage(ChatColor.RED + "Unable to write to " + args[0] + ".yml");
        }
        return true;
    }

}