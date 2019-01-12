package bssentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import bssentials.Bssentials;

public class PlayerCommand implements Listener {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        e.setCancelled( Bssentials.get().getConfig().getList("commandBlackList").contains(e.getMessage().substring(1)) );
    }

}