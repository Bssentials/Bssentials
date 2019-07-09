package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bssentials.api.User;

@CmdInfo
public class Nick extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 1) {
            message(sender, ChatColor.DARK_RED + "Usage: /nick <nickname>");
            return false;
        }

        User user = User.getByName(sender.getName());
        String nick = ChatColor.translateAlternateColorCodes('&', args[0].replaceAll("[SPACECHAR]", " "));
        user.setNick(nick);
        ((Player) sender).setDisplayName(nick);
        message(sender, "Nickname set to: " + nick);
        return true;
    }

}
