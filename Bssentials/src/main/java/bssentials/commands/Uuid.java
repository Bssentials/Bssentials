package bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Uuid extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                message(sender, "Console Usage: /uuid <player>");
                return false;
            }
            Player p = (Player) sender;
            sender.sendMessage("Your UUID: " + p.getUniqueId().toString());
            return true;
        } else if (args.length == 1) {
            try {
                String uuid = Bukkit.getPlayer(args[0]).getUniqueId().toString();
                message(sender, ChatColor.GREEN + "UUID of " + args[0] + ": " + uuid);
            } catch (NullPointerException e) {
                message(sender, ChatColor.RED + "Player not online.");
            }
        }
        return true;
    }

}