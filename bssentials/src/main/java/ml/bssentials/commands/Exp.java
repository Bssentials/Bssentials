package ml.bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Exp extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("exp")) {
            if (sender instanceof Player && args.length == 1) {
                Player p = (Player) sender;
                int oldvalue = p.getLevel();
                p.setLevel(p.getLevel() + Integer.valueOf(args[0]));
                p.sendMessage(ChatColor.GREEN + "Changed your exp from " + oldvalue + " to " + p.getTotalExperience());
                return true;
            }
            return false;
        }
        return false;
    }
}