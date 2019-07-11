package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo
public class Spawn extends Warp {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length == 0) {
            ((Player) sender).teleport(getPlugin().getWarps().getWarp("spawn"));

            return true;
        }

        message(sender, ChatColor.RED + "Usage: /spawn");
        return true;
    }

}