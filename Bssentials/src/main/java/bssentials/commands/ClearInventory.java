package bssentials.commands;

import bssentials.api.User;

@CmdInfo(aliases = {"clear"})
public class ClearInventory extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length > 2) {
            message(user, "Usage: /clear [material]");
            return false;
        }

        if (args.length > 0) {
            message(user, "Removing " + args[0] + " from inventory");
            user.clearInventory(args[0]);
            return true;
        }
        message(user, "Clearing inventory");
        user.clearInventory(null);
        
        return false;
    }

}