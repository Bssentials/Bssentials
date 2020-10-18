package bssentials.bukkit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import bssentials.Bssentials;
import bssentials.Warps;
import bssentials.api.IBssentials;
import bssentials.api.IWorld;
import bssentials.api.User;
import bssentials.commands.BCommand;
import bssentials.commands.BssentialsCmd;
import bssentials.commands.CmdInfo;
import bssentials.commands.Heal;
import bssentials.commands.SpawnMob;
import bssentials.configuration.BssConfiguration;
import bssentials.configuration.BssConfiguration.ConfigException;
import bssentials.configuration.Configs;
import bssentials.listeners.PlayerJoin;
import bssentials.listeners.PlayerLeave;

public class BssentialsPlugin extends JavaPlugin implements IBssentials {

    private static BssentialsPlugin i;
    public static File warpdir;
    private int registered = 0;
    private Warps warpManager;

    public static File DATA_FOLDER;
    public User CONSOLE_USER;

    public HashMap<UUID, User> users;
    public HashMap<String, IWorld> worlds;

    @Override
    public void onEnable() {
        i = this;
        Bssentials.setInstance(this, (DATA_FOLDER = getDataFolder()));

        warpdir = new File((DATA_FOLDER = getDataFolder()), "warps");
        warpdir.mkdirs();
        warpManager = new Warps(this, warpdir);
        CONSOLE_USER = new ConsoleUser(Bukkit.getConsoleSender());
        users = new HashMap<>();

        saveResource("config.yml", false);
        saveResource("info.txt", false);
        saveResource("motd.txt", false);
        saveResource("rules.txt", false);

        getLogger().info("Loading configuration...");
        try {
            Configs.initConfigs();
        } catch (ConfigException e1) {
            e1.printStackTrace();
        }

        getLogger().info("Registering commands...");

        // Entities for SpawnMob
        for (EntityType e : EntityType.values())
            if (e.isSpawnable()) SpawnMob.mobs.put(String.valueOf(e).toLowerCase(), e);

        register("bssentials", new BssentialsCmd());

        for (Class<? extends BCommand> clazz : getCommandClasses()) {
            try {
                register(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                getLogger().warning("Unable to register \"/" + clazz.getName() + "\" command.");
            }
        }
        register("feed", new Heal());

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);

        getLogger().info("Registered " + registered + " commands");

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override public void run() {
                for (World w : Bukkit.getWorlds())
                    worlds.put(w.getName(), new BukkitWorld(w));

                getLogger().info("Bssentials Version " + getDescription().getVersion());

                if (Bukkit.getPluginManager().getPlugin("Essentials") == null)
                    getLogger().info("It is recomended to install \"https://dev.bukkit.org/projects/essentialsapibridge\" to allow ChestShop, Vault, etc to work.");
            }
        });
    }

    public static bssentials.api.Location copy(org.bukkit.Location from) {
        bssentials.api.Location to = new bssentials.api.Location();
        to.world = from.getWorld().getName();
        to.x = from.getX();
        to.y = from.getY();
        to.pitch = from.getPitch();
        to.yaw = from.getYaw();
        return to;
    }

    public void register(String name, BCommand base) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            if (System.getProperty("bss.debug") != null)
                getLogger().info("[Commands]: Registering: /" + name);
            CommandWrapper wrap = new CommandWrapper(name, base);
            if (commandMap.register(name, "bssentials", wrap))
                registered++;
         } catch(Exception e) { e.printStackTrace(); }
    }

    public void register(BCommand... bases) {
        for (BCommand bcmd : bases) {
            register(bcmd.getClass().getSimpleName().toLowerCase(Locale.ENGLISH), bcmd);
            String permission = bcmd.info.permission();
            if (permission.equalsIgnoreCase("REQUIRES_OP") || permission.equalsIgnoreCase("NONE"))
                continue;
        }
    }

    public List<Class<? extends BCommand>> getCommandClasses() {
        List<Class<? extends BCommand>> classList = new ArrayList<Class<? extends BCommand>>();
        try {
            Enumeration<URL> en = getClass().getClassLoader().getResources("bssentials");

            if (en.hasMoreElements()) {
                URL url = en.nextElement();
                JarURLConnection urlcon = (JarURLConnection) url.openConnection();
                try (JarFile jar = urlcon.getJarFile();) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        String entry = entries.nextElement().getName().replaceAll("/", ".").replace(".class", "");
                        if (!(entry.startsWith("bssentials.commands") && entry.length() > 20))
                            continue;
                        try {
                            Class<?> clazz = Class.forName(entry);
                            if (!clazz.getSimpleName().equals("BssentialsCmd") && clazz.isAnnotationPresent(CmdInfo.class) && BCommand.class.isAssignableFrom(clazz))
                                classList.add(clazz.asSubclass(BCommand.class));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return classList;
    }

    @Override
    public FileConfiguration getConfig() {
        return null;
    }

    public static BssentialsPlugin getInstance() {
        return i;
    }

    @Override
    public Warps getWarps() {
        return warpManager;
    }

    @SuppressWarnings("deprecation")
    @Override
    public User getUser(Object base) {
        if (base instanceof Player) {
            Player p = (Player) base;
            if (users.containsKey(p.getUniqueId()))
                return users.get(p.getUniqueId());
            User user = new BukkitUser(p);
            users.put(p.getUniqueId(), user);
            return user;
        }
        if (base instanceof String) {
            Player p = Bukkit.getPlayerExact((String)base);
            if (users.containsKey(p.getUniqueId()))
                return users.get(p.getUniqueId());
            User user = new BukkitUser(p);
            users.put(p.getUniqueId(), user);
            return user;
        }
        return CONSOLE_USER;
    }

    @Override
    public BssConfiguration getMainConfig() {
        return Configs.MAIN;
    }

    @Override
    public String getVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public void broadcastMessage(String string) {
        Bukkit.broadcastMessage(string);
    }

    @Override
    public Collection<IWorld> getWorlds() {
        for (World w : Bukkit.getWorlds()) 
            if (!worlds.containsKey(w.getName()))
                worlds.put(w.getName(), new BukkitWorld(w));
        return worlds.values();
    }

    @Override
    public int getOnlinePlayerCount() {
        return this.getServer().getOnlinePlayers().size();
    }

    @Override
    public IWorld getWorld(String string) {
        for (World w : Bukkit.getWorlds())
            if (!worlds.containsKey(w.getName()))
                worlds.put(w.getName(), new BukkitWorld(w));
        return worlds.get(string);
    }

}
