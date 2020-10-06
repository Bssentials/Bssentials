package bssentials.commands;

import org.bukkit.Location;
import org.bukkit.TreeType;

import bssentials.api.User;

@CmdInfo(aliases = {"largetree"}, onlyPlayer = true)
public class BigTree extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        Location target = user.getTargetBlock(null, 200).getLocation();
        user.getLocation().getWorld().generateTree(target, TreeType.BIG_TREE);

        return false;
    }

}