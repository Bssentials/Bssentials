package bssentials.commands;

import java.lang.management.ManagementFactory;
import java.util.Collection;

import bssentials.Bssentials;
import bssentials.api.IWorld;
import bssentials.api.User;

@CmdInfo(aliases = {"mem", "memory", "uptime", "entities"})
public class Lag extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        user.sendMessage("&6Uptime: &a" + formatTime(ManagementFactory.getRuntimeMXBean().getUptime()));
        user.sendMessage("&6Max RAM: &a" + formatSize(Runtime.getRuntime().maxMemory()));
        user.sendMessage("&6Total RAM: &a" + formatSize(Runtime.getRuntime().totalMemory()));
        user.sendMessage("&6Free RAM: &a" + formatSize(Runtime.getRuntime().freeMemory()));

        Collection<IWorld> worlds = Bssentials.getInstance().getWorlds();
        user.sendMessage("&6Worlds (" + worlds.size() + "):");
        for (IWorld w : worlds)
            user.sendMessage("&6World: &a" + w.getName() + "&6, Chunks: &a" + w.getLoadedChunkCount() + "&6, Entities: &a" + w.getEntityCount());

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