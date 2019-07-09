package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo
public class Warp extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length > 0) {

            Location l = getPlugin().getWarps().getWarp(args[0]);
            ((Player) sender).teleport(l);

            return true;
        }

        message(sender, ChatColor.RED + "Usage: /warp <warp>");
        return true;
    }

}