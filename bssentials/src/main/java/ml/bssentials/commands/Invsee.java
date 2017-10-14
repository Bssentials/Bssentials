package ml.bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.api.BssUtils;
import ml.bssentials.main.Bssentials;

public class Invsee extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("invsee")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
            } else if (args.length == 1) {
                if (BssUtils.hasPermForCommand(sender, cmd.getName())) {
                    Player targetPlayer = sender.getServer().getPlayer(args[0]);
                    ((Player) sender).openInventory(targetPlayer.getInventory());
                    sender.sendMessage(Bssentials.prefix + "Opened Inventory.");
                } else BssUtils.noPermMsg(sender, cmd);
            }
        }
        return false;
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }
}
