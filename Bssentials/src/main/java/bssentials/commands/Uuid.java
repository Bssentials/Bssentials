package bssentials.commands;

import org.bukkit.ChatColor;

import bssentials.api.User;

@CmdInfo
public class Uuid extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length == 0) {
            if (!user.isPlayer()) {
                user.sendMessage("Console Usage: /uuid <player>");
                return false;
            }
            user.sendMessage("Your UUID: " + user.getUniqueId().toString());
            return true;
        }

        try {
            String uuid = getUserByName(args[0]).getUniqueId().toString();
            user.sendMessage(ChatColor.GREEN + "UUID of " + args[0] + ": " + uuid);
        } catch (NullPointerException e) {
            user.sendMessage(ChatColor.RED + "Player not online.");
        }
        return true;
    }

}