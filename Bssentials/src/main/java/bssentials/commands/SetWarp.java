package bssentials.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo(onlyPlayer = true)
public class SetWarp extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 1)
            return message(sender, ChatColor.RED + "Usage: /setwarp <warp>");

        Location loc = ((Player) sender).getLocation();

        try {
            getPlugin().getWarps().setWarp(args[0], loc);
            message(sender, ChatColor.GREEN + "Warp set!");
        } catch (IOException e) {
            e.printStackTrace();
            message(sender, ChatColor.RED + "Unable to write to " + args[0] + ".yml");
        }
        return true;
    }

}