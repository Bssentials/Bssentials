package bssentials.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@CmdInfo(aliases = {"mob"})
public class SpawnMob extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        ArrayList<String> mobs = new ArrayList<>();
        for (EntityType e : EntityType.values())
            if (e.isSpawnable()) mobs.add(String.valueOf(e).toLowerCase());

        if (args.length == 0) {
            message(sender, ChatColor.GREEN + join(mobs, ChatColor.GRAY + ", " + ChatColor.GREEN));
        } else {
            if (!(sender instanceof Player)) {
                Player player = getPlayer(args[1]);
                World world = player.getWorld();
                Location TargetLocation = player.getLocation();
                world.spawnEntity(TargetLocation, EntityType.valueOf(args[0].toUpperCase()));
                player.sendMessage(ChatColor.GREEN + "Spawned " + args[0]);
                message(sender, "Spawned mob on " + player.getName());
            } else {
                Player player = (Player) sender;
                World world = player.getWorld();
                Location TargetLocation = player.getLocation();
                world.spawnEntity(TargetLocation, EntityType.valueOf(args[0].toUpperCase()));
                player.sendMessage(ChatColor.GREEN + "Spawned " + args[0]);
            }
        }
        return true;
    }

    private String join(ArrayList<String> mobs, String string) {
        String s = "Mobs: ";
        for (String sp : mobs) s += ChatColor.GREEN + sp + string;

        return s;
    }

}
