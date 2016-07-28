package io.github.isaiah.bssentials;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.isaiah.listeners.Plugins;
import io.github.isaiah.updater.Updater;
import io.github.ramidzkh.utils.PlayerCheck;

import ml.bssentials.commands.Ping;
import ml.bssentials.commands.ViewNick;
import ml.bssentials.commands.spawnmob;

/**
    <b>Bssentials</b><br>
    <br>
    Created by: Isaiah Patton, PolarCraft, & ramidzkh<br>
    About: Better Essentials for 1.10
    
    @author Isaiah Patton
    @author PolarCraft
    @author ramidzkh
    
    @version 1.7
    
    @see {@link JavaPlugin}
    @see {@link PluginDescriptionFile}
*/

public class Bssentials extends JavaPlugin implements Listener {
	
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
	
	public static final Permission GOOGLE_PERM = new Permission("bssentials.command.google");
 	public static final Permission WIKI_PERM = new Permission("bssentials.command.mcwiki");
 	public static final Permission YOUTUBE_PERM = new Permission("bssentials.command.mcwiki");
  	public static final Permission BUKKIT_PERM = new Permission("bssentials.command.bukkitdev");
    public static final Permission PLUGINS_PERM = new Permission("bssentials.command.plugins");
    public static final Permission PM_PERM = new Permission("bssentials.command.pm");
	public static final Permission GOD_PERM = new Permission("bssentials.command.god");
    public static final Permission PLUGIN_INFO_PERM = new Permission("bssentials.command.bssentials");
    public static final Permission SETSPAWN_PERM = new Permission("bssentials.command.spawn");
    
	private static final String prefix = ChatColor.GREEN + "[Bssentials]" + ChatColor.YELLOW + " ";
    public static final String PREFIX = prefix;
    private File warpf, playerdataf;
    private FileConfiguration warp, playerdata;
    
