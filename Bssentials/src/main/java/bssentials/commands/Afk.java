package bssentials.commands;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Afk extends BCommand {
    public static Collection<String> afkmap = new ArrayList<>();
    public static boolean displaynameafk;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length > 0) {
            message(sender, ChatColor.DARK_RED + "Usage: /afk");
            return true;
        }
        setAFK((Player) sender, !isAFK((Player) sender));
        return false;
    }

    /**
     * Check if player is away from keybord.
     *
     * @return true if player is afk, false if not.
     * @param target
     *            The player to check.
     */
    public static boolean isAFK(Player target) {
        try {
            return afkmap.contains(target.getUniqueId().toString());
        } catch (NullPointerException ingore) {
            return false;
        }
    }

    public static void setAFK(Player target, boolean status) {
        setAFK(target, status, true);
    }

    public static void setAFK(Player target, boolean status, boolean broadcast) {
        String name = (displaynameafk ? target.getDisplayName() : target.getName());
        if (status) {
            if (!isAFK(target)) {
                afkmap.add(target.getUniqueId().toString());
                if (broadcast) {
                    Bukkit.broadcastMessage(ChatColor.GRAY + "* " + name + ChatColor.GRAY + " is now AFK!");
                }
            }
        } else {
            if (isAFK(target)) {
                afkmap.remove(target.getUniqueId().toString());
                if (broadcast) {
                    Bukkit.broadcastMessage(ChatColor.GRAY + "* " + name + ChatColor.GRAY + " is no longer AFK!");
                }
            }
        }
    }
}
