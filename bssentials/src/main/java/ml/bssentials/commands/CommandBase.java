package ml.bssentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import PluginReference.MC_Command;
import PluginReference.MC_Player;

import java.util.List;

import org.bukkit.ChatColor;

import ml.bssentials.api.BssUtils;

/**
 * Multiplatform Command System.
 */
public abstract class CommandBase implements CommandExecutor, MC_Command {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (onlyPlayer()) {
            if(!(sender instanceof Player)){
                sender.sendMessage("[Bssentials] Player only command!");
                return false;
            }
        }
        if (!(senderHasPerm(sender, cmd))){
            BssUtils.noPermMsg(sender, cmd);
            return false;
        }

        return onCommand(sender, cmd, args);
    }

    public boolean onlyPlayer() {
        return false;
    }

    public boolean senderHasPerm(CommandSender sender, Command cmd) {
        return BssUtils.hasPermForCommand(sender, cmd.getName().toLowerCase());
    }

    public boolean senderHasPerm(MC_Player sender) {
        return (sender == null) || (sender.hasPermission("bssentials.command." + getCommandName()));
    }

    public void sendMessage(CommandSender cs, String message) {
        if (cs instanceof Player) {
            cs.sendMessage(message);
        } else {
            cs.sendMessage(ChatColor.stripColor(message));
        }
    }

    @SuppressWarnings("null")
    public void sendMessage(MC_Player cs, String message) {
        if (!(cs == null)) {
            cs.sendMessage(message);
        } else {
            cs.sendMessage(PluginReference.ChatColor.StripColor(message));
        }
    }

    public String getServerMod() {
        return BssUtils.getServerMod();
    }

    public boolean isSpigot() {
        String s = getServerMod();
        return (s.equalsIgnoreCase("spigot") || s.equalsIgnoreCase("paper"));
    }

    public boolean isPaper() {
        if (!isSpigot()) {
            return false;
        }
        return getServerMod().equalsIgnoreCase("paper");
    }

    /* BUKKIT */public abstract boolean onCommand(CommandSender sender, Command cmd, String[] args);
    /* RAINBOW */public abstract boolean onRainbowCommand(MC_Player sender, String cmdname, String[] args);
    // Rainbow

    @Override
    public String getHelpLine(MC_Player arg0) {
        return getCommandName() + " ---- An Bssentials Command";
    }

    @Override
    public List<String> getTabCompletionList(MC_Player arg0, String[] arg1) {
        return null; // TODO Auto-generated method stub
    }

    @SuppressWarnings("null")
    @Override
    public void handleCommand(MC_Player arg0, String[] arg1) {
        if (onlyPlayer() && (arg0 == null)) {
            arg0.sendMessage("[Bssentials] Player only command!");
            return;
        }
        if (!(hasPermissionToUse(arg0))) {
            arg0.sendMessage("You dont have permission!");
            return;
        }
        onRainbowCommand(arg0, getCommandName(), arg1);
    }

    @Override
    public boolean hasPermissionToUse(MC_Player arg0) {
        return arg0.hasPermission("bssentials.command." + getCommandName());
    }
}