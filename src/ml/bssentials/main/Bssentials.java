package ml.bssentials.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

// Bukkit
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

// Listeners
import io.github.isaiah.listeners.Plugins;
import io.github.isaiah.listeners.SpawnJoin;
import io.github.isaiah.listeners.onJoinNick;

// KodeAPI
import io.github.ramidzkh.KodeAPI.api.YamlConf;
import io.github.ramidzkh.utils.PlayerCheck;

// Bssentials classes
import ml.bssentials.addons.GoogleChat;
import ml.bssentials.api.BssUtils;
import ml.bssentials.api.ChatAPI;
import ml.bssentials.commands.Broadcast;
import ml.bssentials.commands.Ping;
import ml.bssentials.commands.Pm;
import ml.bssentials.commands.ViewNick;
import ml.bssentials.commands.spawnmob;
import ml.bssentials.ranks.ChatFormat;
import ml.bssentials.updater.Updater;

/**
    <b>Bssentials</b><br>
    <br>
    Created by: Isaiah Patton & ramidzkh<br>
    About: Better Essentials for 1.10
    
    @author Isaiah Patton
    @author ramidzkh
    @author FunWithJava
    
    @version 2.x
    
    @see {@link JavaPlugin}
    @see {@link PluginDescriptionFile}
*/

public class Bssentials extends JavaPlugin implements Listener {
	
	public static String version = "2.4-dev";
	public Logger logger = getLogger();
	
	public static final Permission GAMEMODE_PERM = new Permission ("bssentials.command.gm");
	public static final Permission STAFFLIST_PERM = new Permission ("bssentials.command.staff");
	public static final Permission INVSEE_PERM = new Permission ("bssentials.command.invsee");
	public static final Permission SETWARP_PERM = new Permission ("bssentials.command.setwarp");
	public static final Permission SETWARP_OR_PERM = new Permission ("bssentials.command.setwarp.or");
	public static final Permission SETRULES_PERM = new Permission ("bssentials.command.setrules");
	public static final Permission SPAWNMOB_PERM = new Permission ("bssentials.command.spawnmob");
	public static final Permission HEAL_PERM = new Permission ("bssentials.command.heal");
	public static final Permission HEAL_OUTHER_PERM = new Permission ("bssentials.command.heal.outher");
	public static final Permission FEED_PERM = new Permission ("bssentials.command.feed");
	public static final Permission FEED_OUTHER_PERM = new Permission ("bssentials.command.feed.outher");
	public static final Permission FLY_PERM = new Permission ("bssentials.command.fly");
	public static final Permission WELCOME_PERM = new Permission ("bssentials.command.welcome");
	public static final Permission WARP_PERM = new Permission ("bssentials.command.warp");
	public static final Permission WARP_OTHERS_PERM = new Permission ("bssentials.command.warp.others");
	public static final Permission GOOGLE_PERM = new Permission("bssentials.command.google");
 	public static final Permission WIKI_PERM = new Permission("bssentials.command.mcwiki");
 	public static final Permission YOUTUBE_PERM = new Permission("bssentials.command.mcwiki");
  	public static final Permission BUKKIT_PERM = new Permission("bssentials.command.bukkitdev");
        public static final Permission PLUGINS_PERM = new Permission("bssentials.command.plugins");
        public static final Permission PM_PERM = new Permission("bssentials.command.pm");
	public static final Permission GOD_PERM = new Permission("bssentials.command.god");
        public static final Permission PLUGIN_INFO_PERM = new Permission("bssentials.command.bssentials");
        public static final Permission SETSPAWN_PERM = new Permission("bssentials.command.spawn");
        public static final Permission BROADCAST_PERM = new Permission("bssentials.command.broadcast");    
    
	public static final String prefix = ChatColor.GREEN + "[Bssentials]" + ChatColor.GOLD + " ";
        public static final String PREFIX = prefix;
    
    public FileConfiguration config = new YamlConfiguration();
    public FileConfiguration warps = new YamlConfiguration();
    public FileConfiguration homes = new YamlConfiguration();
    
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
		Updater updater = new Updater(this);
		updater.checkForUpdate();
		
