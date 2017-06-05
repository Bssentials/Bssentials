package Bssentials_Rainbow.commands;

import java.util.List;

import Bssentials_Rainbow.MyPlugin;
import PluginReference.MC_Player;

public class Staff extends CommandBase {
    public MyPlugin main;

    public Staff(MyPlugin m) {
        this.main = m;
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getCommandName() {
        return "staff";
    }

    @Override
    public boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args) {
        sender.sendMessage("Not added yet");
        return false;
    }

    @Override
    public String getHelpMessage(MC_Player p) {
        return "List all online staff members.";
    }
}
