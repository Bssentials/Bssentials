package bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo(onlyPlayer = true, aliases = {"tpdeny", "tpno", "tpano"})
public class TpaDeny extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player) sender;
        if (Tpa.tpaMap.containsKey(p.getName())) {
            Player from = Bukkit.getPlayer( Tpa.tpaMap.get(p.getName()) );
            message(p, ChatColor.GREEN + "Teleport Request denied.");
            message(from, ChatColor.GREEN + "Teleport Request denied.");
            Tpa.tpaMap.remove(p.getName());
        }
        return message(p, ChatColor.RED + "You have no teleport requests to deny");
    }

}
