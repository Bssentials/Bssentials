package bssentials.commands;

import java.util.HashMap;

import org.bukkit.ChatColor;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class Tpa extends BCommand {
    
    public static HashMap<String, String> tpaMap = new HashMap<>();

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length <= 0)
            return message(user, ChatColor.RED + "Usage: /tpa <player>");

        User target = getUserByName(args[0]);
        if (null == target)
            return message(user, ChatColor.RED + "Target \"" + args[0] + "\" not found.");

        tpaMap.put(target.getName(false), user.getName(false));
        user.sendMessage("&aRequest sent to " + args[0]);
        user.sendMessage("&aRequest will timeout after two minutes");
        target.sendMessage("&a" + user.getName(true) + " is requesting to teleport to you");
        target.sendMessage("&aType /tpaccept to accept the request or");
        target.sendMessage("&aType /tpadeny to deny the request");
        return false;
    }

}