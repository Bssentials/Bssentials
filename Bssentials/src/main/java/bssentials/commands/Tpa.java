package bssentials.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

        Player target = Bukkit.getPlayer(args[0]);
        if (null == target)
            return message(p, ChatColor.RED + "Target \"" + args[0] + "\" not found.");

        tpaMap.put(target.getName(), p.getName());
        message(p, ChatColor.GREEN + "Request sent to " + args[0]);
        message(p, ChatColor.GREEN + "Request will timeout after two minutes");
        message(target, ChatColor.GREEN + p.getName() + " is requesting to teleport to you");
        message(target, ChatColor.GREEN + "Type /tpaccept to accept the request or");
        message(target, ChatColor.GREEN + "Type /tpadeny to deny the request");
        return false;
    }

}