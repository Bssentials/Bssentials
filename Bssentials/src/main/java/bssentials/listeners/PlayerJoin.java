package bssentials.listeners;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import bssentials.Bssentials;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Bssentials main = Bssentials.get();
        if (!player.hasPlayedBefore()) {
            main.getConfig().set("playerdata."+player.getName()+"uuid", player.getUniqueId().toString());
            if (!main.isSpawnSet()) {
                player.sendMessage(ChatColor.RED + "Spawn has not been set!");
            } else {
                try {
                    main.teleportPlayerToWarp(player, "spawn");
                } catch (NumberFormatException | IOException e1) {
                    e1.printStackTrace();
                    player.sendMessage(ChatColor.RED + "Unable to find spawn.");
                }
                player.sendMessage(ChatColor.GREEN + "Warping to spawn");
            }
            Bukkit.broadcastMessage(ChatColor.GRAY + " Please welcome " + player.getName() + " to the server!");
        }
    }
}
