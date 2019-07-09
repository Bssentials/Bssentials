package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bssentials.api.Econ;

@CmdInfo(aliases = {"bal", "money"})
public class Balance extends BCommand {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (!(sender instanceof Player)) {
            message(sender, "Console is not a player");
            return true;
        }

        try {
            message(sender, ChatColor.GREEN + "Balance: " + ChatColor.RED + Econ.getMoney(sender.getName()));
        } catch (Exception e) {
            message(sender, ChatColor.DARK_RED + "Exception while getting balance: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

}