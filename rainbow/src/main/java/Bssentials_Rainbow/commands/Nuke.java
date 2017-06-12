package Bssentials_Rainbow.commands;

import java.util.Arrays;
import java.util.List;

import Bssentials_Rainbow.api.CommandBase;
import PluginReference.MC_EntityType;
import PluginReference.MC_Player;

public class Nuke extends CommandBase {
    @Override
    public String getCommandName() {
        return "Nuke";
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

    public void nuke(MC_Player p) {
        for (int i = 0; i < 5;) {
            p.getWorld().spawnEntity(MC_EntityType.PRIMED_TNT, p.getLocation(), "Tnt");
            i++;
        }
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tnt", "bomb", "explode");
    }

    @Override
    public String getHelpMessage(MC_Player p) {
        return "Explode yourself OR a player.";
    }
}
