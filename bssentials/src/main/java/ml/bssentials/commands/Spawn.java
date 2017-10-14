package ml.bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.api.BssUtils;
import ml.bssentials.main.Bssentials;

public class Spawn extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        Bssentials main = Bssentials.getInstance();

        /* SETSPAWN COMMAND */
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            if (BssUtils.hasPermForCommand(sender, "setspawn")) {
                if (sender instanceof Player) {
                    main.createWarp((Player) sender, "spawn");
                } else sender.sendMessage("Your not a player.");
                return true;
            } else {
                sender.sendMessage("No Permission");
                return false;
            }
        }

        /* SPAWN COMMAND */
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (main.warps.getConfigurationSection("warps.spawn") == null) {
                sender.sendMessage(ChatColor.RED + "Spawn has not been set!");
                return false;
            } else {
                if (args.length == 0) {
                    World w = Bukkit.getServer().getWorld(main.warps.getString("warps.spawn.world"));
                    double x = main.warps.getDouble("warps.spawn.x");
                    double y = main.warps.getDouble("warps.spawn.y");
                    double z = main.warps.getDouble("warps.spawn.z");
                    float yaw = main.warps.getInt("warps.spawn.yaw");
                    float pitch = main.warps.getInt("warps.spawn.pitch");
                    if (sender instanceof Player) {
                        ((Player) sender).teleport(new Location(w, x, y, z, yaw, pitch));
                    }
                    sender.sendMessage(ChatColor.GREEN + "Warping to spawn");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid args");
                    return false;
                }
            }
        }
        return true;
    }
}