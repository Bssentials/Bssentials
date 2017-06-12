package Bssentials_Rainbow.commands;

import java.util.List;

import Bssentials_Rainbow.api.CommandBase;
import PluginReference.MC_Player;

public class ViewNick extends CommandBase {
    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getCommandName() {
        return "viewnick";
    }

    @Override
    public boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args) {
        sender.sendMessage("TODO: Not added yet.");
        return true;
    }

    @Override
    public String getHelpMessage(MC_Player p) {
        return "Views a player's nick/display name.";
    }
}
