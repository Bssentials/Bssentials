package bssentials.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.Files;

import bssentials.Bssentials;

@CmdInfo(onlyPlayer = true)
public class SetSpawn extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length > 1) {
            message(sender, ChatColor.RED + "Usage: /setspawn");
            return true;
        }

        Location loc = ((Player) sender).getLocation();

        String content = "world: " + loc.getWorld().getName() + "\n" + 
                "x: " + loc.getX() + "\n" + "y: " + loc.getY() + "\n" + "z: " + loc.getZ() + "\n" +
                "pitch: " + loc.getPitch() + "\n" + "yaw: " + loc.getYaw();
        try {
            Files.write(content.getBytes(), Bssentials.spawn);
            message(sender, ChatColor.GREEN + "Warp set!");
        } catch (IOException e) {
            e.printStackTrace();
            message(sender, ChatColor.RED + "Unable to write to spawn.yml");
        }
        return true;
    }

}