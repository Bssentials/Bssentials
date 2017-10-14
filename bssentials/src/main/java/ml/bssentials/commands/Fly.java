package ml.bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.api.BssUtils;
import ml.bssentials.main.Bssentials;

public class Fly extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fly")) {
            if (!(BssUtils.hasPermForCommand(sender, "fly"))) {
                sender.sendMessage("You can't fly!");
            } else {
                if (args.length == 2 && BssUtils.hasPermForCommand(sender, "fly.other")) {
                    Player plr = Bukkit.getServer().getPlayer(args[0]);
                    boolean isFlying = plr.isFlying();
                    plr.setAllowFlight(true);
                    plr.setFlying(!isFlying);
                    String m = ChatColor.DARK_AQUA + "Set flight " + (isFlying ? "Dis" : "En") + "abled";
                    plr.sendMessage(m);
                    sendMessage(sender, m);
                } else if (args.length == 0) {
                    Player plr = (Player) sender;
                    boolean isFlying = plr.isFlying();
                    plr.setAllowFlight(true);
                    plr.setFlying(!isFlying);
                    plr.sendMessage(ChatColor.DARK_AQUA + "Set flight " + (isFlying ? "Dis" : "En") + "abled");
                } else {
                    sender.sendMessage(Bssentials.prefix + "Usage is /fly OR /fly [player]");
                }
            }
        }
        return false;
    }

}
