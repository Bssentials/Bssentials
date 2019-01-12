package bssentials;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import bssentials.commands.Afk;
import bssentials.commands.BCommand;
import bssentials.commands.Balance;
import bssentials.commands.BigTree;
import bssentials.commands.Broadcast;
import bssentials.commands.BssentialsCmd;
import bssentials.commands.DelHome;
import bssentials.commands.DelWarp;
import bssentials.commands.Enderchest;
import bssentials.commands.Exp;
import bssentials.commands.Fly;
import bssentials.commands.Gamemode;
import bssentials.commands.Hat;
import bssentials.commands.Heal;
import bssentials.commands.Home;
import bssentials.commands.Nick;
import bssentials.commands.Nuke;
import bssentials.commands.Pay;
import bssentials.commands.Ping;
import bssentials.commands.Pm;
import bssentials.commands.SetHome;
import bssentials.commands.SetSpawn;
import bssentials.commands.SetWarp;
import bssentials.commands.Spawn;
import bssentials.commands.SpawnMob;
import bssentials.commands.Staff;
import bssentials.commands.Uuid;
import bssentials.commands.Warp;
import bssentials.commands.Weather;
import bssentials.listeners.PlayerCommand;
import bssentials.listeners.PlayerJoin;
import bssentials.listeners.PlayerLeave;

public class Bssentials extends JavaPlugin {

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

        register(
                new Warp(), new SetWarp(), new Nuke(), new Broadcast(), new SetSpawn(), new Spawn(),
                new Fly(), new Pm(), new Gamemode(), new Enderchest(), new Heal(), new Exp(), new SpawnMob(),
                new Uuid(), new Hat(), new Weather(), new Balance(), new Ping(), new Pay(), new Afk(), new Nick(),
                new Home(), new SetHome(), new DelHome(), new DelWarp(), new Staff(), new BigTree()
                );
        register("underheal", new Heal());
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
                            + "Plugins that requrire the EssAPI will not function with Bssentials without this bridge");
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

    public Warps getWarps() {
        return warpManager;
    }

    @Deprecated
    public File getFileForWarp0(String warp) throws FileNotFoundException {
        return warpManager.getWarpFile(warp);
    }

    @Deprecated
    public Location getWarp0(String name) {
        return warpManager.getWarp(name);
    }

    @Deprecated
    public boolean isSpawnSet() {
        return warpManager.isSpawnSet();
    }

}