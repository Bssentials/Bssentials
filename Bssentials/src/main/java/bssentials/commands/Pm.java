package bssentials.commands;

import org.bukkit.ChatColor;

import bssentials.api.User;

@CmdInfo
public class Pm extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 2) {
            user.sendMessage(ChatColor.RED + "Usage /pm <player> <message>");
            return false;
        }
        User target = getUserByName(args[0]);
        if(target != null) {
            String message = ChatColor.translateAlternateColorCodes('&', join(args));
            String format = ChatColor.GOLD + "[%s" + ChatColor.GOLD + " -> %s" + ChatColor.GOLD + "]";

            target.sendMessage(String.format(format, user.getName(true), "me") + message);
            user.sendMessage(String.format(format, "me", target.getName(true)) + message);
        } else user.sendMessage("That player is not currently online!");

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