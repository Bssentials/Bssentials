package bssentials.commands;

import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import bssentials.Bssentials;

public class DelWarp extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 1) {
            message(sender, ChatColor.RED + "Usage: /delwarp <warp>");
            return true;
        }

        File file = getFileForWarp(args[0]);
        file.delete();
        message(sender, ChatColor.GREEN + "Warp removed!");
        return true;
    }

    public File getFileForWarp(String warp) {
        if (warp.equalsIgnoreCase("spawn")) {
            return Bssentials.spawn;
        }
        return new File(Bssentials.warpdir, warp + ".yml");
    }
}