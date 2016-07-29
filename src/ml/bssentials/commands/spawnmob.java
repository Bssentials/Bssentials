package ml.bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.api.MobAPI;

public class spawnmob implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    	if(!(sender instanceof Player)){
    		sender.sendMessage("You are not a player");
    		return false;
    	}
    	Player player = (Player) sender;
    	
         if(cmd.getName().equalsIgnoreCase("spawnmob")) {
            if (sender.hasPermission("bssentials.command.spawnmob")) {
                if(args.length == 0) {
                    player.sendMessage(ChatColor.GOLD + "Wolf, Pig, PigZombie, Cow, Blaze, CaveSpider, Chicken, Creeper,");
                    player.sendMessage(ChatColor.GOLD + "EnderDragon, Enderman, Ghast, Giant, IronGolem, MagmaCube, Ocelot,");
                    player.sendMessage(ChatColor.GOLD + "Sheep, SilverFish, Skeleton, Slime, Spider, Squid, Villager, Zombie,");
                    player.sendMessage(ChatColor.GOLD + "PolarBear, Rabbit");
                } else {
                    World world = player.getWorld();
                    Location TargetLocation = player.getLocation();
                    MobAPI.spawnMobCommand(args[0], sender, world, TargetLocation);
                }
            } else {
                sender.sendMessage("No Permission!");
            }
        }
		return true;
    }
}
