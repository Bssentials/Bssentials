package Bssentials_Rainbow.commands;

import java.util.List;

import Bssentials_Rainbow.api.CommandBase;
import PluginReference.MC_Player;

public class UUIDCommand extends CommandBase {
    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getCommandName() {
        return "uuid";
    }

    @Override
    public boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args) {
        sender.sendMessage("Your UUID: " + sender.getUUID());
        return true;
    }

    @Override
    public String getHelpMessage(MC_Player p) {
        return "Shows your uuid.";
    }
}