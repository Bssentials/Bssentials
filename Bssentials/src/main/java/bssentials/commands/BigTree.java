package bssentials.commands;

import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo(aliases = {"largetree"}, onlyPlayer = true)
public class BigTree extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player) sender;

        Location target = p.getTargetBlock(null, 200).getLocation();
        p.getLocation().getWorld().generateTree(target, TreeType.BIG_TREE);

        return false;
    }

}
