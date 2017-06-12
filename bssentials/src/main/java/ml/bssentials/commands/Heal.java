package ml.bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.main.Perms;

public class Heal extends CommandBase {
    @Override
    public boolean onlyPlayer() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("heal")) {
            if (args.length == 0) {
                if (player.hasPermission(Perms.HEAL.permission)) {
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.GREEN + "You have been healed!");
                    return true;
                } else {
                    sender.sendMessage("No Permission");
                    return false;
                }
            } else {
                if (sender.hasPermission(Perms.HEAL_OUTHER.permission)) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Could not find player!");
                        return false;
                    } else {
                        target.setHealth(20);
                        target.setFoodLevel(20);
                        target.sendMessage(ChatColor.GREEN + "You have been healed!");
                        player.sendMessage(ChatColor.GREEN + target.getName() + " has been healed!");
                        return true;
                    }
                } else {
                    sender.sendMessage("No Permission");
                    return false;
                }
            }
        }

        if (cmd.getName().equalsIgnoreCase("underheal")) {
            player.setHealth(1);
            player.setFoodLevel(1);
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("feed")) {
            if (args.length == 0) {
                if (senderHasPerm(player, cmd)) {
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.GREEN + "You have been fed!");
                    return true;
                } else {
                    sender.sendMessage("No Permission");
                    return false;
                }
            } else {
                if (sender.hasPermission(Perms.FEED_OUTHER.permission)) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Could not find player!");
                        return false;
                    } else {
                        target.setFoodLevel(20);
                        target.sendMessage(ChatColor.GREEN + "You have been fed!");
                        player.sendMessage(ChatColor.GREEN + target.getName() + " has been fed!");
                        return true;
                    }
                } else {
                    sender.sendMessage("No Permission");
                    return false;
                }
            }
        }
        return false;
    }
}