package bssentials.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class Top extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        Location l = user.getLocation();
        int x = l.getBlockX(), y = l.getBlockY(), z = l.getBlockZ();
        while (((Player)user.getBase()).getWorld().getBlockAt(x, y, z).getType() != Material.AIR)
            y++;

        user.teleport(new Location(((Player)user.getBase()).getWorld(), x, y + 1, z));
        return false;
    }

}