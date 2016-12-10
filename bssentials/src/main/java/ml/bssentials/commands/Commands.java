package ml.bssentials.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
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
	
    @SuppressWarnings("deprecation") // getPlayer() is deprecated
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    	if(!(sender instanceof Player)){
    		sender.sendMessage("You are not a player");
    		return false;
        }
    	String prefix = Bssentials.prefix;
    	
        String authors = "Isaiah Patton, & ramidzkh";
        Player player = (Player) sender;
        
        /* BSSENTIALS COMMAND */
        if (cmd.getName().equalsIgnoreCase("bssentials")) {
            if(!BssUtils.hasPermForCommand(player, cmd.getName().toLowerCase())){
                BssUtils.noPermMsg(player, cmd);
                return false;
            }
            String pre = prefix;
		
            if (args.length == 0) {
                player.sendMessage(pre + "Name: " + ChatColor.GREEN + "Bssentials");
                player.sendMessage(pre + "Version: " + ChatColor.GREEN + Bssentials.version);
                player.sendMessage(pre + "Authors: " + ChatColor.GREEN + authors);
                player.sendMessage(pre + "Description: " + ChatColor.GREEN + "Essentials for 1.10");
                player.sendMessage(pre + "Song of the day: " + main.getConfig().getString("songOfTheDay"));
            } else {
                if (args[0] == "version") { player.sendMessage(pre + "Version: " + ChatColor.GREEN + Bssentials.version); }
                if (args[0] == "authors") { player.sendMessage(pre + "Authors: " + ChatColor.GREEN + authors); }
                if (args[0] == "about")   { player.sendMessage(pre + "Description: " + ChatColor.GREEN + "Essentials for 1.10"); }
                if (args[0] == "sotd")    { player.sendMessage(pre + "Song of the day: " + main.getConfig().getString("songOfTheDay")); }
            }
        }
     
        /* NICK COMMAND */
        if (cmd.getName().equalsIgnoreCase("nick")) {
            main.getConfig().set("playerdata." + player.getName() + ".nick", StringUtils.join(args, " "));
            player.setDisplayName(ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, " ")));
            main.saveConfig();
        }
        
        /* INFO COMMAND */
        if (cmd.getName().equalsIgnoreCase("info")) {
            Bukkit.dispatchCommand(sender, "warp info");
        }

        /* REMOVELAG COMMAND */
        if (cmd.getName().equalsIgnoreCase("removelag")) {
        	if (args.length == 0) {
        		Bukkit.dispatchCommand(sender, "lagg gc");
        		Bukkit.dispatchCommand(sender, "lagg clear");
        		Bukkit.dispatchCommand(sender, "lagg unloadchunks");
        		sender.sendMessage(Bssentials.prefix + "Removed Lagg!");
        	} else if (args.length !=0 && args[0].equalsIgnoreCase("unloadchunks")) {
                if (BssUtils.hasPermForCommand(player, "removelag.unloadchunks")) {
                    if (args.length == 2 || args.length == 1) {
                        World w = null;
                        if (args.length == 2) {
                            try {
                                w = Bukkit.getWorld((String)args[1]);
                            }
                            catch (Exception e) {
                                sender.sendMessage("World \"" + args[1] + "\" could not be found.");
                                return true;
                            }
                        } else if (player != null) {
                            w = player.getWorld();
                        }
                        if (w == null) {
                            sender.sendMessage("/removelag unloadchunks <world>");
                        } else {
                            int c = 0;
                            if (w.getPlayers().size() == 0) {
                                for (Chunk chunk : w.getLoadedChunks()) {
                                    w.unloadChunk(chunk);
                                    ++c;
                                }
                                sender.sendMessage(c + " chunks in world \"" + w.getName() + "\" have been unloaded.");
                            } else {
                            	sender.sendMessage("Unloading chunks in worlds that contain players has been disabled due to glitches.");
                            }
                        }
                    } else {
                        sender.sendMessage("/removelag unloadchunks <world>");
                    }
                } else {
                    BssUtils.noPermMsg(player, cmd);
                }
                return true;
            }
        }

        /* RAIN COMMAND */
        if (cmd.getName().equalsIgnoreCase("rain")) {
            Bukkit.dispatchCommand(sender, "weather rain");
        }

        /* DAY COMMAND */
        if (cmd.getName().equalsIgnoreCase("day")) {
            Bukkit.dispatchCommand(sender, "time set day");
        }

        /* NIGHT COMMAND */
        if (cmd.getName().equalsIgnoreCase("night")) {
            Bukkit.dispatchCommand(sender, "time set night");
		}

        /* CI COMMAND */
        if (cmd.getName().equalsIgnoreCase("ci")) {
            if (BssUtils.hasPermForCommand(player, "ci")) {
                player.getInventory().clear();
                sender.sendMessage(prefix + "Inventory cleared!");
            } else {
                BssUtils.noPermMsg(player, cmd);
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
        	 	if(argss.contains("/")) {
        	 		target.performCommand(argss.replace("/", ""));
        	 	} else {
        	 		target.chat(argss);
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
        	} else {
        		player.setDisplayName(player.getName());
        		sender.sendMessage("Reseted your name!");
        	}
        }
        
        /* GAMEMODE COMMAND */
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.GRAY + " Usage /gm <0|1>");
            } else if (BssUtils.hasPermForCommand(player, "gamemode")) {
                if (args.length == 1 && args[0].equalsIgnoreCase("0")) {
                	player.setGameMode(GameMode.SURVIVAL);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("1")) {
                    player.setGameMode(GameMode.CREATIVE);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("survival")) {
                	player.setGameMode(GameMode.SURVIVAL);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("creative")) {
                	player.setGameMode(GameMode.CREATIVE);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("switch")) {
                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        player.setGameMode(GameMode.CREATIVE);
                    } else {
                        player.setGameMode(GameMode.SURVIVAL);
                    }
                }
			} else {
		        sender.sendMessage(ChatColor.GREEN + "[Bssentials] " + ChatColor.RED + "You don't have permission: bssentials.command.gamemode");  
            }
        }

        /* STAFF COMMAND */
        if (cmd.getName().equalsIgnoreCase("staff")) {
            if (args.length == 0) {
		
               	if (sender.hasPermission(Bssentials.STAFFLIST_PERM)) {
                    sender.sendMessage(ChatColor.GREEN + "[Bssentials] Staff:");

                    Set<String> keys = main.getConfig().getConfigurationSection("staff").getKeys(false);
                    String staffList = "";
                    for (String s:keys) {
                        staffList = staffList + s + ", ";
                    }
                    sender.sendMessage(ChatColor.BLUE + staffList);
				
                } else {
                        sender.sendMessage("No Permission");
                    }
                } else if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
                    if (sender.hasPermission(Bssentials.STAFFADD_PERM)) {
				
                        List<String> staffList = main.getConfig().getStringList("staff");
                 		String staff = args[1].toLowerCase();
                    
                 		if (!staffList.contains(staff)) {
                 			staffList.add(staff);
                 			main.getConfig().set("staff", staffList);
                 			main.saveConfig();
                 			sender.sendMessage(prefix + "Staff member added to the list");
                 		} else {
                 			sender.sendMessage(prefix + ChatColor.RED + ChatColor.BOLD + "Error! The name you have typed is already not on the list");
                 		}
					
                    } else {
                        sender.sendMessage("No permission");
                    }
    			} else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
                    if (sender.hasPermission(Bssentials.STAFFREMOVE_PERM)) {
					
                        List<String> staffList = main.getConfig().getStringList("staff");
                        String staff = args[1].toLowerCase();
                    
                        if (staffList.contains(staff)) {
                        		staffList.remove(staff);
                        		main.getConfig().set("staff", staffList);
                        		main.saveConfig();
                        		sender.sendMessage(prefix + "staff member removed from the list");
                        } else {
                            sender.sendMessage(prefix + ChatColor.RED + ChatColor.BOLD + "Error! The name you have typed is already not on the list");
                        }
                    
                    }
    			}
        }
        
        /* SETSPAWN COMMAND */
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            if (sender.hasPermission(Bssentials.SETSPAWN_PERM)) {
                main.createWarp(player, "spawn");
            } else {
                sender.sendMessage("No Permission");
            }
        }

        /* SPAWN COMMAND */
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (main.getWarpConfig().getConfigurationSection("warps.spawn") == null) {
                sender.sendMessage(ChatColor.RED + "Spawn has not been set!");
            } else {
                if (args.length == 0) {
                    World w = Bukkit.getServer().getWorld(main.getWarpConfig().getString("warps.spawn.world"));
                    double x = main.getWarpConfig().getDouble("warps.spawn.x");
                    double y = main.getWarpConfig().getDouble("warps.spawn.y");
                    double z = main.getWarpConfig().getDouble("warps.spawn.z");
                    player.teleport(new Location(w, x, y, z));
                    sender.sendMessage(ChatColor.GREEN + "Warping to spawn");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid args");
                }
            }
        }
        
        /* WELCOME COMMAND */
        if (cmd.getName().equalsIgnoreCase("welcome")) {
            if (args.length == 0) {
                sender.sendMessage("Wrong args!");
            } else if (args.length == 1) {
                if (sender.hasPermission("bssentials.command.welcome")) {
					Player theNewPlayer = player.getServer().getPlayer(args[0]);
                    theNewPlayer.sendMessage(ChatColor.YELLOW + sender.getName() + " " + ChatColor.AQUA + "says Welcome to The Server!");
                    sender.sendMessage("You welcomed " + args[0] + " to the server");
                } else {
                    sender.sendMessage("No Permission");
                }
			}
        }
        
        /* HEAL COMMAND */
        if (cmd.getName().equalsIgnoreCase("heal")) {
            if (args.length == 0) {
                if (player.hasPermission(Bssentials.HEAL_PERM)) {
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.GREEN + "You have been healed!");
                } else {
                    sender.sendMessage("No Permission");
                }
            } else {
                if (sender.hasPermission(Bssentials.HEAL_OUTHER_PERM)) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Could not find player!");
                    } else {
                        target.setHealth(20);
                        target.setFoodLevel(20);
                        target.sendMessage(ChatColor.GREEN + "You have been healed!");
                        player.sendMessage(ChatColor.GREEN + target.getName() + " has been healed!");
                    }
                } else {
                    sender.sendMessage("No Permission");
                }
            }
        }
        
        /* UNDERHEAL COMMMAND */
        if (cmd.getName().equalsIgnoreCase("underheal")) {
            player.setHealth(1);
            player.setFoodLevel(1); 
        }
        
        /* CHECKBAN COMMAND */
        if (cmd.getName().equalsIgnoreCase("checkban")) {
        	if (args.length == 0) {
        		sender.sendMessage("Banned Players: " + Bukkit.getBannedPlayers());
        	} else {
				Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Could not find player!");
                } else {
                	String banned;
                	if (target.isBanned() == true) {
                		banned = "banned!";
                	} else {
                		banned = "NOT banned!";
                	}
                	sender.sendMessage(target.getName() + " is " + banned);
                }
        	}
        }
        
        /* FEED COMMAND */
        if (cmd.getName().equalsIgnoreCase("feed")) {
            if (args.length == 0) {
                if (sender.hasPermission(Bssentials.FEED_PERM)) {
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.GREEN + "You have been fed!");
                } else {
                    sender.sendMessage("No Permission");
                }
            } else {
                if (sender.hasPermission(Bssentials.FEED_OUTHER_PERM)) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Could not find player!");
                    } else {
                        target.setFoodLevel(20);
                        target.sendMessage(ChatColor.GREEN + "You have been fed!");
                        player.sendMessage(ChatColor.GREEN + target.getName() + " has been fed!");
                    }
                } else {
                    sender.sendMessage("No Permission");
                }
            }
        }
        
        /* FLY COMMAND */
        if(cmd.getName().equalsIgnoreCase("fly")) {
        	if(!(sender.hasPermission(Bssentials.FLY_PERM))){
        		sender.sendMessage("You can't fly!");
        	} else {
        		if(args.length == 1){
				Player plr = (Player) sender;
        			if(args[0].equalsIgnoreCase("off")) {
        				plr.setFlying(false);
        			} else if(args[0].equalsIgnoreCase("on")){
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
        	} else {
        		sender.sendMessage(prefix + "No Permission!");
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
        
        /* ALIAS COMMAND */
        if (cmd.getName().equalsIgnoreCase("alias")) {
        	if(args.length == 2) {
        		if(main.getCommand(args[0]).getAliases() == null) {
        			List<String> aliases = new ArrayList<String>();
        			aliases.add(args[1]);
        			main.getCommand(args[0]).setAliases(aliases);
        		} else {
        			List<String> aliases = main.getCommand(args[0]).getAliases();
        			aliases.add(args[1]);
        			main.getCommand(args[0]).setAliases(aliases);
        		}
        	} else {
        		sender.sendMessage(prefix + "Usage: /alias command shortcut");
        	}
        }
        
        /* SETHOME COMMAND */
        if(cmd.getName().equalsIgnoreCase("sethome")) {
        	main.createHome(player);
        }
        
        /* DELHOME COMMAND */
        if(cmd.getName().equalsIgnoreCase("delhome")) {
        	main.delHome(player);
        }
        
        /* HOME COMMAND */
        if(cmd.getName().equalsIgnoreCase("home")) {
        	if (main.getHomeConfig().getConfigurationSection("homes." + player.getName()) == null) {
                sender.sendMessage(ChatColor.RED + "You have to set your home first /sethome");
            } else {
                if (args.length == 0) {
                    World w = Bukkit.getServer().getWorld(main.getHomeConfig().getString("homes." + player.getName() + ".world"));
                    double x = main.getHomeConfig().getDouble("homes." + player.getName() + ".x");
                    double y = main.getHomeConfig().getDouble("homes." + player.getName() + ".y");
                    double z = main.getHomeConfig().getDouble("homes." + player.getName() + ".z");
                    player.teleport(new Location(w, x, y, z));
                    sender.sendMessage(ChatColor.GREEN + "Welcome home " + player.getName() + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid args");
                }
            }
		}
        
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
