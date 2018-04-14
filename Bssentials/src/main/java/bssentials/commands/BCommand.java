package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bssentials.Bssentials;

public abstract class BCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (onlyPlayer()) {
            if(!(sender instanceof Player)){
                sender.sendMessage("Player only command!");
                return false;
            }
        }
        if (!(hasPerm(sender, cmd))){
            message(sender, ChatColor.RED + "No permission for command.");
            return false;
        }

        return onCommand(sender, cmd, args);
    }

    public boolean onlyPlayer() {
        return false;
    }

    public boolean hasPerm(CommandSender sender, Command cmd) {
        return Bssentials.get().hasPerm(sender, cmd);
    }

    public void message(CommandSender cs, String message) {
        if (cs instanceof Player) {
            cs.sendMessage(message);
        } else cs.sendMessage(ChatColor.stripColor(message));
    }

    @Deprecated
    public String getServerMod() {
        return "SPIGOT";
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String[] args);
}