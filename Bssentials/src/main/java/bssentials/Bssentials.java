package bssentials;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
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

    @Override
    public void onEnable() {
        getLogger().info("===========================");
        getLogger().info("Bssentials 3");
        getLogger().info("Authors: ");
        for (String p : getDescription().getAuthors()) getLogger().info("  - " + p);
        getLogger().info("===========================");
        i = this;

        warpdir = new File(getDataFolder(), "warps");
        warpdir.mkdirs();
        warpManager = new Warps(this, warpdir);

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
        String packagePath = "bssentials/commands";
        URL urls = getClass().getClassLoader().getResource(packagePath);

        File[] classes = new File(urls.getPath()).listFiles();

        List<Class<? extends BCommand>> classList = new ArrayList<Class<? extends BCommand>>();
        for(File classfile : classes){
            String className = packagePath.replace("/", ".") + "." +
                    classfile.getName().substring(0, classfile.getName().indexOf("."));
            try {
                Class<?> clazz = Class.forName(className);
                if (!clazz.getSimpleName().equals("BssentialsCmd") && clazz.isAnnotationPresent(CmdInfo.class) &&
                        BCommand.class.isAssignableFrom(clazz))
                    classList.add(clazz.asSubclass(BCommand.class));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }

    @Deprecated
    public static Bssentials get() {
        return i;
    }

    public boolean hasPerm(CommandSender p, Command cmd) {
        String c = cmd.getName();
        return p.isOp() || p.hasPermission("bssentials.command." + c) || p.hasPermission("essentials." + c)
                || p.hasPermission("accentials.command." + c) || p.hasPermission("bssentials.command.*");
    }

    public boolean teleportPlayerToWarp(Player sender, String warpname) throws NumberFormatException, IOException {
        Location l = warpManager.getWarp(warpname);
        return sender.teleport(l == null ? sender.getLocation() : l);
    }

    @Override
    public Warps getWarps() {
        return warpManager;
    }

}