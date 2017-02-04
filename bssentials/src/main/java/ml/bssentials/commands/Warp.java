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

import ml.bssentials.main.Bssentials;

public class Warp implements CommandExecutor {
    private Bssentials main;
    
    public Warp(Bssentials bss) {
        main = bss;
    }

    @SuppressWarnings("deprecation") 
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You are not a player");
            return false;
        }
        Player player = (Player) sender;

        /* WARP COMMAND */
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if(player.hasPermission(Bssentials.WARP_PERM)) {
                if (main.getWarpConfig().getConfigurationSection("warps") == null) {
                    sender.sendMessage(ChatColor.RED + "No warps set!");
                } else {
                    if (args.length == 1) {
                        if(main.getWarpConfig().getConfigurationSection("warps." + args[0]) != null) {
                            World w = Bukkit.getServer().getWorld(main.getWarpConfig().getString("warps." + args[0] + ".world"));
                            double x = main.getWarpConfig().getDouble("warps." + args[0] + ".x");
                            double y = main.getWarpConfig().getDouble("warps." + args[0] + ".y");
                            double z = main.getWarpConfig().getDouble("warps." + args[0] + ".z");
                            float yaw = main.getWarpConfig().getInt("warps" + args[0] + ".yaw");
                            float pitch = main.getWarpConfig().getInt("warps" + args[0] + "pitch");
                            
                            main.teleport(player, new Location(w,x,y,z,yaw,pitch));
                            sender.sendMessage(ChatColor.GREEN + "Warping to " + args[0]);
                        } else {
                            sender.sendMessage(ChatColor.RED + "No warp by that name exists.");
                        }
                    } else if (args.length == 2 ) {
                        if(player.hasPermission(Bssentials.WARP_OTHERS_PERM)) {
                            Player targetPlayer = player.getServer().getPlayer(args[1]);
                            if(targetPlayer != null) {
                                if(main.getWarpConfig().getConfigurationSection("warps." + args[0]) != null) {
                                    World w = Bukkit.getServer().getWorld(main.getWarpConfig().getString("warps." + args[0] + ".world"));
                                    double x = main.getWarpConfig().getDouble("warps." + args[0] + ".x");
                                    double y = main.getWarpConfig().getDouble("warps." + args[0] + ".y");
                                    double z = main.getWarpConfig().getDouble("warps." + args[0] + ".z");
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
                        Set<String> keys = main.getWarpConfig().getConfigurationSection("warps").getKeys(false);
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
                if (main.getWarpConfig().getConfigurationSection("warps." + warpname) != null) {
                    if (sender.hasPermission(Bssentials.SETWARP_OR_PERM)) {
                        main.createWarp(player, args[0]);
                    } else {
                        sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.RED + " You can't overwrite that");
                    }
                } else {
                    if (sender.hasPermission(Bssentials.SETWARP_PERM) || sender.isOp()) {
                        main.createWarp(player, args[0]);
                    }
                }
            } else  {
                sender.sendMessage(ChatColor.RED + "Invalid args");
            }
        }
        
        /* DELWARP COMMAND */
        if (cmd.getName().equalsIgnoreCase("delwarp")) {
            if (player.hasPermission(Bssentials.SETWARP_OR_PERM)) {
                try {
                    if(main.getWarpConfig().getConfigurationSection("warps." + args[0]) != null) {
                        main.getWarpConfig().set("warps." + args[0], null);
                        main.saveWarpConfig();
                        sender.sendMessage(ChatColor.GREEN + "Deleted warp " + args[0]);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Unable to find warp to delete.");
                    }
                } catch(Exception e) {
                    sender.sendMessage(ChatColor.RED + "Unable to delete warp.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to delete warps.");
            }
        }
        return true;
    }
}
