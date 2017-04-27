package ml.bssentials.commands;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import PluginReference.MC_Player;

public class Broadcast extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("broadcast")) {
            if (args.length == 0) {
                sender.sendMessage("/broadcast <message>");
            } else {
                String message = StringUtils.join(args, " ");
                Bukkit.broadcastMessage("[Broadcast] " + ChatColor.translateAlternateColorCodes('&', message));
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getCommandName() {
        return "broadcast";
    }

    @Override
    public boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args) {
        if (args.length > 0) {
            sender.getServer().broadcastMessage(
                    "[Broadcast] " + Arrays.asList(args).toString().replace("[", "").replace("]", ""));
            return true;
        }
        return false;
    }
}
