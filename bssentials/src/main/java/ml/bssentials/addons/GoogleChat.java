package ml.bssentials.addons;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.main.Bssentials;

/**
 * The plugin GoogleChat (http://dev.bukkit.org/bukkit-plugins/googlechat) added into Bssentials! 
 */
public class GoogleChat implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    	if(!(sender instanceof Player)){
    		sender.sendMessage("[Bssentials] You are not a player");
    		return false;
    	}
		
        String TooManyArgs = "Too many args! Max 15! sorry.";
        String NoArgs = "No args!";
        String Perm = "No Permisson: bssentials.command."+cmd.getName().toLowerCase();
        
        //BUKKITDEV COMMAND
        if (cmd.getName().equalsIgnoreCase("BukkitDev")) {
            if (sender.hasPermission(Bssentials.BUKKIT_PERM)) {
                if (args.length > 0) {
                    sender.sendMessage("http://dev.bukkit.org/bukkit-plugins/?search=" + StringUtils.join(args, "+"));
                } else if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + Bssentials.prefix + ChatColor.GOLD + " " + NoArgs);
                } else if (args.length < 15) {
                    sender.sendMessage(ChatColor.RED + TooManyArgs);
                }
            } else {
                sender.sendMessage(ChatColor.RED + Perm);
            }
        }
        
        //YOUTUBE COMMAND
        if (cmd.getName().equalsIgnoreCase("YouTube")) {
            if (sender.hasPermission(Bssentials.YOUTUBE_PERM)) {
                if (args.length > 0) {
                    sender.sendMessage("http://youtube.com/results?search_query=" + StringUtils.join(args, "+"));
                } else if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + Bssentials.prefix + ChatColor.GOLD + " " + NoArgs);
                } else if (args.length < 15) {
                    sender.sendMessage(ChatColor.RED + TooManyArgs);
                }
            } else {
                sender.sendMessage(ChatColor.RED + Perm);
            }
        }
        
        //GOOGLE COMMAND
        if (cmd.getName().equalsIgnoreCase("Google")) {
            if (sender.hasPermission(Bssentials.GOOGLE_PERM)) {
                if (args.length > 0) {
                    sender.sendMessage("http://google.com/?gws_rd=ssl#q=" + StringUtils.join(args, "+"));
                } else if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + Bssentials.prefix + ChatColor.GOLD + " " + NoArgs);
                } else if (args.length < 15) {
                    sender.sendMessage(ChatColor.RED + TooManyArgs);
                }
            } else {
                sender.sendMessage(ChatColor.RED + Perm);
            }
        }
        
        //MCWIKI COMMAND
        if (cmd.getName().equalsIgnoreCase("MCWiki")) {
        	if (sender.hasPermission(Bssentials.WIKI_PERM)) {
                	if (args.length > 0) {
                   		sender.sendMessage("http://minecraftwiki.net/wiki/" + StringUtils.join(args, (String)"_"));
                	} else if (args.length == 0) {
                    		sender.sendMessage(ChatColor.RED + Bssentials.prefix + ChatColor.GOLD + " " + NoArgs);
			} else if (args.length < 15) {
                    		sender.sendMessage(ChatColor.RED + TooManyArgs);
                	}
            	} else {
                	sender.sendMessage(ChatColor.RED + Perm);
            	}
       	}
    	
		return true;
	}
}
