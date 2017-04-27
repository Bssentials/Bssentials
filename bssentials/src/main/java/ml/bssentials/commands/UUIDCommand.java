package ml.bssentials.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import PluginReference.MC_Player;

public class UUIDCommand extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if(cmd.getName().equalsIgnoreCase("uuid")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sendMessage(sender, "CONSOLE Usage: /uuid <player>");
                    return false;
                }
                Player p = (Player) sender;
                sender.sendMessage("Your uuid: " + p.getUniqueId().toString());
                return true;
            } else if (args.length == 1) {
                try {
                    String uuid = Bukkit.getPlayer(args[0]).getUniqueId().toString();
                    sendMessage(sender, ChatColor.GREEN + "The uuid of that player is: " + uuid);
                } catch (NullPointerException e) {
                    sendMessage(sender, ChatColor.RED + "That player is not online right now!");
                }
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean senderHasPerm(CommandSender sender, Command cmd) {
        return sender.isOp();
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getCommandName() {
        return "uuid";
    }

    @Override
    public boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args) {
        sender.sendMessage("Your UUID: " + sender.getUUID());
        return true;
    }
}