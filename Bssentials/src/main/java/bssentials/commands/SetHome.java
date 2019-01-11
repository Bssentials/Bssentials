package bssentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class SetHome extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /sethome <name>");
            return false;
        }
        String home = args[0];
        Player p = (Player) sender;
        User.getByName(p.getName()).setHome(home, p.getLocation());
        return false;
    }

}