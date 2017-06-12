package Bssentials_Rainbow.commands;

import java.util.List;

import Bssentials_Rainbow.api.CommandBase;
import PluginReference.MC_Player;

public class Pm extends CommandBase {
    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getCommandName() {
        return "pm";
    }

    @Override
    public boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args) {
        sender.sendMessage("Not added yet, Sorry :(");
        return false;
    }

    @Override
    public String getHelpMessage(MC_Player p) {
        return "Private message a player!";
    }
}
