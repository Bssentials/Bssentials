package bssentials.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@CmdInfo
public class Nuke extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("nuke")) {
            if(args.length == 1) {
                try {
                    Player target = getPlayer(args[0]);
                    nuke(target.getLocation());
                    message(sender, "Tnt rain!");
                    message(target, "Tnt rain!");
                } catch (NullPointerException e) {
                    message(sender, "Target player is offline!");
                }
            } else if(args.length == 0) {
                if(sender instanceof Player) {
                    nuke(((Player) sender).getLocation());
                    sender.sendMessage("TNT RAIN!");
                } else message(sender, "Usage: /nuke <player>");
            } else message(sender, "Usage: /nuke <player>");

            return true;
        }
        return false;
    }

    public void nuke(Location l) {
        for (int i = 0; i < 5; i++)
            l.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
    }

}