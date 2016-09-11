package ml.bssentials.api;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ml.bssentials.main.Bssentials;

public class WarpAPI {
	public static Bssentials main;
	
    public static void createWarp(Player p, String warpname) {
        main.getWarpConfig().set("warps." + warpname + ".world", p.getLocation().getWorld().getName());
        main.getWarpConfig().set("warps." + warpname + ".x", p.getLocation().getX());
        main.getWarpConfig().set("warps." + warpname + ".y", p.getLocation().getY());
        main.getWarpConfig().set("warps." + warpname + ".z", p.getLocation().getZ());
        main.saveWarpConfig();
        
        p.sendMessage(ChatColor.GREEN + warpname + " warp set!");
    }
    
    public static void createHome(Player p) {
    	String homename = p.getName();
    	main.getHomeConfig().set("homes." + homename + ".world", p.getLocation().getWorld().getName());
    	main.getHomeConfig().set("homes." + homename + ".x", p.getLocation().getX());
    	main.getHomeConfig().set("homes." + homename + ".y", p.getLocation().getY());
    	main.getHomeConfig().set("homes." + homename + ".z", p.getLocation().getZ());
    	main.saveHomeConfig();
        
        p.sendMessage(ChatColor.GREEN + "Your home has been set!");
    }
    
    public static void delHome(Player p) {
    	String homename = p.getName();
    	main.getHomeConfig().set("homes." + homename, null);
    	main.saveWarpConfig();
    }
}
