package bssentials;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import bssentials.commands.BCommand;
import bssentials.commands.BssentialsCmd;
import bssentials.commands.CmdInfo;
import bssentials.commands.Heal;
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
        getLogger().info("Bssentials " + getDescription().getVersion());
        i = this;

        warpdir = new File((DATA_FOLDER = getDataFolder()), "warps");
        warpdir.mkdirs();
        warpManager = new Warps(this, warpdir);

        saveResource("info.txt", false);
        saveResource("motd.txt", false);
        saveResource("rules.txt", false);

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

        getLogger().info("Registered " + registered + " commands");

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override public void run() {
                getLogger().info(" __   __   __   ___      ___              __  ");
                getLogger().info("|__) /__` /__` |__  |\\ |  |  |  /\\  |    /__` ");
                getLogger().info("|__) .__/ .__/ |___ | \\|  |  | /~~\\ |___ .__/ ");
                getLogger().info("                                                ");
                getLogger().info("Version " + getDescription().getVersion());

                if (Bukkit.getPluginManager().getPlugin("Essentials") == null) {
                    getLogger().warning("The EssentialsBridge plugin is not installed! "
                            + "Plugins that requrire the EssAPI will not function with Bssentials without this bridge, (ex ChestShop, Vault, etc)");
                    getLogger().info("https://dev.bukkit.org/projects/essentialsapibridge");
                }
            }
        });
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
        for (BCommand bcmd : bases)
            register(bcmd.getClass().getSimpleName().toLowerCase(Locale.ENGLISH), bcmd);
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
                            if (!clazz.getSimpleName().equals("BssentialsCmd") && clazz.isAnnotationPresent(CmdInfo.class) &&
                                    BCommand.class.isAssignableFrom(clazz))
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

    @Deprecated
    public static Bssentials get() {
        return i;
    }

    /**
     * Moved to {@link Warps}
     */
    @Deprecated
    public boolean teleportPlayerToWarp0(Player sender, String warpname) {
        return warpManager.teleportToWarp(sender, warpname);
    }

    @Override
    public Warps getWarps() {
        return warpManager;
    }

}