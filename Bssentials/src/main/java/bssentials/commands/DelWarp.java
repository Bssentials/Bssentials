package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CmdInfo
public class DelWarp extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 1) {
            message(sender, ChatColor.RED + "Usage: /delwarp <warp>");
            return true;
        }

        message(sender, getPlugin().getWarps().removeWarp(args[0]) ? 
                (ChatColor.GREEN + "Warp removed!") : (ChatColor.DARK_RED + "Unable to remove warp!"));
        return true;
    }

}