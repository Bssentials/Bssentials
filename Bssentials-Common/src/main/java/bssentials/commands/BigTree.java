package bssentials.commands;

import bssentials.api.Location;
import bssentials.api.User;

@CmdInfo(aliases = {"largetree"}, onlyPlayer = true)
public class BigTree extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        Location target = user.getTargetBlock(200);
        user.getWorld().generateTree(target.x, target.y, target.z);

        return false;
    }

}