package ml.bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.api.BssUtils;

public class Gamemode extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.GRAY + " Usage /gm <mode>");
            } else if (BssUtils.hasPermForCommand(sender, "gamemode")) {
                if (args.length == 1) {
                    String m = args[0];
                    Player player = (Player) sender;
                    if (m.equalsIgnoreCase("0") || m.equalsIgnoreCase("survival")) {
                        player.setGameMode(GameMode.SURVIVAL);
                        return true;
                    }
                    if (m.equalsIgnoreCase("1") || m.equalsIgnoreCase("creative")) {
                        player.setGameMode(GameMode.CREATIVE);
                        return true;
                    }
                    if (m.equalsIgnoreCase("2") || m.equalsIgnoreCase("adventure")) {
                        player.setGameMode(GameMode.ADVENTURE);
                        return true;
                    }
                    if (m.equalsIgnoreCase("3") || m.equalsIgnoreCase("spectator")) {
                        player.setGameMode(GameMode.SPECTATOR);
                        return true;
                    }
                    player.setGameMode(GameMode.valueOf(m));
                    return true;
                } else {
                    sender.sendMessage("Wrong args!");
                    return false;
                }
            } else {
                BssUtils.noPermMsg(sender, cmd);
                return false;
            }
        }
        return true;
    }

}
