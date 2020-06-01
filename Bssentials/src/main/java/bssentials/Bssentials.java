package bssentials;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import bssentials.commands.BCommand;
import bssentials.commands.BssentialsCmd;
import bssentials.commands.CmdInfo;
import bssentials.commands.Heal;
import bssentials.configuration.BssConfiguration;
import bssentials.configuration.BssConfiguration.ConfigException;
import bssentials.configuration.Configs;
import bssentials.listeners.PlayerChat;
import bssentials.listeners.PlayerCommand;
import bssentials.listeners.PlayerJoin;
import bssentials.listeners.PlayerLeave;

public class Bssentials extends JavaPlugin implements IBssentials {

    private static Bssentials i;
    public static File warpdir;
    private int registered = 0;
    private Warps warpManager;

    public static File DATA_FOLDER;

    @Override
    public void onEnable() {
        i = this;

        warpdir = new File((DATA_FOLDER = getDataFolder()), "warps");
        warpdir.mkdirs();
        warpManager = new Warps(this, warpdir);

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
        Bukkit.getPluginManager().registerEvents(new PlayerCommand(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);

        getLogger().info("Registered " + registered + " commands");

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override public void run() {
                getLogger().info("Bssentials Version " + getDescription().getVersion());

                if (Bukkit.getPluginManager().getPlugin("Essentials") == null)
                    getLogger().info("It is recomended to install (https://dev.bukkit.org/projects/essentialsapibridge) to allow ChestShop, Vault, etc to work.");
            }
        });

        try {
            fakeRegisterAsEssentials();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
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
            Bukkit.getPluginManager().addPermission(new Permission(permission));
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

    @SuppressWarnings("unchecked")
    private void fakeRegisterAsEssentials() throws Exception {
        Field privateMap = Bukkit.getPluginManager().getClass().getDeclaredField("lookupNames");
        privateMap.setAccessible(true);

        HashMap<String, Plugin> lookupNames = (HashMap<String, Plugin>) privateMap.get(Bukkit.getPluginManager());
        lookupNames.put("Essentials", new Essentials(this));
    }

    @Override
    public BssConfiguration getConfig() {
        return Configs.MAIN;
    }

    @Deprecated
    public static Bssentials get() {
        return i;
    }

    @Override
    public Warps getWarps() {
        return warpManager;
    }

}