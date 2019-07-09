package bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo
public class Pm extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 2) {
            message(sender, ChatColor.RED + "Usage /pm <player> <message>");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target != null) {
            String message = ChatColor.translateAlternateColorCodes('&', join(args));
            String format = ChatColor.GOLD + "[%s" + ChatColor.GOLD + " -> %s" + ChatColor.GOLD + "]";

            message(target, String.format(format, sender.getName(), "me") + message);
            message(sender, String.format(format, "me", target.getName()) + message);

        } else message(sender, "That player is not currently online!");

        return false;
    }

    public String join(String[] args) {
        String result = " ";

        int i = 0;
        for (String s : args) {
            if (i > 0) result = result + " " + s;

            i++;
        }

        return result.trim();
    }

}