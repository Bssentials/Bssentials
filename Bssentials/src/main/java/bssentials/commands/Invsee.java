package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo(onlyPlayer = true)
public class Invsee extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
            return false;
        }
        Player target = getPlayer(args[0]);
        ((Player) sender).openInventory(target.getInventory());
        sender.sendMessage("Opened Inventory");
        return true;
    }

}