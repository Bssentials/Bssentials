package bssentials.commands;

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
            user.sendMessage("&aUUID of " + args[0] + ": " + uuid);
        } catch (NullPointerException e) {
            user.sendMessage("&4Player not online.");
        }
        return true;
    }

}