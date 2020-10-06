package bssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

import bssentials.api.User;

@CmdInfo(aliases = {"bss", "ess", "essentials"})
public class BssentialsCmd extends BCommand {

    @Override
    public boolean onCommand(User sender, String label, String[] args) {
        PluginDescriptionFile des = getPlugin().getDescription();
        message(sender, ChatColor.GREEN + "Running Bssentials " + des.getVersion());
        message(sender, ChatColor.GREEN + "Authors: https://bit.ly/bssentialscontributors");

        for (String p : des.getAuthors()) message(sender, ChatColor.GREEN + "  - " + p);

        return true;
    }

}