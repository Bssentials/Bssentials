package bssentials.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

@CmdInfo(onlyPlayer = true)
public class Top extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player) sender;
        Location l = p.getLocation();
        int x = l.getBlockX(), y = l.getBlockY(), z = l.getBlockZ();
        while (p.getWorld().getBlockAt(x, y, z).getType() != Material.AIR)
            y++;

        p.teleport(new Location(p.getWorld(), x, y + 1, z), TeleportCause.COMMAND);
        return false;
    }

}
