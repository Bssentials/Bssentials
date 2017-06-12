package ml.bssentials.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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

        /* BSSENTIALS COMMAND */
        if (cmd.getName().equalsIgnoreCase("bssentials")) {
            if(!BssUtils.hasPermForCommand(player, cmd.getName().toLowerCase())){
                BssUtils.noPermMsg(player, cmd);
                return false;
            }
            if (args.length == 0) {
                player.sendMessage(ChatColor.GREEN + "Bssentials v" + main.getDescription().getVersion());
                player.sendMessage(ChatColor.GREEN + "By: " + StringUtils.join(main.getDescription().getAuthors(), ", "));
                return true;
            } else {
                switch (args[0]) {
                    case "version":
                    case "ver":
                        player.sendMessage(prefix + "Version: " + ChatColor.GREEN + main.getDescription().getVersion());
                        break;
                    case "authors":
                        player.sendMessage(prefix + "Authors: " + ChatColor.GREEN + main.getDescription().getAuthors());
                        break;
                    case "about":
                        player.sendMessage(
                                prefix + "Description: " + ChatColor.GREEN + main.getDescription().getDescription());
                        break;
                    default:
                        break;
                }
                return true;
            }
        }

        /* NICK COMMAND */
        if (cmd.getName().equalsIgnoreCase("nick")) {
            main.getConfig().set("playerdata." + player.getName() + ".nick", StringUtils.join(args, " "));
            player.setDisplayName(ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, " ")));
            main.saveConfig();
            return true;
        }

        /* INFO COMMAND */
        if (cmd.getName().equalsIgnoreCase("info")) {
            Bukkit.dispatchCommand(sender, "warp info");
            return true;
        }

        /* REMOVELAG COMMAND */

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
            if (BssUtils.hasPermForCommand(player, cmd.getName().toLowerCase())) {
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

        /* CONTROL COMMAND */
        if (cmd.getName().equalsIgnoreCase("control")) {
            if (BssUtils.hasPermForCommand(player, "control")){
                Player target = player.getServer().getPlayer(args[0]);
                String argss = StringUtils.join(args, " ").replace(args[0], "");
                if(argss.startsWith("/")) {
                    target.performCommand(argss.substring(1));
                    return true;
                } else {
                    target.chat(argss);
                    return true;
                }
            } else {
                BssUtils.noPermMsg(player, cmd);
            }
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
            if (player.getName() == player.getDisplayName()) {
                sender.sendMessage("Your nickname and real name are the same!");
                return true;
            } else {
                player.setDisplayName(player.getName());
                sender.sendMessage("Reseted your name!");
                return true;
            }
        }

        /* GAMEMODE COMMAND */
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.GRAY + " Usage /gm <0|1>");
            } else if (BssUtils.hasPermForCommand(player, "gamemode")) {
                if (args.length == 1) {
                    String m = String.valueOf(args[0]);
                    if (m.equalsIgnoreCase("0") || m.equalsIgnoreCase("survival")) {
                        player.setGameMode(GameMode.SURVIVAL);
                        return true;
                    }
                    if (m.equalsIgnoreCase("1") || m.equalsIgnoreCase("creative")) {
                        player.setGameMode(GameMode.CREATIVE);
                        return true;
                    }
                    if (m.equalsIgnoreCase("2") || m.equalsIgnoreCase("adventure")) {
                        player.setGameMode(GameMode.ADVENTURE);
                        return true;
                    }
                    if (m.equalsIgnoreCase("3") || m.equalsIgnoreCase("spectator")) {
                        player.setGameMode(GameMode.SPECTATOR);
                        return true;
                    }
                    player.setGameMode(GameMode.valueOf(m));
                    return true;
                } else {
                    sender.sendMessage("Wrong args!");
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + "[Bssentials] " + ChatColor.RED + "You don't have permission: bssentials.command.gamemode");
                return false;
            }
        }

        /* SETSPAWN COMMAND */
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            if (BssUtils.hasPermForCommand(sender, "setspawn")) {
                main.createWarp(player, "spawn");
                return true;
            } else {
                sender.sendMessage("No Permission");
                return false;
            }
        }

        /* SPAWN COMMAND */
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (main.warps.getConfigurationSection("warps.spawn") == null) {
                sender.sendMessage(ChatColor.RED + "Spawn has not been set!");
                return false;
            } else {
                if (args.length == 0) {
                    World w = Bukkit.getServer().getWorld(main.warps.getString("warps.spawn.world"));
                    double x = main.warps.getDouble("warps.spawn.x");
                    double y = main.warps.getDouble("warps.spawn.y");
                    double z = main.warps.getDouble("warps.spawn.z");
                    float yaw = main.warps.getInt("warps.spawn.yaw");
                    float pitch = main.warps.getInt("warps.spawn.pitch");
                    player.teleport(new Location(w, x, y, z, yaw, pitch));
                    sender.sendMessage(ChatColor.GREEN + "Warping to spawn");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid args");
                    return false;
                }
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
                    } else {
                        banned = "not banned!";
                    }
                    sender.sendMessage(target.getName() + " is " + banned);
                    return true;
                }
            }
        }

        /* FLY COMMAND */
        if(cmd.getName().equalsIgnoreCase("fly")) {
            if (!(BssUtils.hasPermForCommand(sender, "fly"))) {
                sender.sendMessage("You can't fly!");
            } else {
                if(args.length == 1){
                    Player plr = (Player) sender;
                    if(args[0].equalsIgnoreCase("off")) {
                        plr.setFlying(false);
                    } else if(args[0].equalsIgnoreCase("on")){
                        if (!(Bukkit.getAllowFlight())) {
                            plr.setAllowFlight(true);
                        }
                        plr.setFlying(true);
                    }
                } else if (args.length == 2){
                    Player fly = main.getServer().getPlayer(args[1]);
                    if(args[0].equalsIgnoreCase("off")) {
                        fly.setFlying(false);
                    } else if(args[0].equalsIgnoreCase("on")){
                        fly.setFlying(true);
                    }
                } else {
                    sender.sendMessage(prefix + "Usage is /fly [on/off] or /fly [on/off] [player]");
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

        /* INVSEE COMMAND */
        if (cmd.getName().equalsIgnoreCase("invsee")) {
            if (args.length == 0) {
                sender.sendMessage("Wrong args!");
            } else if (args.length == 1) {
                if (sender.hasPermission("bssentials.command.invsee")) {
                    Player targetPlayer = player.getServer().getPlayer(args[0]);
                    ((Player) sender).openInventory(targetPlayer.getInventory());
                    sender.sendMessage(prefix + "Openned Inv.");
                } else {
                    sender.sendMessage(prefix + "No Permission");
                }
            }
        }

        /* ALIAS COMMAND */
        if (cmd.getName().equalsIgnoreCase("alias")) {
            sender.sendMessage("ml.bssentials.main.Bssentials: Execption");
            return false;
        }
        return true;
    }
}
