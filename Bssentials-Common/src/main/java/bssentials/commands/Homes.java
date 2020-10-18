package bssentials.commands;

import java.util.Set;

import bssentials.api.Location;
import bssentials.api.User;

@CmdInfo(onlyPlayer = true, aliases = {"homelist"})
public class Homes extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {

        Set<String> homes = user.getHomes();
        user.sendMessage("&aHome Name: (World, X, Y, Z)");
        for (String home : homes) {
            Location loc = user.getHome(home);
            user.sendMessage("- Home \"" + home + "\": " + "(" + loc.world + "," + (int)loc.x + "," + loc.y + "," + (int)loc.z + ")");
        }

        return false;
    }

}