package bssentials.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

@CmdInfo(aliases = {"clear"})
public class ClearInventory extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length > 2) {
            sender.sendMessage("Usage: /clear [material]");
            return false;
        }
        Player p = (Player) sender;
        PlayerInventory i = p.getInventory();
        if (args.length > 0) {
            p.sendMessage("Removing " + args[0] + " from inventory");
            i.remove(Material.valueOf(args[0]));
            return true;
        }
        p.sendMessage("Clearing inventory");
        i.clear();
        
        return false;
    }

}