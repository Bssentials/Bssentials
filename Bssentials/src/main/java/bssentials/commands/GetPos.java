package bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo(aliases = {"eposition"})
public class GetPos extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length <= 0)
                return message(sender, "Usage: /getpos <player>");
            Player p = Bukkit.getPlayer(args[0]);
            return message(sender, "Pos: " + p.getLocation().toString());
        }
        if (args.length <= 0)
            return message(sender, ChatColor.GREEN + "Pos: " + ((Player)sender).getLocation().toString());
        Player p = Bukkit.getPlayer(args[0]);
        return message(sender, ChatColor.GREEN + "Pos: " + p.getLocation().toString());
    }

}
