/**
 * Lincence under GPLv3!
 * 
 * http://github.com/bssentials/
 * */
package ml.bssentials.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
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

// Bssentials classes
import ml.bssentials.addons.GoogleChat;
import ml.bssentials.api.ChatAPI;
import ml.bssentials.commands.Broadcast;
import ml.bssentials.commands.Commands;
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
    
    @version 2.5-dev
    
    @see {@link JavaPlugin}
    @see {@link PluginDescriptionFile}
*/

public class Bssentials extends JavaPlugin implements Listener {
	
    public static String version = "2.5-dev";
	public Logger logger = getLogger();
	
	public static final Permission GAMEMODE_PERM = new Permission ("bssentials.command.gm");
	public static final Permission STAFFLIST_PERM = new Permission ("bssentials.command.staff");
	public static final Permission STAFFADD_PERM = new Permission ("bssentials.command.staff.add");
	public static final Permission STAFFREMOVE_PERM = new Permission ("bssentials.command.staff.remove");
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
    public FileConfiguration ranks = new YamlConfiguration();
    
    /**
     * On the plugin enable.
     * */
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
		new Updater(this);
		Updater.checkForUpdate();
		
		saveDefaultConfig();
		registerPermissions(pm);
        createFiles();
        saveDefaultConfig();
        
        getCommand("spawnmob").setExecutor(new spawnmob());
        getCommand("viewnick").setExecutor(new ViewNick());
        getCommand("ping").setExecutor(new Ping());
        getCommand("broadcast").setExecutor(new Broadcast());
        getCommand("pm").setExecutor(new Pm());
        
        regCommands();
        
        pm.registerEvents(new ChatFormat(this), this);
		pm.registerEvents(new Plugins(), this);
		pm.registerEvents(new onJoinNick(this), this);
		pm.registerEvents(new SpawnJoin(this), this);
		pm.registerEvents(this, this);
	}

    /**
     * Registers commands!
     * */
    public void regCommands(){
    	getCommand("info").setExecutor(new Commands(this));
    	getCommand("bssentials").setExecutor(new Commands(this));
    	getCommand("gm").setExecutor(new Commands(this));
    	getCommand("rules").setExecutor(new Commands(this));
    	getCommand("setrules").setExecutor(new Commands(this));
    	getCommand("removelag").setExecutor(new Commands(this));
    	getCommand("ci").setExecutor(new Commands(this));
    	getCommand("day").setExecutor(new Commands(this));
    	getCommand("night").setExecutor(new Commands(this));
    	getCommand("rain").setExecutor(new Commands(this));
    	getCommand("staff").setExecutor(new Commands(this));
    	getCommand("invsee").setExecutor(new Commands(this));
    	getCommand("setspawn").setExecutor(new Commands(this));
    	getCommand("spawn").setExecutor(new Commands(this));
    	getCommand("welcome").setExecutor(new Commands(this));
    	getCommand("heal").setExecutor(new Commands(this));
    	getCommand("feed").setExecutor(new Commands(this));
    	getCommand("fly").setExecutor(new Commands(this));
    	/*GoogleChat*/getCommand("BukkitDev").setExecutor(new GoogleChat());
        /*GoogleChat*/getCommand("youtube").setExecutor(new GoogleChat());
        /*GoogleChat*/getCommand("google").setExecutor(new GoogleChat());
        /*GoogleChat*/getCommand("mcwiki").setExecutor(new GoogleChat());
    	getCommand("god").setExecutor(new Commands(this));
    	getCommand("pm").setExecutor(new Commands(this));
    	getCommand("setwarp").setExecutor(new Commands(this));
    	getCommand("warp").setExecutor(new Commands(this));
    	getCommand("nick").setExecutor(new Commands(this));
    	getCommand("underheal").setExecutor(new Commands(this));
    	getCommand("disnick").setExecutor(new Commands(this));
    	getCommand("alias").setExecutor(new Commands(this));
    	getCommand("rank").setExecutor(new Commands(this));
    	getCommand("control").setExecutor(new Commands(this));
    	getCommand("delwarp").setExecutor(new Commands(this));
    	getCommand("home").setExecutor(new Commands(this));
    	getCommand("sethome").setExecutor(new Commands(this));
    	getCommand("delhome").setExecutor(new Commands(this));
    	getCommand("bancheck").setExecutor(new Commands(this));
    }
    
    private File configf, warpsf, homesf, ranksf;

    /**
    * Gets the warps.yml
    */
    public FileConfiguration getWarpConfig() {
        return this.warps;
    }
    
    /**
    * Gets the homes.yml
    */
    public FileConfiguration getHomeConfig() {
    	return this.homes;
    }
    
    /**
    * Gets the ranks.yml
    */
    public FileConfiguration getRankConfig() {
    	return this.ranks;
    }
    
    /**
    * Saves the warps.yml
    */
    public void saveWarpConfig() {
    	YamlConf.saveConf(warps, warpsf);
    }
    
    /**
    * Saves the homes.yml
    */
    public void saveHomeConfig() {
    	YamlConf.saveConf(homes, homesf);
    }
    
    /**
    * Saves the ranks.yml
    */
    public void saveRankConfig() {
    	YamlConf.saveConf(ranks, ranksf);
    }

    private void createFiles() {

        configf = new File(getDataFolder(), "config.yml");
        warpsf = new File(getDataFolder(), "warps.yml");
        homesf = new File(getDataFolder(), "homes.yml");
        ranksf = new File(getDataFolder(), "ranks.yml");

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
        if (!ranksf.exists()) {
            homesf.getParentFile().mkdirs();
            saveResource("ranks.yml", false);
         }

        config = new YamlConfiguration();
        warps = new YamlConfiguration();
        homes = new YamlConfiguration();
        ranks = new YamlConfiguration();
        try {
            config.load(configf);
            warps.load(warpsf);
            homes.load(homesf);
            ranks.load(ranksf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    public void FileSave() {
    	saveConfig();
    }
    
	public void onDisable() {}
	
    private void registerPermissions(PluginManager pm) {
        pm.addPermission(GAMEMODE_PERM);
        pm.addPermission(STAFFLIST_PERM);
        pm.addPermission(STAFFADD_PERM);
        pm.addPermission(STAFFREMOVE_PERM);
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
    
    @Deprecated
    public void nickName(Player player, String name) { ChatAPI.nickName(player, name); }

    
    /**
     * Creates an Warp
     **/
    public void createWarp(Player p, String warpname) {
        getWarpConfig().set("warps." + warpname + ".world", p.getLocation().getWorld().getName());
        getWarpConfig().set("warps." + warpname + ".x", p.getLocation().getX());
        getWarpConfig().set("warps." + warpname + ".y", p.getLocation().getY());
        getWarpConfig().set("warps." + warpname + ".z", p.getLocation().getZ());
        saveWarpConfig();
        
        p.sendMessage(ChatColor.GREEN + warpname + " warp set!");
    }
    
    /**
     * Creates the player's home
     **/
    public void createHome(Player p) {
    	String homename = p.getName();
        getHomeConfig().set("homes." + homename + ".world", p.getLocation().getWorld().getName());
        getHomeConfig().set("homes." + homename + ".x", p.getLocation().getX());
        getHomeConfig().set("homes." + homename + ".y", p.getLocation().getY());
        getHomeConfig().set("homes." + homename + ".z", p.getLocation().getZ());
        saveHomeConfig();
        
        p.sendMessage(ChatColor.GREEN + "Your home has been set!");
    }
    
    /**
     * Deleates the player's home
     **/
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
