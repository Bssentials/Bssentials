package ml.bssentials.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
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
import ml.bssentials.commands.BssentialsCmd;
import ml.bssentials.commands.Commands;
import ml.bssentials.commands.Enderchest;
import ml.bssentials.commands.Exp;
import ml.bssentials.commands.Fly;
import ml.bssentials.commands.Gamemode;
import ml.bssentials.commands.Heal;
import ml.bssentials.commands.Home;
import ml.bssentials.commands.Invsee;
import ml.bssentials.commands.Nick;
import ml.bssentials.commands.Nuke;
import ml.bssentials.commands.Ping;
import ml.bssentials.commands.Pm;
import ml.bssentials.commands.RemoveLag;
import ml.bssentials.commands.Spawn;
import ml.bssentials.commands.Staff;
import ml.bssentials.commands.Sudo;
import ml.bssentials.commands.UUIDCommand;
import ml.bssentials.commands.ViewNick;
import ml.bssentials.commands.Warp;
import ml.bssentials.commands.spawnmob;
import ml.bssentials.listeners.ChatLis;
import ml.bssentials.listeners.CommandLis;
import ml.bssentials.listeners.PlayerJoinLis;
import ml.bssentials.listeners.PlayerLeave;
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
    private static Bssentials instance;
    private int registered = 0;
    public static ArrayList<String> afkplayers = new ArrayList<>();

    public static final String prefix = ChatColor.GREEN + "[Bssentials]" + ChatColor.GOLD + " ";

    public File configf, warpsf, homesf, ranksf;
    public FileConfiguration config = new YamlConfiguration();
    public FileConfiguration warps = new YamlConfiguration();
    public FileConfiguration homes = new YamlConfiguration();
    public FileConfiguration ranks = new YamlConfiguration();
    public static final Permission ALL_PERM = new Permission("bssentials.all");

    public static Bssentials getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
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

        getLogger().info("Registering commands...");

        register("spawnmob", new spawnmob());
        register("viewnick", new ViewNick());
        register("ping", new Ping());
        register("broadcast", new Broadcast());
        register("pm", new Pm());
        register("uuid", new UUIDCommand());
        register("nuke", new Nuke());
        register("enderchest", new Enderchest());
        register("exp", new Exp());
        register("sudo", new Sudo());
        register("bssentials", new BssentialsCmd());
        register("removelag", new RemoveLag());
        register("staff", new Staff(this));
        register("heal", new Heal());
        register("feed", new Heal());
        register("fly", new Fly());
        register("underheal", new Heal());
        register("gamemode", new Gamemode());
        register("nick", new Nick());
        register("invsee", new Invsee());
        register("setspawn", new Spawn());
        register("spawn", new Spawn());

        register("info", new Commands(this));
        register("gamemode", new Commands(this));
        register("rules", new Commands(this));
        register("setrules", new Commands(this));
        register("ci", new Commands(this));
        register("day", new Commands(this));
        register("night", new Commands(this));
        register("rain", new Commands(this));
        register("welcome", new Commands(this));
        register("god", new Commands(this));
        register("pm", new Commands(this));
        register("disnick", new Commands(this)); // Deprecated
        register("rank", new Commands(this));
        register("bancheck", new Commands(this));

        register("setwarp", new Warp(this));
        register("warp", new Warp(this));
        register("delwarp", new Warp(this));
        register("home", new Home(this));
        register("sethome", new Home(this));
        register("delhome", new Home(this));

        getLogger().info("Registered all " + registered + " commands.");

        pm.registerEvents(new ChatLis(this), this);
        pm.registerEvents(new CommandLis(this), this);
        pm.registerEvents(new PlayerJoinLis(this), this);
        pm.registerEvents(new PlayerLeave(), this);
        pm.registerEvents(this, this);
        new Metrics(this);
    }

    public void register(String name, CommandExecutor base) {
        getCommand(name).setExecutor(base);
        registered++;
    }

    /**
     * Saves the warps.yml
     */
    @Deprecated
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
        // TODO: per warp configuration
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

    public void delHome(Player p) {
        String homename = p.getName();
        homes.set("homes." + homename, null);
        saveHomeConfig();
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
