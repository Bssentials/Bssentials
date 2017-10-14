package ml.bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ping extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ping")) {
            int ping = pingPlayer((Player) sender);
            if (ping <= -1) {
                sender.sendMessage("Pong!");
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Ping: " + ping + "ms");
        }
        return true;
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }

    private static int pingPlayer(Player p) {
        try {
            String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".entity.CraftPlayer");
            Object handle = craftPlayer.getMethod("getHandle").invoke(p);
            Integer ping = (Integer) handle.getClass().getDeclaredField("ping").get(handle);
            return ping.intValue();
        } catch (Exception e) {
            return -1;
        }
    }
}
