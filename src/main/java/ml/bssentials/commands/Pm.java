package ml.bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.main.Bssentials;

public class Pm implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    	if(!(sender instanceof Player)){
    		sender.sendMessage("You are not a player");
    		return false;
    	}
		
		if (cmd.getName().equalsIgnoreCase("pm")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.GRAY + " Usage /pm <player> <message>");
                return false;
            }
            if (sender.hasPermission(Bssentials.PM_PERM) || sender.isOp()) {
				@SuppressWarnings("deprecation")
				Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    String message = "";
                    for(int i = 1; i != args.length; i++)
 
                        message += args[i] + " ";

                        target.sendMessage(sender.getName() + " -> " + target.getName() + ": " + ChatColor.translateAlternateColorCodes('&', message));
 
                        sender.sendMessage("me" + " -> " + target.getName() + " " + message);
 
                } else if(target == null) {
                    sender.sendMessage("That player is not currently online!");
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + "[Bssentials] " + ChatColor.RED + "You don't have permission: bssentials.command.pm");  
            }
        }
		return true;
	}
}
