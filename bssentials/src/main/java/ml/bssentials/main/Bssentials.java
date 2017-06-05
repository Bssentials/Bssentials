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
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ml.bssentials.commands.Broadcast;
import ml.bssentials.commands.Commands;
import ml.bssentials.commands.Heal;
import ml.bssentials.commands.Home;
import ml.bssentials.commands.Nuke;
import ml.bssentials.commands.Ping;
import ml.bssentials.commands.Pm;
import ml.bssentials.commands.RemoveLag;
import ml.bssentials.commands.Staff;
import ml.bssentials.commands.UUIDCommand;
import ml.bssentials.commands.ViewNick;
import ml.bssentials.commands.Warp;
import ml.bssentials.commands.spawnmob;
import ml.bssentials.listeners.ChatLis;
import ml.bssentials.listeners.CommandLis;
import ml.bssentials.listeners.PlayerJoinLis;
import ml.bssentials.updater.Skyupdater;

/**
 * Bssentials
 *
 * @author Isaiah Patton
 * @author ramidzkh
 */
public class Bssentials extends JavaPlugin implements Listener {
    public String version = this.getDescription().getVersion();
    public Logger logger = getLogger();

    public static final String prefix = ChatColor.GREEN + "[Bssentials]" + ChatColor.GOLD + " ";

    public File configf, warpsf, homesf, ranksf;
    public FileConfiguration config = new YamlConfiguration();
    public FileConfiguration warps = new YamlConfiguration();
    public FileConfiguration homes = new YamlConfiguration();
    public FileConfiguration ranks = new YamlConfiguration();
    public static final Permission ALL_PERM = new Permission("bssentials.all");

    /**
     * On the plugin enable.
     * */
    @Override public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        saveDefaultConfig();
        createFiles();
        saveDefaultConfig();

        try {
            getLogger().info("Checking for update...");
            new Skyupdater(this, 99810, this.getFile(), true, true);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        getCommand("spawnmob").setExecutor(new spawnmob());
        getCommand("viewnick").setExecutor(new ViewNick());
        getCommand("ping").setExecutor(new Ping());
        getCommand("broadcast").setExecutor(new Broadcast());
        getCommand("pm").setExecutor(new Pm());
        getCommand("info").setExecutor(new Commands(this));
        getCommand("bssentials").setExecutor(new Commands(this));
        getCommand("gm").setExecutor(new Commands(this));
        getCommand("rules").setExecutor(new Commands(this));
        getCommand("setrules").setExecutor(new Commands(this));
        getCommand("removelag").setExecutor(new RemoveLag());
        getCommand("ci").setExecutor(new Commands(this));
        getCommand("day").setExecutor(new Commands(this));
        getCommand("night").setExecutor(new Commands(this));
        getCommand("rain").setExecutor(new Commands(this));
        getCommand("staff").setExecutor(new Staff(this));
        getCommand("invsee").setExecutor(new Commands(this));
        getCommand("setspawn").setExecutor(new Commands(this));
        getCommand("spawn").setExecutor(new Commands(this));
        getCommand("welcome").setExecutor(new Commands(this));
        getCommand("heal").setExecutor(new Heal());
        getCommand("feed").setExecutor(new Heal());
        getCommand("fly").setExecutor(new Commands(this));
        getCommand("god").setExecutor(new Commands(this));
        getCommand("pm").setExecutor(new Commands(this));
        getCommand("setwarp").setExecutor(new Warp(this));
        getCommand("warp").setExecutor(new Warp(this));
        getCommand("nick").setExecutor(new Commands(this));
        getCommand("underheal").setExecutor(new Heal());
        getCommand("disnick").setExecutor(new Commands(this));
        getCommand("alias").setExecutor(new Commands(this));
        getCommand("rank").setExecutor(new Commands(this));
        getCommand("control").setExecutor(new Commands(this));
        getCommand("delwarp").setExecutor(new Warp(this));
        getCommand("home").setExecutor(new Home(this));
        getCommand("sethome").setExecutor(new Home(this));
        getCommand("delhome").setExecutor(new Home(this));
        getCommand("bancheck").setExecutor(new Commands(this));
        getCommand("uuid").setExecutor(new UUIDCommand());
        getCommand("nuke").setExecutor(new Nuke());

        pm.registerEvents(new ChatLis(this), this);
        pm.registerEvents(new CommandLis(this), this);
        pm.registerEvents(new PlayerJoinLis(this), this);
        pm.registerEvents(this, this);
    }

    /**
     * Saves the warps.yml
     */
    public void saveWarpConfig() {
        try {
            warps.save(warpsf);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[Bssentials] Could not save file warps.yml");
        }
    }

    public void saveHomeConfig() {
        try {
            homes.save(homesf);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[Bssentials] Could not save homes.yml");
        }
    }

    public void saveRankConfig() {
        try {
            ranks.save(ranksf);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[Bssentials] Could not save file ranks.yml");
        }
    }

    private void createFiles() {
        configf = new File(getDataFolder(), "config.yml");
        warpsf = new File(getDataFolder(), "warps.yml");
        homesf = new File(getDataFolder(), "homes.yml");
        ranksf = new File(getDataFolder(), "ranks.yml");

        saveRes(ranksf, "config.yml");
        saveRes(ranksf, "warps.yml");
        saveRes(ranksf, "homes.yml");
        saveRes(ranksf, "ranks.yml");

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

    private void saveRes(File file, String name) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource(name, false);
        }
    }

    /**
     * Creates an Warp
     */
    public void createWarp(Player p, String warpname) {
        warps.set("warps." + warpname + ".world", p.getLocation().getWorld().getName());
        warps.set("warps." + warpname + ".x", p.getLocation().getX());
        warps.set("warps." + warpname + ".y", p.getLocation().getY());
        warps.set("warps." + warpname + ".z", p.getLocation().getZ());
        warps.set("warps." + warpname + ".yaw", p.getLocation().getYaw());
        warps.set("warps." + warpname + ".pitch", p.getLocation().getPitch());
        saveWarpConfig();

        p.sendMessage(ChatColor.GREEN + warpname + " warp set!");
    }

    /**
     * Creates the player's home
     */
    public void createHome(Player p) {
        String homename = p.getName();
        homes.set("homes." + homename + ".world", p.getLocation().getWorld().getName());
        homes.set("homes." + homename + ".x", p.getLocation().getX());
        homes.set("homes." + homename + ".y", p.getLocation().getY());
        homes.set("homes." + homename + ".z", p.getLocation().getZ());
        saveHomeConfig();

        p.sendMessage(ChatColor.GREEN + "Your home has been set!");
    }

    /**
     * Deleates the player's home
     */
    public void delHome(Player p) {
        String homename = p.getName();
        homes.set("homes." + homename, null);
        saveWarpConfig();
    }

    /**
     * Tp a Player.
     */
    public void teleport(Player player, Location l) {
        // check for mount entity in mount metadata
        List<MetadataValue> mount = player.getMetadata("mount");
        if (!mount.isEmpty()) {
            try {
                Entity m = (Entity)mount.get(mount.size()-1).value();
                m.eject();
                player.teleport(l);
                m.teleport(l.add(1,0,0));
            } catch(Exception e) {
                player.teleport(l);
            }
        } else player.teleport(l);
    }
}
