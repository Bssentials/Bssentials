package Bssentials_Rainbow.commands;

import java.util.Arrays;
import java.util.List;

import Bssentials_Rainbow.api.CommandBase;
import PluginReference.MC_Player;

public class Broadcast extends CommandBase {
    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getCommandName() {
        return "broadcast";
    }

    @Override
    public boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args) {
        if (args.length > 0) {
            sender.getServer().broadcastMessage(
                    "[Broadcast] " + Arrays.asList(args).toString().replace("[", "").replace("]", ""));
            return true;
        }
        return false;
    }

    @Override
    public String getHelpMessage(MC_Player p) {
        return "Broadcast a message!";
    }
}
