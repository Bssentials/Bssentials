package ml.bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewNick implements CommandExecutor {
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    	if(!(sender instanceof Player)){
    		sender.sendMessage("You are not a player");
    		return false;
    	}
    	Player player = (Player) sender;
    	if (cmd.getName().equalsIgnoreCase("ViewNick")) {
    		if (args.length == 0) {
    			sender.sendMessage("/viewnick <player>");
    		} else {
    			//@SuppressWarnings("deprecation")
    			Player target = player.getServer().getPlayer(args[0]);
    			String targetsnick = target.getDisplayName();
    			String line = "------------";
    			sender.sendMessage(ChatColor.GOLD + line);
    			sender.sendMessage(ChatColor.GOLD + "Real Name: " + target.getName());
    			sender.sendMessage(ChatColor.GOLD + "Nick Name: " + targetsnick);
    			sender.sendMessage(ChatColor.GOLD + line);
    		}
        }
		return true;
    }
}
