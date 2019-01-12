package bssentials.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CmdInfo(onlyPlayer = true)
public class SetSpawn extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length > 1) {
            message(sender, ChatColor.RED + "Usage: /setspawn");
            return true;
        }

        Location loc = ((Player) sender).getLocation();

        try {
            getPlugin().getWarps().setWarp("spawn", loc);
            message(sender, ChatColor.GREEN + "Spawn set!");
        } catch (IOException e) {
            e.printStackTrace();
            message(sender, ChatColor.RED + "Unable to write to spawn.yml");
        }
        return true;
    }

}