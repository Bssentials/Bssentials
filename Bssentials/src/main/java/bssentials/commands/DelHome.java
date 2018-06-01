package bssentials.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bssentials.User;

public class DelHome extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /delhome <name>");
            return false;
        }
        String home = args[0];
        Player p = (Player) sender;
        User.getByName(p.getName()).delHome(home);
        return false;
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }
}