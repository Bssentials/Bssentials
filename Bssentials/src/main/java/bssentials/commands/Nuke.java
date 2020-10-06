package bssentials.commands;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import bssentials.api.User;

@CmdInfo
public class Nuke extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (label.equalsIgnoreCase("nuke")) {
            if(args.length == 1) {
                try {
                    User target = getUserByName(args[0]);
                    nuke(target.getLocation());
                    user.sendMessage("Tnt rain!");
                    target.sendMessage("Tnt rain!");
                } catch (NullPointerException e) {
                    user.sendMessage("Target player is offline!");
                }
            } else if(args.length == 0) {
                if(user.isPlayer()) {
                    nuke(user.getLocation());
                    user.sendMessage("TNT RAIN!");
                } else user.sendMessage("Usage: /nuke <player>");
            } else user.sendMessage("Usage: /nuke <player>");

            return true;
        }
        return false;
    }

    public void nuke(Location l) {
        for (int i = 0; i < 5; i++)
            l.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
    }

}