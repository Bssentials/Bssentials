package ml.bssentials.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import ml.bssentials.api.BssUtils;
import ml.bssentials.main.Bssentials;

public class BssentialsCmd extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("bssentials")) {
            if(!BssUtils.hasPermForCommand(sender, cmd.getName().toLowerCase())){
                BssUtils.noPermMsg(sender, cmd);
                return true;
            }
            PluginDescriptionFile des = Bssentials.getInstance().getDescription();
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "Bssentials " + ChatColor.GOLD + des.getVersion());
                sender.sendMessage(ChatColor.GREEN + "By: " + StringUtils.join(des.getAuthors(), ", "));
                return true;
            } else {
                switch (args[0]) {
                    case "version":
                    case "ver":
                        sender.sendMessage(ChatColor.GREEN + "Version: " + ChatColor.GOLD + des.getVersion());
                        break;
                    case "authors":
                        sender.sendMessage(ChatColor.GREEN + "Authors: " + ChatColor.GOLD + des.getAuthors());
                        break;
                    case "about":
                        sender.sendMessage(ChatColor.GREEN + "Description: " + ChatColor.GOLD + des.getDescription());
                        break;
                    default:
                        sender.sendMessage(ChatColor.RED + "Usage: /bssentials <version|authors|about>");
                        break;
                }
                return true;
            }
        }
        return true;
    }
}