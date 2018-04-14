package bssentials.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bssentials.Bssentials;

public class Warp extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length > 0) {
            File warp = getFileForWarp(args[0]);

            // Read YAML file
            // TODO: Proper YAML parser
            try {
                Location l = ((Player) sender).getLocation();
                for (String line : Files.readAllLines(warp.toPath())) {
                    if (line.startsWith("world")) {
                        l.setWorld(Bukkit.getWorld(line.substring("world: ".length())));
                    }

                    if (line.startsWith("x:")) l.setX(Double.valueOf(line.substring(3)));

                    if (line.startsWith("y:")) l.setY(Double.valueOf(line.substring(3)));

                    if (line.startsWith("z:")) l.setZ(Double.valueOf(line.substring(3)));

                    if (line.startsWith("pitch")) l.setPitch(new Float(Double.valueOf(line.substring("pitch: ".length()))));

                    if (line.startsWith("yaw")) l.setYaw(new Float(Double.valueOf(line.substring("yaw: ".length()))));
                }
                ((Player) sender).teleport(l);
            } catch (IOException e) {
                e.printStackTrace();
                message(sender, e.getMessage());
                return true;
            }

            return true;
        }

        message(sender, ChatColor.RED + "Usage: /warp <warp>");
        return true;
    }

    public File getFileForWarp(String warp) {
        if (warp.equalsIgnoreCase("spawn")) return Bssentials.spawn;

        return new File(Bssentials.warpdir, warp + ".yml");
    }
}
