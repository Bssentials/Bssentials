package bssentials.listeners;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import bssentials.Bssentials;
import bssentials.api.User;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player plr = e.getPlayer();
        Bssentials bss = Bssentials.get();
        if (!plr.hasPlayedBefore()) {
            bss.getConfig().set("playerdata." + plr.getName() + ".uuid", plr.getUniqueId().toString());
            if (!bss.isSpawnSet()) {
                plr.sendMessage(ChatColor.RED + "Spawn has not been set!");
            } else {
                try {
                    bss.teleportPlayerToWarp(plr, "spawn");
                } catch (NumberFormatException | IOException e1) {
                    e1.printStackTrace();
                    plr.sendMessage(ChatColor.RED + "Unable to find spawn.");
                }
                plr.sendMessage(ChatColor.GREEN + "Warping to spawn");
            }
            Bukkit.broadcastMessage(ChatColor.GRAY + " Please welcome " + plr.getName() + " to the server!");
        }

        User user = User.getByName(plr.getName());
        if (!user.nick.equalsIgnoreCase("_null_")) {
            plr.sendMessage(ChatColor.GRAY + "Nickname changed to: " + user.nick);
            plr.setDisplayName(user.nick);
        }
    }

}
