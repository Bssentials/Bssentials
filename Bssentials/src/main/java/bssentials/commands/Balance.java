package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bssentials.eco.Eco;

public class Balance extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (!(sender instanceof Player)) {
            message(sender, "CONSOLE Balance: \u221E");
        }
        try {
            message(sender, ChatColor.GREEN + "Balance: " + ChatColor.RED + Eco.getMoney(sender.getName()));
        } catch (Exception e) {
            message(sender, ChatColor.DARK_RED + "Exception while getting balance: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onlyPlayer() {
        return false;
    }
}
