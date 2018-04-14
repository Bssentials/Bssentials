package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import bssentials.Bssentials;

public class BssentialsCmd extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        PluginDescriptionFile des = Bssentials.get().getDescription();
        message(sender, ChatColor.GREEN + "Running Bssentials " + des.getVersion());

        return true;
    }

}