    public static String version = "2.1";
    
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
        
        
		pm.registerEvents(new Plugins(), this);
		pm.registerEvents(this, this);
	}

    public FileConfiguration getWarpConfig() {
        return this.warp;
    }
    
    public FileConfiguration getPlayerDataConfig() {
        return this.playerdata;
    }

    private void createFiles() {}

        //configf = new File(getDataFolder(), "config.yml");
        /*warpf = new File(getDataFolder(), "warps.yml");
        playerdataf = new File(getDataFolder(), "playerdata.yml");

        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        if (!warpf.exists()) {
            warpf.getParentFile().mkdirs();
            saveResource("warps.yml", false);
        }
        if (!playerdataf.exists()) {
            warpf.getParentFile().mkdirs();
            saveResource("playerdata.yml", false);
        }

        config = new YamlConfiguration();
        warp = new YamlConfiguration();
        playerdata = new YamlConfiguration();
        try {
            config.load(configf);
            warp.load(warpf);
            playerdata.load(playerdataf);
            playerdata.save("playerdata.yml");
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }*/
    
    public void FileSave() {
    	//save("playerdata.yml");
    	saveConfig();
    }
    
	public void onDisable() {}
	
	/**
	 * Registers <code>commands</code>
	 * 
	 * @author <b>ramidzkh</b>
	 * 
	 * @see CommandExecutor
	 */
    
    public int getPing(Player p) {
        //CraftPlayer cp = (CraftPlayer) p; 
        //EntityPlayer ep = cp.getHandle();
        //return ep.ping; 
        //I think this will make Bssentials only work on one version, ex: import org.bukkit.craftbukkit.1_10_2_R1.CraftPlayer;
        return 0;
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
    }
    
    public void nickName(Player player, String name) {
        getConfig().set("playerdata." + player.getName() + ".nick", name);
        player.setDisplayName(getConfig().getString("playerdata." + player.getName() + ".nick"));
        saveConfig();
    }
    
    public void savePlayerDataConfig() {
            saveAllConfigs();
    }
    
    public void saveWarpConfig() {
            //warpf.save("warps.yml");
            saveAllConfigs();
    }
    
    public void saveAllConfigs() {
		saveAConfig(playerdataf);
		saveAConfig(warpf);
	}
    
    public void saveAConfig(File file) {
    	 try {
             //warpf.save();
             //playerdataf.save;
             YamlConfiguration fileT = YamlConfiguration.loadConfiguration(file);
             fileT.save(file);
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    	if(!(sender instanceof Player)){
    		sender.sendMessage("You are not a player");
    		return false;
    	}
    	
        String authors = "Isaiah Patton, PolarCraft, & ramidzkh";
        @SuppressWarnings("unused")
		String website = "bssentials.github.io";
        
        Player player = (Player) sender;
        Player p = player;

        if (cmd.getName().equalsIgnoreCase("bssentials")) {
            if(!PlayerCheck.hasPerm(player, PLUGIN_INFO_PERM)){
                player.sendMessage(PREFIX + "You don't have permission: bssentials.command.bssentials");
                return false;
            }
            String pre = PREFIX;
		
            player.sendMessage(pre + "Name: " + ChatColor.GREEN + "Bssentials");
            player.sendMessage(pre + "Version: " + ChatColor.GREEN + version);
            player.sendMessage(pre + "Authors: " + ChatColor.GREEN + authors);
            player.sendMessage(pre + "Description: " + ChatColor.GREEN + "Essentials for 1.10");
        }
     
        if (cmd.getName().equalsIgnoreCase("nick")) {
            nickName(player, StringUtils.join(args, " "));
        }
        
        if (cmd.getName().equalsIgnoreCase("pm")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.GRAY + " Usage /pm <player> <message>");
                return false;
            }
            if (sender.hasPermission(PM_PERM) || sender.isOp()) {
                @SuppressWarnings("deprecation")
				Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    String message = "";
                    for(int i = 1; i != args.length; i++)
 
                        message += args[i] + " ";

                        target.sendMessage(sender.getName() + " -> " + target.getName() + ": " + ChatColor.translateAlternateColorCodes('&', message));
 
                        sender.sendMessage("me" + " -> " + target.getName() + " " + message);
 
                } else if(target == null) {
                    sender.sendMessage("That player is not currently online!");
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + "[Bssentials] " + ChatColor.RED + "You don't have permission: bssentials.command.pm");  
            }
        }
        
        if (cmd.getName().equalsIgnoreCase("info")) {
            Bukkit.dispatchCommand(sender, "warp info");
        }

        if (cmd.getName().equalsIgnoreCase("removelag")) {
            Bukkit.dispatchCommand(sender, "lagg gc");
			Bukkit.dispatchCommand(sender, "lagg clear");
			Bukkit.dispatchCommand(sender, "lagg unloadchunks");
            sender.sendMessage(prefix + "Removed Lagg!");
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
        }
        
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.GRAY + " Usage /gm <0|1>");
            } else if (sender.hasPermission(GAMEMODE_PERM) || sender.isOp()) {
                if (args.length == 1 && args[0].equalsIgnoreCase("0")) {
                    Bukkit.dispatchCommand(sender, "gamemode 0");
                } else if (args.length == 1 && args[0].equalsIgnoreCase("1")) {
                    Bukkit.dispatchCommand(sender, "gamemode 1");
                } else if (args.length == 1 && args[0].equalsIgnoreCase("survival")) {
                    Bukkit.dispatchCommand(sender, "gamemode 0");
                } else if (args.length == 1 && args[0].equalsIgnoreCase("creative")) {
                    Bukkit.dispatchCommand(sender, "gamemode 1");
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
                    double x = getConfig().getDouble("warps.spawn.x");
                    double y = getConfig().getDouble("warps.spawn.y");
                    double z = getConfig().getDouble("warps.spawn.z");
                    player.teleport(new Location(w, x, y, z));
                    sender.sendMessage(ChatColor.GREEN + "Warping to spawn");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid args");
                }
            }
        }
        
        if (cmd.getName().equalsIgnoreCase("welcome")) {
            if (args.length == 0) {
                sender.sendMessage("Wrong args!");
            } else if (args.length == 1) {
                if (sender.hasPermission("bssentials.command.welcome")) {
                    @SuppressWarnings("deprecation")
					Player theNewPlayer = player.getServer().getPlayer(args[0]);
                    theNewPlayer.sendMessage(ChatColor.YELLOW + sender.getName() + " " + ChatColor.AQUA + "Says Welcome to The Server!");
                    sender.sendMessage("You welcomed " + args[0] + " to the server");
                } else {
                    sender.sendMessage("No Permission");
                }
			}
        }
        
        if (cmd.getName().equalsIgnoreCase("broadcast")) {
            if (args.length == 0) {
                sender.sendMessage("/broadcast <message>");
            } else {
                Bukkit.broadcastMessage("[BroadCast]" + " " + StringUtils.join(args, " "));
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
                    @SuppressWarnings("deprecation")
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
                    @SuppressWarnings("deprecation")
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
        		@SuppressWarnings("deprecation")
				Player fly = getServer().getPlayer(args[1]);
        		if(args[0].equalsIgnoreCase("off")) {
        			fly.setFlying(false);
        		} else if(args[0].equalsIgnoreCase("on")){
        			fly.setFlying(true);
        		}
        	}
        }
        //Strings needed for GoogleChat
        String TooManyArgs = "Too many args! Max 15!";
        String NoArgs = "No args!";
        String Perm = "No Permisson: bssentials.command.<command>";
        
        if (cmd.getName().equalsIgnoreCase("BukkitDev")) {
            if (sender.hasPermission(BUKKIT_PERM)) {
                if (args.length > 0) {
                    sender.sendMessage("http://dev.bukkit.org/bukkit-plugins/?search=" + StringUtils.join(args, "+"));
                } else if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + prefix + ChatColor.GOLD + " " + NoArgs);
                } else if (args.length < 15) {
                    sender.sendMessage(ChatColor.RED + TooManyArgs);
                }
            } else {
                sender.sendMessage(ChatColor.RED + Perm);
            }
        }
        if (cmd.getName().equalsIgnoreCase("YouTube")) {
            if (sender.hasPermission(YOUTUBE_PERM)) {
                if (args.length > 0) {
                    sender.sendMessage("http://youtube.com/results?search_query=" + StringUtils.join(args, "+"));
                } else if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + prefix + ChatColor.GOLD + " " + NoArgs);
                } else if (args.length < 15) {
                    sender.sendMessage(ChatColor.RED + TooManyArgs);
                }
            } else {
                sender.sendMessage(ChatColor.RED + Perm);
            }
        }
        if (cmd.getName().equalsIgnoreCase("Google")) {
            if (sender.hasPermission(GOOGLE_PERM)) {
                if (args.length > 0) {
                    sender.sendMessage("http://google.com/?gws_rd=ssl#q=" + StringUtils.join(args, "+"));
                } else if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + prefix + ChatColor.GOLD + " " + NoArgs);
                } else if (args.length < 15) {
                    sender.sendMessage(ChatColor.RED + TooManyArgs);
                }
            } else {
                sender.sendMessage(ChatColor.RED + Perm);
            }
        }
        if (cmd.getName().equalsIgnoreCase("MCWiki")) {
            if (sender.hasPermission(WIKI_PERM)) {
                if (args.length > 0) {
                    sender.sendMessage("http://minecraftwiki.net/wiki/" + StringUtils.join(args, (String)"_"));
                } else if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + prefix + ChatColor.GOLD + " " + NoArgs);
                } else if (args.length < 15) {
                    sender.sendMessage(ChatColor.RED + TooManyArgs);
                }
            } else {
                sender.sendMessage(ChatColor.RED + Perm);
            }
        }      
        if (cmd.getName().equalsIgnoreCase("invsee")) {
            if (args.length == 0) {
                sender.sendMessage("Wrong args!");
            } else if (args.length == 1) {
                if (sender.hasPermission("bssentials.command.invsee")) {
                    @SuppressWarnings("deprecation")
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
                if (getConfig().getConfigurationSection("warps." + warpname) != null) {
                    if (sender.hasPermission(SETWARP_OR_PERM)) {
                        createWarp(player, args[0]);
                    } else {
                        sender.sendMessage(ChatColor.GREEN + "[Bssentials]" + ChatColor.RED + " You can't over right that");
                    }
                } else {
                    if (sender.hasPermission(SETWARP_PERM) || sender.isOp()) {
                    	createWarp(player, args[0]);

                        sender.sendMessage(ChatColor.GREEN + args[0] + " warp set!");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid args");
            }
        }
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (getConfig().getConfigurationSection("warps") == null) {
                sender.sendMessage(ChatColor.RED + "Any warps have not yet been set!");
            } else {
                if (args.length == 1) {
                    World w = Bukkit.getServer().getWorld(getConfig().getString("warps." + args[0] + ".world"));
                    double x = getConfig().getDouble("warps." + args[0] + ".x");
                    double y = getConfig().getDouble("warps." + args[0] + ".y");
                    double z = getConfig().getDouble("warps." + args[0] + ".z");
                    player.teleport(new Location(w, x, y, z));
                    sender.sendMessage(ChatColor.GREEN + "Warping to " + args[0]);
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid args");
                }
            }
        }
        return true;
    }
    public void createWarp(Player p, String warpname) {
        getConfig().set("warps." + warpname + ".world", p.getLocation().getWorld().getName());
        getConfig().set("warps." + warpname + ".x", p.getLocation().getX());
        getConfig().set("warps." + warpname + ".y", p.getLocation().getY());
        getConfig().set("warps." + warpname + ".z", p.getLocation().getZ());
        saveConfig();
        
        p.sendMessage(ChatColor.GREEN + warpname + " warp set!");
    }
}
