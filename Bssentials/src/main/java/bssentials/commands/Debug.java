package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import bssentials.Bssentials;

public class Debug extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 1) {
            message(sender, ChatColor.RED + "Usage: /debug <true|false>");
            return true;
        }
        Bssentials.get().debug = Boolean.valueOf(args[0]);
        message(sender, "Debug mode changed to: " + args[0]);
        message(sender, "Will reset on restart.");

        return true;
    }

    @Override
    public boolean hasPerm(CommandSender sender, Command cmd) {
        return sender.isOp();
    }
}
