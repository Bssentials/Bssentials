package bssentials.commands;

import org.bukkit.ChatColor;
import bssentials.api.User;

@CmdInfo(onlyPlayer = true, aliases = {"xp"})
public class Exp extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length == 1) {
            int oldvalue = user.getExpLevel();
            user.setExpLevel(user.getExpLevel() + Integer.valueOf(args[0]));
            user.sendMessage(ChatColor.GREEN + "Changed your exp from " + oldvalue + " to " + user.getExpLevel());
            return true;
        }
        return false;
    }

}