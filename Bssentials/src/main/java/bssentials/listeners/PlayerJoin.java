package bssentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import bssentials.Bssentials;
import bssentials.Warps;
import bssentials.api.User;
import bssentials.configuration.Configs;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player plr = e.getPlayer();
        Warps warps = Bssentials.get().getWarps();
        User user = User.getByName(plr.getName());
        String opNameColor = Configs.MAIN.getString("ops-name-color");

        if (user.nick != null && !user.nick.equalsIgnoreCase("_null_") && Configs.MAIN.getBoolean("change-displayname", false)) {
            plr.setDisplayName(user.nick);
            if (Configs.MAIN.getBoolean("change-playerlist", true))
                plr.setPlayerListName(plr.getDisplayName());
        }

        // OPs name color
        if (plr.isOp() && null != opNameColor && !opNameColor.equalsIgnoreCase("none"))
            plr.setDisplayName(ChatColor.translateAlternateColorCodes('&', opNameColor + plr.getDisplayName() + "&r"));

        if (!plr.hasPlayedBefore()) {
            user.getConfig().set("uuid", plr.getUniqueId().toString());
            user.save();
            Bukkit.broadcastMessage(ChatColor.GRAY + " Please welcome " + plr.getName() + " to the server!");

            if (!warps.isSpawnSet()) return;

            try {
                warps.teleportToWarp(plr, "spawn");
            } catch (Exception ex) {
                ex.printStackTrace();
                user.sendMessage("&4Unable to find spawn: " + ex.getMessage());
            }
        }
    }

}