		saveDefaultConfig();
		registerPermissions(pm);
        createFiles();
        saveDefaultConfig();
        
        getCommand("spawnmob").setExecutor(new spawnmob());
        getCommand("viewnick").setExecutor(new ViewNick());
        getCommand("ping").setExecutor(new Ping());
        getCommand("broadcast").setExecutor(new Broadcast());
        getCommand("pm").setExecutor(new Pm());
        
        
        registerGoogleChat();
        
        pm.registerEvents(new ChatFormat(this), this);
		pm.registerEvents(new Plugins(), this);
		pm.registerEvents(new onJoinNick(this), this);
		pm.registerEvents(new SpawnJoin(this), this);
		pm.registerEvents(this, this);
	}

    private File configf, warpsf, homesf;

    public FileConfiguration getWarpConfig() {
        return this.warps;
    }
    
    public FileConfiguration getHomeConfig() {
    	return this.homes;
    }
    
    public void saveWarpConfig() {
    	YamlConf.saveConf(warps, warpsf);
    }
    
    public void saveHomeConfig() {
    	YamlConf.saveConf(homes, homesf);
    }

    private void createFiles() {

        configf = new File(getDataFolder(), "config.yml");
        warpsf = new File(getDataFolder(), "warps.yml");
        homesf = new File(getDataFolder(), "homes.yml");

        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        if (!warpsf.exists()) {
            warpsf.getParentFile().mkdirs();
            saveResource("warps.yml", false);
         }
        if (!homesf.exists()) {
            homesf.getParentFile().mkdirs();
            saveResource("homes.yml", false);
         }

        config = new YamlConfiguration();
        warps = new YamlConfiguration();
        homes = new YamlConfiguration();
        try {
            config.load(configf);
            warps.load(warpsf);
            homes.load(homesf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    public void FileSave() {
    	//save("playerdata.yml");
    	saveConfig();
    }
    
	public void onDisable() {}
    
	private void registerGoogleChat() {
		getCommand("BukkitDev").setExecutor(new GoogleChat());
        getCommand("youtube").setExecutor(new GoogleChat());
        getCommand("google").setExecutor(new GoogleChat());
        getCommand("mcwiki").setExecutor(new GoogleChat());
	}
	
    private void registerPermissions(PluginManager pm) {
        pm.addPermission(GAMEMODE_PERM);
		pm.addPermission(STAFFLIST_PERM);
		pm.addPermission(INVSEE_PERM);
        pm.addPermission(SETWARP_PERM);
        pm.addPermission(SETWARP_OR_PERM);
        pm.addPermission(SPAWNMOB_PERM);
        pm.addPermission(HEAL_PERM);
        pm.addPermission(HEAL_OUTHER_PERM);
        pm.addPermission(FEED_PERM);
        pm.addPermission(FEED_OUTHER_PERM);
        pm.addPermission(FLY_PERM);
        pm.addPermission(WELCOME_PERM);
        pm.addPermission(GOOGLE_PERM);
        pm.addPermission(YOUTUBE_PERM);
        pm.addPermission(BUKKIT_PERM);
        pm.addPermission(PLUGINS_PERM);
        pm.addPermission(PM_PERM);
        pm.addPermission(GOD_PERM);
        pm.addPermission(PLUGIN_INFO_PERM);
        pm.addPermission(SETSPAWN_PERM);
        pm.addPermission(BROADCAST_PERM);
        pm.addPermission(WARP_PERM);
        pm.addPermission(WARP_OTHERS_PERM);
    }
    
    @SuppressWarnings("unused")
	private void registerPerms(PluginManager pm, Permission... perms) {
    	for (Permission perm : perms) {
            pm.addPermission(perm);
        }
    }
    
    @Deprecated
    public void nickName(Player player, String name) { ChatAPI.nickName(player, name); }
    
    public boolean ifCommand(Command cmd, String cmd2) {
    	if (cmd.getName().equalsIgnoreCase(cmd2)) {
    		
    	}
		return true;
	}
    
    @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    	if(!(sender instanceof Player) && !cmd.getName().equalsIgnoreCase("bssentials")){
    		sender.sendMessage("You are not a player");
    		return false;
    	} else if (!(sender instanceof Player) && cmd.getName().equalsIgnoreCase("bssentials")){
    		sender.sendMessage("Bssentials!");
    	}
    	
        String authors = "Isaiah Patton, & ramidzkh";
        String NoPerm = prefix + "You don't have permission: bssentials.command." + cmd.getName().toLowerCase();
        
        Player player = (Player) sender;
        Player p = player;
        
        if (cmd.getName().equalsIgnoreCase("bssentials")) {
            if(!PlayerCheck.hasPerm(player, PLUGIN_INFO_PERM)){
                player.sendMessage(NoPerm);
                return false;
            }
            String pre = PREFIX;
		
            player.sendMessage(pre + "Name: " + ChatColor.GREEN + "Bssentials");
            player.sendMessage(pre + "Version: " + ChatColor.GREEN + version);
            player.sendMessage(pre + "Authors: " + ChatColor.GREEN + authors);
            player.sendMessage(pre + "Description: " + ChatColor.GREEN + "Essentials for 1.10");
            //player.sendMessage(pre + "Addons: " + ChatColor.GREEN + AddonManager.getAllAddons());
        }
     
        if (cmd.getName().equalsIgnoreCase("nick")) {
            nickName(player, StringUtils.join(args, " "));
        }
        
        if (cmd.getName().equalsIgnoreCase("info")) {
            Bukkit.dispatchCommand(sender, "warp info");
        }

        if (cmd.getName().equalsIgnoreCase("removelag")) {
        	if (args.length == 0) {
        		Bukkit.dispatchCommand(sender, "lagg gc");
        		Bukkit.dispatchCommand(sender, "lagg clear");
        		Bukkit.dispatchCommand(sender, "lagg unloadchunks");
        		sender.sendMessage(prefix + "Removed Lagg!");
        	} else if (args.length !=0 && args[0].equalsIgnoreCase("unloadchunks")) {
                if (BssUtils.hasPermForCommand(p, "removelag.unloadchunks")) {
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
                        } else if (p != null) {
                            w = p.getWorld();
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
                    BssUtils.noPermMsg(p);
                }
                return true;
            }
        }

        if (cmd.getName().equalsIgnoreCase("rain")) {
            Bukkit.dispatchCommand(sender, "weather rain");
        }

        if (cmd.getName().equalsIgnoreCase("day")) {
            Bukkit.dispatchCommand(sender, "time set day");
        }

        if (cmd.getName().equalsIgnoreCase("night")) {
            Bukkit.dispatchCommand(sender, "time set night");
		}

        if (cmd.getName().equalsIgnoreCase("ci")) {
            player.getInventory().clear();
            sender.sendMessage(prefix + "Inventory cleared!");
        }
    
        
        if (cmd.getName().equalsIgnoreCase("control")) {
        	if (PlayerCheck.hasPermForCommand(p, "control")){
        	 	Player target = player.getServer().getPlayer(args[0]);
        	 	String argss = StringUtils.join(args, " ").replace(args[0], "");
        	 	if(argss.contains("/")) {
        	 		getServer().dispatchCommand(sender, argss.replace("/", ""));
        	 	} else {
        	 		target.chat(argss);
        	 	}
        	}
        }
        
        if (cmd.getName().equalsIgnoreCase("rank")) {
        	if (args.length == 0) {
        		sender.sendMessage("Use /rank create <rankname> <display>");
        		sender.sendMessage("Or: /rank setplayer <player> <rank>");
        	} else if (args[0].equalsIgnoreCase("create")) {
        		getConfig().set("ranks."+args[1]+".prefix", args[2]);
        		saveConfig();
        		sender.sendMessage("Created rank: "+args[1]+" With the display of: " +args[2]);
        	} else if (args[0].equalsIgnoreCase("setplayer")) {
        		getConfig().set("playerdata." + args[1] + ".rank", args[2]);
			saveConfig();
        		sender.sendMessage("Added "+args[1]+" to the rank" + args[2]);
        	}
        }
        
        
        if (cmd.getName().equalsIgnoreCase("disnick")) {
        	if (player.getName() == player.getDisplayName()) {
        		sender.sendMessage("Your nickname and real name are the same!");
        	} else {
        		player.setDisplayName(player.getName());
        		sender.sendMessage("Reseted your name!");
        	}
        }
        
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.GRAY + " Usage /gm <0|1>");
            } else if (sender.hasPermission(GAMEMODE_PERM) || sender.isOp()) {
                if (args.length == 1 && args[0].equalsIgnoreCase("0")) {
                	player.setGameMode(GameMode.SURVIVAL);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("1")) {
                    player.setGameMode(GameMode.CREATIVE);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("survival")) {
                	player.setGameMode(GameMode.SURVIVAL);
                } else if (args.length == 1 && args[0].equalsIgnoreCase("creative")) {
                	player.setGameMode(GameMode.CREATIVE);
                }
			} else {
		        sender.sendMessage(ChatColor.GREEN + "[Bssentials] " + ChatColor.RED + "You don't have permission: bssentials.command.gm");  
            }
        }

        if (cmd.getName().equalsIgnoreCase("staff")) {
                if (sender.hasPermission(STAFFLIST_PERM)) {
			sender.sendMessage(ChatColor.GREEN + "[Bssentials] Staff:");
                	sender.sendMessage(" " + ChatColor.YELLOW + getConfig().getStringList("staff"));
		} else {
                sender.sendMessage("No Permission");
            }
        }
        
       if (cmd.getName().equalsIgnoreCase("setspawn")) {
		if (sender.hasPermission(SETSPAWN_PERM)) {
			createWarp(p, "spawn");
		} else {
                sender.sendMessage("No Permission");
            }
        }

        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (getConfig().getConfigurationSection("warps.spawn") == null) {
                sender.sendMessage(ChatColor.RED + "Spawn has not been set!");
            } else {
                if (args.length == 0) {
                    World w = Bukkit.getServer().getWorld(getConfig().getString("warps.spawn.world"));
                    double x = getWarpConfig().getDouble("warps.spawn.x");
                    double y = getWarpConfig().getDouble("warps.spawn.y");
                    double z = getWarpConfig().getDouble("warps.spawn.z");
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
        
        if (cmd.getName().equalsIgnoreCase("heal")) {
            if (args.length == 0) {
                if (player.hasPermission(HEAL_PERM)) {
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.GREEN + "You have been healed!");
                } else {
                    sender.sendMessage("No Permission");
                }
            } else {
                if (sender.hasPermission(HEAL_OUTHER_PERM)) {
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
        
        if (cmd.getName().equalsIgnoreCase("underheal")) {
            player.setHealth(1);
            player.setFoodLevel(1); 
        }
        
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
        
        if (cmd.getName().equalsIgnoreCase("feed")) {
            if (args.length == 0) {
                if (sender.hasPermission(FEED_PERM)) {
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.GREEN + "You have been fed!");
                } else {
                    sender.sendMessage("No Permission");
                }
            } else {
                if (sender.hasPermission(FEED_OUTHER_PERM)) {
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
        
        if(cmd.getName().equalsIgnoreCase("fly")) {
        	if(!(sender.hasPermission(FLY_PERM))){
        		sender.sendMessage("You can't fly!");
        		return false;
        	}
        	
        	if(args.length == 0) {
        		sender.sendMessage("Usage is /fly [on/off] or /fly [on/off] [player]");
        	}
        	if(args.length == 1){
        		if(args[0].equalsIgnoreCase("off")) {
        			player.setFlying(false);
        		} else if(args[0].equalsIgnoreCase("on")){
        			player.setFlying(true);
        		}
        	} else {
				Player fly = getServer().getPlayer(args[1]);
        		if(args[0].equalsIgnoreCase("off")) {
        			fly.setFlying(false);
        		} else if(args[0].equalsIgnoreCase("on")){
        			fly.setFlying(true);
        		}
        	}
        }
        
        /* REPAIR COMMAND */
        if (cmd.getName().equalsIgnoreCase("repair")) {
        	if (PlayerCheck.hasPermForCommand(p, "repair")) {
        		player.getItemInHand().setDurability((short) 0);
        		sender.sendMessage("Repaired!");
        	} else {
        		sender.sendMessage("No Permission!");
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
                } else {
                    sender.sendMessage("No Permission");
                }
			}
	    }
        
        
        if (cmd.getName().equalsIgnoreCase("setwarp")) {
            if (args.length == 1) {
                String warpname = args[0];
                if (getWarpConfig().getConfigurationSection("warps." + warpname) != null) {
                    if (sender.hasPermission(SETWARP_OR_PERM)) {
                        createWarp(player, args[0]);
                    } else {
                        sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.RED + " You can't overwrite that");
                    }
                } else {
                    if (sender.hasPermission(SETWARP_PERM) || sender.isOp()) {
                    	createWarp(player, args[0]);
                    }
                }
            } else  {
                sender.sendMessage(ChatColor.RED + "Invalid args");
            }
        }
        
        
        if (cmd.getName().equalsIgnoreCase("alias")) {
        	if(args.length == 2) {
        		if(getCommand(args[0]).getAliases() == null) {
        			List<String> aliases = new ArrayList<String>();
        			aliases.add(args[1]);
        			getCommand(args[0]).setAliases(aliases);
        		} else {
        			List<String> aliases = getCommand(args[0]).getAliases();
        			aliases.add(args[1]);
        			getCommand(args[0]).setAliases(aliases);
        		}
        	} else {
        		sender.sendMessage("Usage: /alias command shortcut");
        	}
        }
        
        
        if(cmd.getName().equalsIgnoreCase("sethome")) {
        	createHome(p);
        }
        
        
        if(cmd.getName().equalsIgnoreCase("delhome")) {
        	delHome(p);
        }
        
        
        if(cmd.getName().equalsIgnoreCase("home")) {
        	if (getHomeConfig().getConfigurationSection("homes." + p.getName()) == null) {
                sender.sendMessage(ChatColor.RED + "You have to set your home first /sethome");
            } else {
                if (args.length == 0) {
                    World w = Bukkit.getServer().getWorld(getHomeConfig().getString("homes." + p.getName() + ".world"));
                    double x = getHomeConfig().getDouble("homes." + p.getName() + ".x");
                    double y = getHomeConfig().getDouble("homes." + p.getName() + ".y");
                    double z = getHomeConfig().getDouble("homes." + p.getName() + ".z");
                    player.teleport(new Location(w, x, y, z));
                    sender.sendMessage(ChatColor.GREEN + "Welcome home " + p.getName() + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid args");
                }
            }
		}
        
        
        if (cmd.getName().equalsIgnoreCase("warp")) {
        	if(p.hasPermission(WARP_PERM)) {
	            if (getWarpConfig().getConfigurationSection("warps") == null) {
	                sender.sendMessage(ChatColor.RED + "No warps set!");
	            } else {
	                if (args.length == 1) {
	                	if(getWarpConfig().getConfigurationSection("warps." + args[0]) != null) {
	                		World w = Bukkit.getServer().getWorld(getWarpConfig().getString("warps." + args[0] + ".world"));
		                    double x = getWarpConfig().getDouble("warps." + args[0] + ".x");
		                    double y = getWarpConfig().getDouble("warps." + args[0] + ".y");
		                    double z = getWarpConfig().getDouble("warps." + args[0] + ".z");
		                    
		                    teleport(player, new Location(w,x,y,z));
		                    sender.sendMessage(ChatColor.GREEN + "Warping to " + args[0]);
	                	} else {
	                		sender.sendMessage(ChatColor.RED + "No warp by that name exists.");
	                	}
	                } else if (args.length == 2 ) {
	                	if(p.hasPermission(WARP_OTHERS_PERM)) {
	                		Player targetPlayer = player.getServer().getPlayer(args[1]);
	                		if(targetPlayer != null) {
	    	                	if(getWarpConfig().getConfigurationSection("warps." + args[0]) != null) {
	    	                		World w = Bukkit.getServer().getWorld(getWarpConfig().getString("warps." + args[0] + ".world"));
	    	                		double x = getWarpConfig().getDouble("warps." + args[0] + ".x");
	    		                	double y = getWarpConfig().getDouble("warps." + args[0] + ".y");
	    		                	double z = getWarpConfig().getDouble("warps." + args[0] + ".z");
	    		                	teleport(targetPlayer, new Location(w, x, y, z));
	    		                	sender.sendMessage(ChatColor.GREEN + "Warping " + args[1] + " to " + args[0]);
	    		                	targetPlayer.sendMessage(ChatColor.GREEN + p.getName() + " warped you to " + args[0]);
	    	                	} else {
	    	                		sender.sendMessage(ChatColor.RED + "No warp by that name exists.");
	    	                	}	                			
	                		}
	                	} else {
	                		sender.sendMessage(ChatColor.RED + "You do not have permission to warp other players.");
	                	}
	                } else if (args.length == 0 ) {
	                	Set<String> keys = getWarpConfig().getConfigurationSection("warps").getKeys(false);
	                	sender.sendMessage(ChatColor.BLUE + "List of warps:");
	                	for (String s:keys) {
	                		sender.sendMessage(ChatColor.BLUE + "  " + s);
	                	}
	            	} else {
	                    sender.sendMessage(ChatColor.RED + "Invalid args");
	                }
	            }
        	} else {
        		sender.sendMessage(ChatColor.RED + "You don't have permission to warp.");
        	}
        }
        
        
        if (cmd.getName().equalsIgnoreCase("delwarp")) {
        	if (p.hasPermission(SETWARP_OR_PERM)) {
        		try {
	        		if(getWarpConfig().getConfigurationSection("warps." + args[0]) != null) {
	        			getWarpConfig().set("warps." + args[0], null);
	        			saveWarpConfig();
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
    
    public void createWarp(Player p, String warpname) {
        getWarpConfig().set("warps." + warpname + ".world", p.getLocation().getWorld().getName());
        getWarpConfig().set("warps." + warpname + ".x", p.getLocation().getX());
        getWarpConfig().set("warps." + warpname + ".y", p.getLocation().getY());
        getWarpConfig().set("warps." + warpname + ".z", p.getLocation().getZ());
        saveWarpConfig();
        
        p.sendMessage(ChatColor.GREEN + warpname + " warp set!");
    }
    public void createHome(Player p) {
    	String homename = p.getName();
        getHomeConfig().set("homes." + homename + ".world", p.getLocation().getWorld().getName());
        getHomeConfig().set("homes." + homename + ".x", p.getLocation().getX());
        getHomeConfig().set("homes." + homename + ".y", p.getLocation().getY());
        getHomeConfig().set("homes." + homename + ".z", p.getLocation().getZ());
        saveHomeConfig();
        
        p.sendMessage(ChatColor.GREEN + "Your home has been set!");
    }
    public void delHome(Player p) {
    	String homename = p.getName();
    	getHomeConfig().set("homes." + homename, null);
    	saveWarpConfig();
    }
    
    public void teleport(Player player, Location l) {
    	// check for mount entity in mount metadata
        List<MetadataValue> mount = player.getMetadata("mount");
        if(!mount.isEmpty()) {
        	try {
	        	Entity m = (Entity)mount.get(mount.size()-1).value();        	
	        	m.eject();
	        	player.teleport(l);
	        	m.teleport(l.add(1,0,0));
        	} catch(Exception e) {
        		player.teleport(l);
        	}
        } else {               
        	player.teleport(l);
        }
    }
}
