package bssentials.commands;

import java.lang.management.ManagementFactory;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CmdInfo(aliases = {"mem", "memory", "uptime", "entities"})
public class Lag extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        message(sender, ChatColor.GOLD + "Uptime: " + ChatColor.GREEN + formatTime(ManagementFactory.getRuntimeMXBean().getUptime()));
        message(sender, ChatColor.GOLD + "Max RAM: " + ChatColor.GREEN + formatSize(Runtime.getRuntime().maxMemory()));
        message(sender, ChatColor.GOLD + "Total RAM: " + ChatColor.GREEN + formatSize(Runtime.getRuntime().totalMemory()));
        message(sender, ChatColor.GOLD + "Free RAM: " + ChatColor.GREEN + formatSize(Runtime.getRuntime().freeMemory()));

        List<World> worlds = sender.getServer().getWorlds();
        message(sender, ChatColor.GOLD + "Worlds (" + sender.getServer().getWorlds().size() + "):");
        for (World w : worlds)
            message(sender, ChatColor.GOLD + "World: " + ChatColor.GREEN + w.getName() + ChatColor.GOLD + ": Chunks: " + ChatColor.GREEN + w.getLoadedChunks().length + ChatColor.GOLD + ", Entities: " + ChatColor.GREEN + w.getEntities().size());

        return true;
    }

    private String formatTime(long l) {
        if (l < 1000)
            return l + " milliseconds";

        long sec = l/1000;
        long min = sec/60;

        if (min < 60)
            return min + " minutes";

        long hours = min/60;
        if (hours < 24)
            return hours + "hours";

        return (hours/24) + " days";
    }

    private String formatSize(long v) {
        int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
        return String.format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
    }

}