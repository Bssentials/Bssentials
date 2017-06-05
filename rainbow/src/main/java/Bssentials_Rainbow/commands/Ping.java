package Bssentials_Rainbow.commands;

import java.util.List;

import PluginReference.MC_Player;

public class Ping extends CommandBase {
    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getCommandName() {
        return "ping";
    }

    @Override
    public boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args) {
        if (sender == null) {
            System.out.println("Pong");
        } else {
            sender.sendMessage("Pong");
        }
        return false;
    }

    @Override
    public String getHelpMessage(MC_Player p) {
        return "Pong";
    }
}
