package bssentials.commands;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class Invsee extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length != 1) {
            user.sendMessage("&4Usage: /invsee <player>");
            return false;
        }
        User target = getUserByName(args[0]);
        user.openOtherUserInventory(target);
        user.sendMessage("Opened Inventory");
        return true;
    }

}