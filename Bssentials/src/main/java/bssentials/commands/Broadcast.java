package bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import bssentials.utils.StringUtils;

public class Broadcast extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length == 0) {
            message(sender, "Usage: /broadcast <message>");
            return true;
        } else {
            String message = StringUtils.join(args, " ");
            Bukkit.broadcastMessage("&4[Broadcast] " + ChatColor.RESET + message);
        }
        return true;
    }

}
