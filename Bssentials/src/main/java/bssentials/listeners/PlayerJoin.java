package bssentials.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import bssentials.Bssentials;
import bssentials.bukkit.Warps;
import bssentials.api.User;
import bssentials.configuration.Configs;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player plr = e.getPlayer();
        Warps warps = Bssentials.getInstance().getWarps();
        User user = Bssentials.getInstance().getUser(plr.getName());
        String opNameColor = Configs.MAIN.getString("ops-name-color");

        if (user.getNick() != null && !user.getNick().equalsIgnoreCase("_null_") && Configs.MAIN.getBoolean("change-displayname", false)) {
            plr.setDisplayName(user.getNick());
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
                warps.teleportToWarp(user, "spawn");
            } catch (Exception ex) {
                ex.printStackTrace();
                user.sendMessage("&4Unable to find spawn: " + ex.getMessage());
            }
        }
    }

}
