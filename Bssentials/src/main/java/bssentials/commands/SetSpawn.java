package bssentials.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class SetSpawn extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length > 1)
            return message(user, ChatColor.RED + "Usage: /setspawn");

        Location loc = user.getLocation();

        try {
            getPlugin().getWarps().setWarp("spawn", loc);
            user.sendMessage(ChatColor.GREEN + "Spawn set!");
        } catch (IOException e) {
            e.printStackTrace();
            user.sendMessage(ChatColor.RED + "Unable to write to spawn.yml");
        }
        return true;
    }

}