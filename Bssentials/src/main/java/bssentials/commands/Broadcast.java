package bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import bssentials.api.User;
import bssentials.utils.StringUtils;

@CmdInfo
public class Broadcast extends BCommand {

    @Override
    public boolean onCommand(User sender, String label, String[] args) {
        if (args.length == 0) {
            message(sender, "Usage: /broadcast <message>");
            return true;
        }

        String message = StringUtils.join(args, " ");
        Bukkit.broadcastMessage("&4[Broadcast] " + ChatColor.RESET + message);
        return true;
    }

}
