package ml.bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.api.BssUtils;
import ml.bssentials.main.Bssentials;

public class RemoveLag extends CommandBase {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("removelag")) {
            if (args.length == 0) {
                Bukkit.dispatchCommand(sender, "lagg gc");
                Bukkit.dispatchCommand(sender, "lagg clear");
                Bukkit.dispatchCommand(sender, "lagg unloadchunks");
                sender.sendMessage(Bssentials.prefix + "Removed Lagg!");
            } else if (args.length !=0 && args[0].equalsIgnoreCase("unloadchunks")) {
                if (BssUtils.hasPermForCommand(sender, "removelag.unloadchunks")) {
                    if (args.length == 1) {
                        int c = 0;
                        for (World w : Bukkit.getWorlds())
                            for (Chunk chunk : w.getLoadedChunks())
                                if (w.getPlayers().size() != 0) for (Player p : w.getPlayers()) {
                                    Chunk ch = w.getChunkAt(p.getLocation());
                                    if((chunk.getX() != ch.getX()) && (chunk.getZ() != ch.getZ())){
                                        w.unloadChunk(chunk);
                                        ++c;
                                    }
                                }
                                else {
                                    w.unloadChunk(chunk);
                                    ++c;
                                }
                        sender.sendMessage(String.format("%s chunks have been unloaded.", c));
                    } else {
                        sender.sendMessage("/removelag unloadchunks <world>");
                        return true;
                    }
                } else BssUtils.noPermMsg(sender, cmd);
                return true;
            }
        }
        return true;
    }
}