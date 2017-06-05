package ml.bssentials.commands;

import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class spawnmob extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if(cmd.getName().equalsIgnoreCase("spawnmob")) {
            ArrayList<String> mobs = new ArrayList<>();
            for (EntityType e : EntityType.values()) {
                if (e.isSpawnable()) mobs.add(String.valueOf(e).toLowerCase());
            }
            if(args.length == 0) {
                sendMessage(sender, ChatColor.GREEN + StringUtils.join(mobs, ChatColor.GRAY + ", " + ChatColor.GREEN));
            } else {
                if (!(sender instanceof Player)) {
                    Player player = Bukkit.getPlayer(args[1]);
                    World world = player.getWorld();
                    Location TargetLocation = player.getLocation();
                    world.spawnEntity(TargetLocation, EntityType.valueOf(args[0].toUpperCase()));
                    player.sendMessage(ChatColor.GREEN + "Spawned " + args[0]);
                    sendMessage(sender, "Spawned mob on " + player.getName());
                } else {
                    Player player = (Player) sender;
                    World world = player.getWorld();
                    Location TargetLocation = player.getLocation();
                    world.spawnEntity(TargetLocation, EntityType.valueOf(args[0].toUpperCase()));
                    player.sendMessage(ChatColor.GREEN + "Spawned " + args[0]);
                }
            }
        }
        return true;
    }

    @Override
    public boolean onlyPlayer() {
        return false;
    }
}
