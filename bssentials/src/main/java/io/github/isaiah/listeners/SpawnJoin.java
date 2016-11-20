package io.github.isaiah.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ml.bssentials.main.Bssentials;

/**
 * If the player is new to the server, he will be teleported to the spawn.
 *
 * @author Bssentials
 **/
public class SpawnJoin implements Listener {
	
	private Bssentials main;
	
	public SpawnJoin(Bssentials bss) {
		main = bss;
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void playerJoin(PlayerJoinEvent e){
	    Player p = e.getPlayer();
	    if(!p.hasPlayedBefore()){
	    	if (main.getWarpConfig().getConfigurationSection("warps.spawn") == null) {
                p.sendMessage(ChatColor.RED + "Spawn has not been set!");
            } else {
            	World w = Bukkit.getServer().getWorld(main.getWarpConfig().getString("warps.spawn.world"));
                double x = main.getWarpConfig().getDouble("warps.spawn.x");
                double y = main.getWarpConfig().getDouble("warps.spawn.y");
                double z = main.getWarpConfig().getDouble("warps.spawn.z");
                p.teleport(new Location(w, x, y, z));
                p.sendMessage(ChatColor.GREEN + "Warping to spawn");
            }
			Bukkit.broadcastMessage(main.PREFIX + " Please welcome " + p.getName() + " to the server!");
        }
    }
}
