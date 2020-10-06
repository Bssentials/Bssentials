package bssentials.commands;

import org.bukkit.ChatColor;

import bssentials.api.User;

@CmdInfo(aliases = {"eposition"})
public class GetPos extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length <= 0)
            return message(user, user.isPlayer() ? ChatColor.GREEN + "Pos: " + user.getLocation().toString() : "Usage: /getpos <player>");
        return message(user, ChatColor.GREEN + "Pos: " + getUserByName(args[0]).getLocation().toString());
    }

}
