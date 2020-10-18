package bssentials.commands;

import bssentials.api.User;

@CmdInfo(aliases = {"clear"})
public class ClearInventory extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length > 2) {
            user.sendMessage("&4Usage: /clear [material]");
            return false;
        }

        if (args.length > 0) {
            user.sendMessage("Removing " + args[0] + " from inventory");
            user.clearInventory(args[0]);
            return true;
        }
        user.sendMessage("Clearing inventory");
        user.clearInventory(null);
        
        return false;
    }

}