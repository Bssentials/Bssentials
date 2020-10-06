package bssentials.commands;

import org.bukkit.ChatColor;

import bssentials.api.User;

@CmdInfo
public class Nick extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 1) {
            message(user, ChatColor.DARK_RED + "Usage: /nick <nickname>");
            return false;
        }

        String nick = ChatColor.translateAlternateColorCodes('&', args[0].replaceAll("[SPACECHAR]", " "));
        if (nick.equals("reset"))
            nick = user.getName(false);

        try {
            user.setNick(nick);
            user.sendMessage("Nickname set to: " + nick);
        } catch (Exception e) {
            user.sendMessage(ChatColor.RED + "Nickname is too long!");
        }
        return true;
    }

}
