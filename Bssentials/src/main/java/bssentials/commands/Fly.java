package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo
public class Fly extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length == 2 && sender.hasPermission("bssentials.command.fly.other")) {
            Player plr = getPlayer(args[0]);
            boolean b = plr.getAllowFlight();
            plr.setAllowFlight(!b);
            plr.setFlying(!b);
            String m = ChatColor.DARK_AQUA + "Set flight " + !b;
            plr.sendMessage(m);
            message(sender, m);
        } else if (args.length == 0) {
            Player plr = (Player) sender;
            boolean b = plr.getAllowFlight();
            plr.setAllowFlight(!b);
            plr.setFlying(!b);
            message(sender, ChatColor.DARK_AQUA + "Set flight " + !b);
        } else message(sender, ChatColor.RED + "Usage: /fly OR /fly [player]");

        return false;
    }

}
