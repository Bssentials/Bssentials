package ml.bssentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import ml.bssentials.main.Bssentials;

public class PlayerLeave implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent e) {
        Bssentials.afkplayers.remove(e.getPlayer().getUniqueId().toString());
    }
}