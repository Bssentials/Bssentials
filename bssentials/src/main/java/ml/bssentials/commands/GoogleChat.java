package ml.bssentials.commands;

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

    @Override public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("[Bssentials] You are not a player");
            return false;
        }

        String TooManyArgs = "Too many args! Max 15! sorry.";
        String NoArgs = "No args!";
        String Perm = "No Permisson: bssentials.command."+cmd.getName().toLowerCase();

        if (cmd.getName().equalsIgnoreCase("BukkitDev")) sender.sendMessage("Deprecated");
        if (cmd.getName().equalsIgnoreCase("YouTube")) sender.sendMessage("Deprecated");
        if (cmd.getName().equalsIgnoreCase("MCWiki")) sender.sendMessage("Deprecated");

        //GOOGLE COMMAND
        if (cmd.getName().equalsIgnoreCase("Google")) {
            if (sender.hasPermission("googlechat.google")) {
                if (args.length > 0) {
                    sender.sendMessage("http://google.com/?gws_rd=ssl#q=" + StringUtils.join(args, "+"));
                } else if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + Bssentials.prefix + ChatColor.GOLD + " " + NoArgs);
                } else if (args.length < 15) sender.sendMessage(ChatColor.RED + TooManyArgs);
            } else sender.sendMessage(ChatColor.RED + Perm);
        }

        return true;
    }
}
