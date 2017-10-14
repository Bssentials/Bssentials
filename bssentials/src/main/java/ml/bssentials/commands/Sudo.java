package ml.bssentials.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sudo extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sudo")) {
            if (args.length == 0) {
                sendMessage(sender, ChatColor.RED + "Usage: /sudo <player> <command>");
                return true;
            } else if (args.length > 1) {
                Player p = Bukkit.getServer().getPlayer(args[0]);
                if (!p.isOnline()) {
                    sendMessage(sender, ChatColor.RED + "Player is not online!");
                    return true;
                }

                String command = StringUtils.join(args).split(args[0])[1];

                if (command.startsWith("c:")) {
                    p.chat(command);
                    return true;
                }

                if (p.performCommand(command)) {
                    sendMessage(sender, ChatColor.GREEN + "Forced " + args[0] + " to run " + command);
                    return true;
                } else {
                    sendMessage(sender, ChatColor.RED + "Unable to run " + command);
                    return true;
                }
            }
            return true;
        }
        return false;
    }
}