package Bssentials_Rainbow.commands;

import java.util.List;

import PluginReference.ChatColor;
import PluginReference.MC_Command;
import PluginReference.MC_Player;

public abstract class CommandBase implements MC_Command {
    public boolean onlyPlayer() {
        return false;
    }

    public boolean senderHasPerm(MC_Player sender) {
        return (sender == null) || (sender.hasPermission("bssentials.command." + getCommandName()));
    }

    @SuppressWarnings("null")
    public void sendMessage(MC_Player cs, String message) {
        if (cs == null) {
            cs.sendMessage(PluginReference.ChatColor.StripColor(message));
        } else {
            cs.sendMessage(message);
        }
    }

    public String getServerMod() {
        return "RAINBOW";
    }

    public boolean isRails() {
        try {
            Class.forName("org.projectrails.Rails");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public abstract boolean onRainbowCommand(MC_Player p, String cmdname, String[] args);

    public abstract String getHelpMessage(MC_Player p);

    @Override
    public String getHelpLine(MC_Player p) {
        return getCommandName() + " ---- " + getHelpMessage(p);
    }

    @Override
    public List<String> getTabCompletionList(MC_Player p, String[] args) {
        return null;
    }

    @Override
    public void handleCommand(MC_Player p, String[] args) {
        if (onlyPlayer() && (p == null)) {
            sendMessage(p, "[Bssentials] Player only command!");
            return;
        }
        if (!(hasPermissionToUse(p))) {
            sendMessage(p, ChatColor.RED + "You dont have permission!");
            return;
        }
        onRainbowCommand(p, getCommandName(), args);
    }

    @Override
    public boolean hasPermissionToUse(MC_Player p) {
        return senderHasPerm(p);
    }
}