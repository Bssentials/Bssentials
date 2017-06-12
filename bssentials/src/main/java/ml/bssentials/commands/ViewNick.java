package ml.bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewNick extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ViewNick")) {
            if (args.length == 0) {
                sender.sendMessage("/viewnick <player>");
            } else {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                String targetsnick = target.getDisplayName();
                String line = "------------";
                sendMessage(sender, ChatColor.GOLD + line);
                sendMessage(sender, ChatColor.GOLD + "Real Name: " + target.getName());
                sendMessage(sender, ChatColor.GOLD + "Nick Name: " + targetsnick);
                sendMessage(sender, ChatColor.GOLD + line);
            }
        }
        return true;
    }
}
