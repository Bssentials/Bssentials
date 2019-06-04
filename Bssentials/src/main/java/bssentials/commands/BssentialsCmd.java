package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

@CmdInfo(aliases = {"bss", "ess", "essentials"})
public class BssentialsCmd extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        PluginDescriptionFile des = getPlugin().getDescription();
        message(sender, ChatColor.GREEN + "Running Bssentials " + des.getVersion());
        message(sender, ChatColor.GREEN + "Authors: " + des.getVersion());

        for (String p : des.getAuthors()) message(sender, ChatColor.GREEN + "  - " + p);

        return true;
    }

}