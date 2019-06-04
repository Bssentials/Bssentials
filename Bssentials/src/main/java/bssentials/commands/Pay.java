package bssentials.commands;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import bssentials.api.User;

public class Pay extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 2) {
            message(sender, ChatColor.DARK_RED + "Usage: /pay <player> <amount>");
            return true;
        }

        User u1 = User.getByName(sender.getName());
        User u2 = User.getByName(args[0]);

        BigDecimal d = new BigDecimal(args[1]);
        u1.setMoney(u1.getMoney().subtract(d));
        u2.setMoney(u2.getMoney().add(d));

        message(sender, ChatColor.GREEN + "$" + args[1] + " sent to " + args[0]);
        message(Bukkit.getPlayer(args[0]), ChatColor.GREEN + args[0] + " has given you $" + args[1]);

        return true;
    }

}
