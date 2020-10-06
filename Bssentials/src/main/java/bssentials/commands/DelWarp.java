package bssentials.commands;

import org.bukkit.ChatColor;

import bssentials.api.User;

@CmdInfo
public class DelWarp extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 1) {
            message(user, ChatColor.RED + "Usage: /delwarp <warp>");
            return true;
        }

        message(user, getPlugin().getWarps().removeWarp(args[0]) ? 
                (ChatColor.GREEN + "Warp removed!") : (ChatColor.DARK_RED + "Unable to remove warp!"));
        return true;
    }

}