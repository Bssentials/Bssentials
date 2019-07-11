package bssentials.commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo(onlyPlayer = true)
public class Tpa extends BCommand {
    
    public static HashMap<String, String> tpaMap = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        Player p = (Player) sender;
        if (args.length <= 0)
            return message(p, ChatColor.RED + "Usage: /tpa <player>");

        Player target = getPlayer(args[0]);
        if (null == target)
            return message(p, ChatColor.RED + "Target \"" + args[0] + "\" not found.");

        tpaMap.put(target.getName(), p.getName());
        message(p, "&aRequest sent to " + args[0]);
        message(p, "&aRequest will timeout after two minutes");
        message(target, "&a" + p.getName() + " is requesting to teleport to you");
        message(target, "&aType /tpaccept to accept the request or");
        message(target, "&aType /tpadeny to deny the request");
        return false;
    }

}