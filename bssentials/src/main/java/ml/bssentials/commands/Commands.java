package ml.bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import ml.bssentials.api.BssUtils;
import ml.bssentials.main.Bssentials;

public class Commands implements CommandExecutor {
    private Bssentials main;
    public Commands(Bssentials bss) {
        main = bss;
    }

    @Override @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You are not a player");
            return false;
        }
        String prefix = Bssentials.prefix;

        Player player = (Player) sender;

        /* INFO COMMAND */
        if (cmd.getName().equalsIgnoreCase("info")) {
            Bukkit.dispatchCommand(sender, "warp info");
            return true;
        }

        /* RAIN COMMAND */
        if (cmd.getName().equalsIgnoreCase("rain")) {
            Bukkit.dispatchCommand(sender, "weather rain");
            return true;
        }

        /* DAY COMMAND */
        if (cmd.getName().equalsIgnoreCase("day")) {
            Bukkit.dispatchCommand(sender, "time set day");
            return true;
        }

        /* NIGHT COMMAND */
        if (cmd.getName().equalsIgnoreCase("night")) {
            Bukkit.dispatchCommand(sender, "time set night");
            return true;
        }

        /* CI COMMAND */
        if (cmd.getName().equalsIgnoreCase("ci")) {
            if (BssUtils.hasPermForCommand(player, "ci")) {
                player.getInventory().clear();
                sender.sendMessage(prefix + "Inventory cleared!");
                return true;
            } else {
                BssUtils.noPermMsg(player, cmd);
                return true;
            }
        }

        /* HAT COMMAND */
        if (cmd.getName().equalsIgnoreCase("hat")) {
            if (BssUtils.hasPermForCommand(player, "hat")) {
                if (player.getItemInHand().getType() != Material.AIR) {
                    ItemStack itemHand = player.getItemInHand();
                    PlayerInventory inventory = player.getInventory();
                    ItemStack itemHead = inventory.getHelmet();
                    inventory.removeItem(new ItemStack[]{itemHand});
                    inventory.setHelmet(itemHand);
                    inventory.setItemInHand(itemHead);
                    player.sendMessage(ChatColor.YELLOW + "Item successfuly put on your head.");
                    return true;
                }
                player.sendMessage(ChatColor.YELLOW + "You must have something to put on your head!");
                return true;
            }
            BssUtils.noPermMsg(player, cmd);
            return true;
        }

        /* RANK COMMAND */
        if (cmd.getName().equalsIgnoreCase("rank")) {
            if (BssUtils.hasPermForCommand(player, "rank")) {
                if (args.length == 0) {
                    sender.sendMessage("Use /rank create <rankname> <display>");
                    sender.sendMessage("Or: /rank setplayer <player> <rank>");
                } else if (args[0].equalsIgnoreCase("create")) {
                    main.getConfig().set("ranks."+args[1]+".prefix", args[2]);
                    main.saveConfig();
                    sender.sendMessage("Created rank: "+args[1]+" With the display of: " +args[2]);
                } else if (args[0].equalsIgnoreCase("setplayer")) {
                    main.getConfig().set("playerdata." + args[1] + ".rank", args[2]);
                    main.saveConfig();
                    sender.sendMessage("Added "+args[1]+" to the rank" + args[2]);
                }
            } else {
                BssUtils.noPermMsg(player, cmd);
            }
        }

        /* DISNICK COMMAND */
        if (cmd.getName().equalsIgnoreCase("disnick")) {
            sender.sendMessage(ChatColor.RED + "This command will be replaced with \"/nick off\" in the next release!");
            if (player.getName() == player.getDisplayName()) {
                sender.sendMessage("Your nickname and real name are the same!");
                return true;
            } else {
                player.setDisplayName(player.getName());
                sender.sendMessage("Reseted your name!");
                return true;
            }
        }

        /* WELCOME COMMAND */
        if (cmd.getName().equalsIgnoreCase("welcome")) {
            if (args.length == 0) {
                sender.sendMessage("Wrong args!");
                return false;
            } else if (args.length == 1) {
                if (sender.hasPermission("bssentials.command.welcome")) {
                    Player theNewPlayer = player.getServer().getPlayer(args[0]);
                    theNewPlayer.sendMessage(ChatColor.YELLOW + sender.getName() + " " + ChatColor.AQUA + "says Welcome to The Server!");
                    sender.sendMessage("You welcomed " + args[0] + " to the server");
                    return true;
                } else {
                    sender.sendMessage("No Permission");
                    return false;
                }
            }
        }

        /* CHECKBAN COMMAND */
        if (cmd.getName().equalsIgnoreCase("checkban")) {
            if (args.length == 0) {
                sender.sendMessage("Banned Players: " + Bukkit.getBannedPlayers());
                return true;
            } else {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Could not find player!");
                } else {
                    String banned;
                    if (target.isBanned()) {
                        banned = "banned!";
                    } else banned = "not banned!";

                    sender.sendMessage(target.getName() + " is " + banned);
                    return true;
                }
            }
        }

        /* REPAIR COMMAND */
        if (cmd.getName().equalsIgnoreCase("repair")) {
            if (BssUtils.hasPermForCommand(player, "repair")) {
                player.getItemInHand().setDurability((short) 0);
                sender.sendMessage(prefix + "Repaired!");
                return true;
            } else {
                sender.sendMessage(prefix + "No Permission!");
                return false;
            }
        }

        return true;
    }
}
