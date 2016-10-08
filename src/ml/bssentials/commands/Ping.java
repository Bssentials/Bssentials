package ml.bssentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.main.Bssentials;

public class Ping implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    	if(!(sender instanceof Player)){
    		sender.sendMessage("You are not a player");
    		return false;
    	}

    	if (cmd.getName().equalsIgnoreCase("ping")) {
    		sender.sendMessage(Bssentials.PREFIX + "Still working on showing your ping in numbers");
    		sender.sendMessage("Pong! (lol, ping pong)");
    	}
		return true;
	}
}
