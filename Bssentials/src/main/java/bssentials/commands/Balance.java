package bssentials.commands;

import org.bukkit.ChatColor;

import bssentials.api.Econ;
import bssentials.api.User;

@CmdInfo(aliases = {"bal", "money"})
public class Balance extends BCommand {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(User sender, String label, String[] args) {
        try {
            message(sender, ChatColor.GREEN + "Balance: " + ChatColor.RED + Econ.getMoney(sender.getName(false)));
        } catch (Exception e) {
            message(sender, ChatColor.DARK_RED + "Exception while getting balance: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

}