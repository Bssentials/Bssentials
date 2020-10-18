package bssentials.commands;

import bssentials.api.Location;
import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class Home extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        String home = "home";
        if (args.length > 0) home = args[0];

        Location l = user.getHome(home);
        if (null != l) {
            user.teleport(l);
        } else {
            user.sendMessage("&4Home not set!");
        }

        return false;
    }

}