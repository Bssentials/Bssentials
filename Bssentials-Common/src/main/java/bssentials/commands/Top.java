package bssentials.commands;

import bssentials.api.Location;
import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class Top extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        Location l = user.getLocation();
        int x = (int) l.x, y = (int) l.y, z = (int) l.z;
        while (user.getWorld().getBlockAt(x, y, z) != 0) y++;

        l.y = y + 1;

        user.teleport(l);
        return false;
    }

}