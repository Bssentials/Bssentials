package bssentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import bssentials.Bssentials;

public class PlayerCommand implements Listener {

    private Plugin bss;

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        if (null == bss)
            bss = Bssentials.get();
        // TODO e.setCancelled( bss.getConfig().getList("commandBlackList").contains(e.getMessage().substring(1)) );
    }

}