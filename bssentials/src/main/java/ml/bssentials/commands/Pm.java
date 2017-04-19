package ml.bssentials.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pm extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pm")) {
            if (args.length == 0) {
                sendMessage(sender, ChatColor.GREEN + "[Bssentials]" + ChatColor.GRAY + " Usage /pm <player> <message>");
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                String message = StringUtils.join(args, " ");

                sendMessage(target, "["+sender.getName() + " -> " + "me] " + ChatColor.translateAlternateColorCodes('&', message));
                sendMessage(sender, "[me" + " -> " + target.getName() + "] " +  ChatColor.translateAlternateColorCodes('&', message));

            } else sendMessage(sender, "That player is not currently online!");
        }
        return true;
    }
}
