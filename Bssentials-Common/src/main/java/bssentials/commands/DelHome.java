package bssentials.commands;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class DelHome extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 1) {
            user.sendMessage("Usage: /delhome <name>");
            return false;
        }
        user.delHome(args[0]);
        return false;
    }

}