package Bssentials_Rainbow.commands;

import java.util.List;

import Bssentials_Rainbow.api.CommandBase;
import PluginReference.ChatColor;
import PluginReference.MC_EntityType;
import PluginReference.MC_Player;

public class spawnmob extends CommandBase {
    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getCommandName() {
        return "spawnmob";
    }

    @Override
    public boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Entities:");
            sender.sendMessage(ChatColor.GREEN + MC_EntityType.values());

        } else if (args.length > 0) {
            sender.getWorld().spawnEntity(MC_EntityType.valueOf(args[0].toUpperCase()), sender.getLocation(), args[0]);
            return true;
        }
        return false;
    }

    @Override
    public String getHelpMessage(MC_Player p) {
        return "Summon Entities.";
    }
}
