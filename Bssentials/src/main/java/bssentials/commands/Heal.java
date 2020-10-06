package bssentials.commands;

import org.bukkit.ChatColor;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class Heal extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (label.equalsIgnoreCase("heal")) {
            if (args.length == 0) {
                if (hasPerm(user, label)) {
                    user.setHealthAndFoodLevel(20, 20);
                    user.sendMessage(ChatColor.GREEN + "You have been healed!");
                    return true;
                } else {
                    user.sendMessage("No Permission");
                    return false;
                }
            } else {
                if (user.isAuthorized("bssentials.commands.heal.other")) {
                    User target = getUserByName(args[0]);
                    if (target == null) {
                        user.sendMessage(ChatColor.RED + "Could not find player!");
                        return false;
                    } else {
                        target.setHealthAndFoodLevel(20, 20);
                        target.sendMessage(ChatColor.GREEN + "You have been healed!");
                        user.sendMessage(ChatColor.GREEN + target.getName(true) + " has been healed!");
                        return true;
                    }
                } else {
                    user.sendMessage("No Permission");
                    return false;
                }
            }
        }

        if (label.equalsIgnoreCase("feed")) {
            if (args.length == 0) {
                if (hasPerm(user, label)) {
                    user.setHealthAndFoodLevel(-1, 20);
                    user.sendMessage(ChatColor.GREEN + "You have been fed!");
                } else user.sendMessage("&4No Permission");

                return true;
            } else {
                if (user.isAuthorized("bssentials.commands.feed.other")) {
                    User target = getUserByName(args[0]);
                    if (target == null) {
                        user.sendMessage(ChatColor.RED + "Could not find player!");
                        return false;
                    } else {
                        target.setHealthAndFoodLevel(-1, 20);
                        target.sendMessage(ChatColor.GREEN + "You have been fed!");
                        user.sendMessage(ChatColor.GREEN + target.getName(true) + " has been fed!");
                        return true;
                    }
                } else {
                    user.sendMessage("&4No Permission");
                    return false;
                }
            }
        }
        return false;
    }

}