package bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import bssentials.api.User;

@CmdInfo
public class Ping extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        user.sendMessage(user.isPlayer() ? ChatColor.GREEN + "Your Ping: " + getPing(((Player)user.getBase())) + " ms" : "Your Ping: 0ms");
        return true;
    }

    public int getPing(Player p) {
        try {
            String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".entity.CraftPlayer");
            Object handle = craftPlayer.getMethod("getHandle").invoke(p);
            return ((Integer) handle.getClass().getDeclaredField("ping").get(handle)).intValue();
        } catch (Exception e) { return -1; }
    }

}