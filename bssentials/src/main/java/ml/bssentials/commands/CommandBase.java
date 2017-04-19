package ml.bssentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import ml.bssentials.api.BssUtils;

public abstract class CommandBase implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (onlyPlayer()) {
            if(!(sender instanceof Player)){
                sender.sendMessage("[Bssentials] Player only command!");
                return false;
            }
        }
        if (!(senderHasPerm(sender, cmd))){
            BssUtils.noPermMsg(sender, cmd);
            return false;
        }

        return onCommand(sender, cmd, args);
    }

    public boolean onlyPlayer() {
        return false;
    }

    public boolean senderHasPerm(CommandSender sender, Command cmd) {
        return BssUtils.hasPermForCommand(sender, cmd.getName().toLowerCase());
    }

    public void sendMessage(CommandSender cs, String message) {
        if (cs instanceof Player) {
            cs.sendMessage(message);
        } else {
            cs.sendMessage(ChatColor.stripColor(message));
        }
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String[] args);
}
