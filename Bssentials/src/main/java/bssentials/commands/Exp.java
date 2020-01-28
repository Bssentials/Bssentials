package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo(onlyPlayer = true, aliases = {"xp"})
public class Exp extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length == 1) {
            Player p = (Player) sender;
            int oldvalue = p.getLevel();
            p.setLevel(p.getLevel() + Integer.valueOf(args[0]));
            p.sendMessage(ChatColor.GREEN + "Changed your exp from " + oldvalue + " to " + p.getLevel());
            return true;
        }
        return false;
    }

}