package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bssentials.Bssentials;
import bssentials.api.User;
import bssentials.configuration.Configs;

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
        try {
            user.setNick(nick);
            Player plr = ((Player) sender);
            plr.setDisplayName(nick);
            if (Configs.MAIN.getBoolean("change-playerlist", true))
                plr.setPlayerListName(plr.getDisplayName());
            message(sender, "Nickname set to: " + nick);
        } catch (Exception e) {
            message(sender, ChatColor.RED + "Nickname is too long!");
        }
        return true;
    }

}
