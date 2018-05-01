package bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Enderchest extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("enderchest")) {
            if (args.length == 0) {
                Player p = (Player) sender;
                p.sendMessage("Opening your enderchest");
                p.openInventory(p.getEnderChest());
                return true;
            } else if (args.length == 1) {
                Player p = (Player) sender;
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null || !target.isOnline()) {
                    p.sendMessage(ChatColor.RED + "Player not found");
                    return true;
                }
                p.sendMessage("Opening " + target.getName() + "'s enderchest.");
                p.openInventory(target.getEnderChest());
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }
}