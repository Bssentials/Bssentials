package bssentials.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import bssentials.api.User;

@CmdInfo(aliases = {"awayfromkeyboard", "away"})
public class Afk extends BCommand {

    public static Collection<String> afkmap = new ArrayList<>();
    public static boolean displaynameafk;

    @Override
    public boolean onCommand(User sender, String label, String[] args) {
        if (args.length > 0) {
            message(sender, ChatColor.DARK_RED + "Usage: /afk");
            return true;
        }
        setAFK(sender, !isAFK(sender.getUniqueId()));
        return false;
    }

    /**
     * Check if player is away from keyboard.
     *
     * @return if player is AFK or not.
     * @param target The player to check.
     */
    public static boolean isAFK(UUID uuid) {
        try {
            return afkmap.contains(uuid.toString());
        } catch (NullPointerException ingore) {
            return false;
        }
    }

    public static void setAFK(User target, boolean status) {
        String name = target.getName(displaynameafk);
        if (status) {
            if (!isAFK(target.getUniqueId())) {
                afkmap.add(target.getUniqueId().toString());
                Bukkit.broadcastMessage(ChatColor.GRAY + "* " + name + ChatColor.GRAY + " is now AFK!");
            }
        } else {
            if (isAFK(target.getUniqueId())) {
                afkmap.remove(target.getUniqueId().toString());
                Bukkit.broadcastMessage(ChatColor.GRAY + "* " + name + ChatColor.GRAY + " is no longer AFK!");
            }
        }
    }

}