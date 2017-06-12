package ml.bssentials.commands;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.api.BssUtils;
import ml.bssentials.main.Bssentials;
import ml.bssentials.main.Perms;

public class Warp implements CommandExecutor {
    private Bssentials main;
    public Warp(Bssentials bss) {
        main = bss;
    }

    @Override public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You are not a player");
            return false;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (player.hasPermission(Perms.WARP.permission)) {
                if (main.warps.getConfigurationSection("warps") == null) {
                    sender.sendMessage(ChatColor.RED + "No warps set!");
                } else {
                    if (args.length == 1) {
                        if(main.warps.getConfigurationSection("warps." + args[0]) != null) {
                            World w = Bukkit.getServer().getWorld(main.warps.getString("warps." + args[0] + ".world"));
                            double x = main.warps.getDouble("warps." + args[0] + ".x");
                            double y = main.warps.getDouble("warps." + args[0] + ".y");
                            double z = main.warps.getDouble("warps." + args[0] + ".z");
                            float yaw = main.warps.getInt("warps" + args[0] + ".yaw");
                            float pitch = main.warps.getInt("warps" + args[0] + "pitch");

                            main.teleport(player, new Location(w,x,y,z,yaw,pitch));
                            sender.sendMessage(ChatColor.GREEN + "Warping to " + args[0]);
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "No warp by that name exists.");
                            return false;
                        }
                    } else if (args.length == 2 ) {
                        if (player.hasPermission(Perms.WARP_OTHERS.permission)) {
                            Player targetPlayer = player.getServer().getPlayer(args[1]);
                            if(targetPlayer != null) {
                                if(main.warps.getConfigurationSection("warps." + args[0]) != null) {
                                    World w = Bukkit.getServer().getWorld(main.warps.getString("warps." + args[0] + ".world"));
                                    double x = main.warps.getDouble("warps." + args[0] + ".x");
                                    double y = main.warps.getDouble("warps." + args[0] + ".y");
                                    double z = main.warps.getDouble("warps." + args[0] + ".z");
                                    main.teleport(targetPlayer, new Location(w, x, y, z));
                                    sender.sendMessage(ChatColor.GREEN + "Warping " + args[1] + " to " + args[0]);
                                    targetPlayer.sendMessage(ChatColor.GREEN + player.getName() + " warped you to " + args[0]);
                                } else {
                                    sender.sendMessage(ChatColor.RED + "No warp by that name exists.");
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "You do not have permission to warp other players.");
                        }
                    } else if (args.length == 0 ) {
                        Set<String> keys = main.warps.getConfigurationSection("warps").getKeys(false);
                        sender.sendMessage(ChatColor.BLUE + "List of warps:");
                        String warpList = "";
                        for (String s:keys) {
                            warpList = warpList + s + ", ";
                        }
                        sender.sendMessage(ChatColor.BLUE + warpList);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid args");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have permission to warp.");
            }
        }

        /* SETWARP COMMAND */
        if (cmd.getName().equalsIgnoreCase("setwarp")) {
            if (args.length == 1) {
                String warpname = args[0];
                if (main.warps.getConfigurationSection("warps." + warpname) != null) {
                    if (sender.hasPermission(Perms.SETWARP_OR.permission)) {
                        main.createWarp(player, args[0]);
                    } else {
                        sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.RED + " You can't overwrite that");
                    }
                } else {
                    if (sender.hasPermission(Perms.SETWARP.permission) || sender.isOp()) {
                        main.createWarp(player, args[0]);
                    }
                }
            } else  {
                sender.sendMessage(ChatColor.RED + "Invalid args");
            }
        }

        /* DELWARP COMMAND */
        if (cmd.getName().equalsIgnoreCase("delwarp")) {
            if (player.hasPermission(Perms.SETWARP_OR.permission)) {
                try {
                    if(main.warps.getConfigurationSection("warps." + args[0]) != null) {
                        main.warps.set("warps." + args[0], null);
                        main.saveWarpConfig();
                        sender.sendMessage(ChatColor.GREEN + "Deleted warp " + args[0]);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Unable to find warp to delete.");
                    }
                } catch(Exception e) {
                    sender.sendMessage(ChatColor.RED + "Unable to delete warp.");
                }
            } else {
                BssUtils.noPermMsg(sender, cmd);
            }
        }
        return true;
    }
}
