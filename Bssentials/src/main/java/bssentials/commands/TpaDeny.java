package bssentials.commands;

import org.bukkit.ChatColor;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true, aliases = {"tpdeny", "tpno", "tpano"})
public class TpaDeny extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (Tpa.tpaMap.containsKey(user.getName(false))) {
            User from = getUserByName( Tpa.tpaMap.get(user.getName(false)) );
            user.sendMessage(ChatColor.GREEN + "Teleport Request denied.");
            message(from, ChatColor.GREEN + "Teleport Request denied.");
            Tpa.tpaMap.remove(user.getName(false));
        }
        return message(user, ChatColor.RED + "You have no teleport requests to deny");
    }

}
