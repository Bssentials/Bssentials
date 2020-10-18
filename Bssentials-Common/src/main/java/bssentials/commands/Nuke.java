package bssentials.commands;

import bssentials.api.User;

@CmdInfo
public class Nuke extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (label.equalsIgnoreCase("nuke")) {
            if(args.length == 1) {
                try {
                    User target = getUserByName(args[0]);
                    nuke(target);
                    user.sendMessage("Tnt rain!");
                    target.sendMessage("Tnt rain!");
                } catch (NullPointerException e) {
                    user.sendMessage("Target player is offline!");
                }
            } else if(args.length == 0) {
                if(user.isPlayer()) {
                    nuke(user);
                    user.sendMessage("TNT RAIN!");
                } else user.sendMessage("Usage: /nuke <player>");
            } else user.sendMessage("Usage: /nuke <player>");

            return true;
        }
        return false;
    }

    public void nuke(User user) {
        for (int i = 0; i < 5; i++)
            user.getWorld().spawnEntity(user.getLocation(), "PRIMED_TNT", null);
    }

}