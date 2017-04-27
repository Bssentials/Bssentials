package ml.bssentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import PluginReference.MC_EntityType;
import PluginReference.MC_Player;
import PluginReference.MC_World;
import sun.net.www.content.text.plain;

public class Nuke extends CommandBase {
    @Override
    public String getCommandName() {
        return "Nuke";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("nuke")) {
            if(args.length == 1) {
                try {
                    Player target = Bukkit.getPlayer(args[0]);
                    nuke(target);
                    sendMessage(sender, "Tnt rain!");
                    target.sendMessage("Nuked!");
                } catch (NullPointerException e) {
                    sendMessage(sender, "Target player is offline!");
                }
            } else if(args.length == 0) {
                if(sender instanceof Player) {
                    nuke((Player) sender);
                    sender.sendMessage("TNT RAIN!");
                } else {
                    sendMessage(sender, "CONSOLE Usage: /nuke <player>");
                }
            } else {
                sendMessage(sender, "Usage: /nuke <player>");
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args) {
        if (args.length == 1) {
            MC_Player target = sender.getServer().getOnlinePlayerByName(args[0]);
            target.sendMessage("Nuked!");
            nuke(sender.getServer().getOnlinePlayerByName(args[0]));
        } else if (args.length == 0) {
            if (!(sender == null)) {
                nuke(sender);
                sender.sendMessage("Nuked!");
            }
        }
        return false;
    }

    /**
     * Nukes player
     */
    public void nuke(Player p) {
        World w = p.getWorld();
        for (int i = 0; i < 5;) {
            w.spawnEntity(p.getLocation(), EntityType.PRIMED_TNT);
            i++;
        }
    }

    public void nuke(MC_Player p) {
        MC_World w = p.getWorld();
        for (int i = 0; i < 5;) {
            w.spawnEntity(MC_EntityType.PRIMED_TNT, p.getLocation(), "Tnt");
            i++;
        }
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> a = new ArrayList<String>();
        a.add("tnt");
        a.add("bomb");
        return a;
    }
}
