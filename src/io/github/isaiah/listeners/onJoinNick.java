package io.github.isaiah.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

//import io.github.isaiah.bssentials.Bssentials;
import ml.bssentials.main.Bssentials;

public class onJoinNick implements Listener {

	private Bssentials main;
	
	public onJoinNick(Bssentials bs) {
		main = bs;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (main.getConfig().getString("playerdata." + player.getName() + ".nick") != null) {
			player.setDisplayName(main.getConfig().getString("playerdata." + player.getName() + ".nick"));
			player.sendMessage(Bssentials.PREFIX + ChatColor.GOLD + "Found a nick name you set! type /disnick to disable your nickname!");
		}
	}
}
