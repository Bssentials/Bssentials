package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo(aliases = {"eposition"})
public class GetPos extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        boolean isPlr = sender instanceof Player;

        if (args.length <= 0)
            return message(sender, isPlr ? ChatColor.GREEN + "Pos: " + ((Player)sender).getLocation().toString() : "Usage: /getpos <player>");
        return message(sender, ChatColor.GREEN + "Pos: " + getPlayer(args[0]).getLocation().toString());
    }

}
