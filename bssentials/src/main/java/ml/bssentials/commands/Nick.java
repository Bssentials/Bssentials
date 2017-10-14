package ml.bssentials.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.main.Bssentials;

public class Nick extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("nick")) {
            if (args.length <= 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /nick <nickname|off>");
            }
            Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("off")) {
                p.setDisplayName(p.getDisplayName());
                p.sendMessage(ChatColor.GOLD + "Nickname reset.");
                Bssentials.getInstance().getConfig().set("playerdata." + sender.getName() + ".nick", null);
                Bssentials.getInstance().saveConfig();
                return true;
            }
            Bssentials.getInstance().getConfig().set("playerdata." + sender.getName() + ".nick",
                    StringUtils.join(args, " "));
            p.setDisplayName(ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, " ")));
            Bssentials.getInstance().saveConfig();
            return true;
        }
        return true;
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }
}