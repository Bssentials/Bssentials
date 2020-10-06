package bssentials.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import bssentials.api.User;

@CmdInfo(aliases = {"mob"})
public class SpawnMob extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        ArrayList<String> mobs = new ArrayList<>();
        for (EntityType e : EntityType.values())
            if (e.isSpawnable()) mobs.add(String.valueOf(e).toLowerCase());

        if (args.length == 0) {
            user.sendMessage(ChatColor.GREEN + join(mobs, ChatColor.GRAY + ", " + ChatColor.GREEN));
            return true;
        }

        if (!(user.isPlayer())) {
            User target = getUserByName(args[1]);
            Location targetLocation = target.getLocation();
            targetLocation.getWorld().spawnEntity(targetLocation, EntityType.valueOf(args[0].toUpperCase()));
            target.sendMessage(ChatColor.GREEN + "Spawned " + args[0]);
            user.sendMessage("Spawned mob on " + target.getName(true));
        } else {
            ((Player)user.getBase()).getWorld().spawnEntity(user.getLocation(), EntityType.valueOf(args[0].toUpperCase()));
            user.sendMessage(ChatColor.GREEN + "Spawned " + args[0]);
        }
        return true;
    }

    private String join(ArrayList<String> mobs, String string) {
        String s = "Mobs: ";
        for (String sp : mobs) s += ChatColor.GREEN + sp + string;

        return s;
    }

}
