package ml.bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Nuke extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("nuke")) {
            if(args.length == 1) {
                try {
                    Player target = Bukkit.getPlayer(args[0]);
                    nuke(target);
                    sendMessage(sender, "Tnt rain!");
                    target.sendMessage("Nuked!");
                } catch (NullPointerException e) {
                    sendMessage(sender, "Target player is offline!");
                }
            } else if(args.length == 0) {
                if(sender instanceof Player) {
                    nuke((Player) sender);
                    sender.sendMessage("TNT RAIN!");
                } else {
                    sendMessage(sender, "Console Usage: /nuke <player>");
                }
            } else sendMessage(sender, "Usage: /nuke <player>");

            return true;
        }
        return false;
    }

    public void nuke(Player p) {
        for (int i = 0; i < 5;) {
            p.getWorld().spawnEntity(p.getLocation(), EntityType.PRIMED_TNT);
            i++;
        }
    }
}
