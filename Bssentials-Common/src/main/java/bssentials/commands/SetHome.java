package bssentials.commands;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class SetHome extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 1) {
            user.sendMessage("&4Usage: /sethome <name>");
            return false;
        }
        String home = args[0];
        user.setHome(home, user.getLocation());
        return false;
    }

}