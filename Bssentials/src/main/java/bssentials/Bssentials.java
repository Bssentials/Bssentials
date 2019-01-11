package bssentials;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
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
import bssentials.commands.Uuid;
import bssentials.commands.Warp;
import bssentials.commands.Weather;
import bssentials.listeners.PlayerJoin;

public class Bssentials extends JavaPlugin {

    private static Bssentials i;
    public static File warpdir;
    public static File spawn;
    private int registered = 0;

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

        spawn = new File(getDataFolder(), "spawn.yml");
        try {
            spawn.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe("Could not generate empty spawn.yml!");
        }

        File esswarps = new File(getDataFolder().getAbsolutePath().replace("Bssentials", "Essentials"), "warps");
        if (esswarps.exists()) {
            getLogger().info("===========================");
            getLogger().info("EssentialsX warps found!");
            getLogger()
                    .info("To use your old warps move them from the /Essentials/warps/ folder into /Bssentials/warps/");
            getLogger().info("===========================");
        }

        File oldwarps = new File(getDataFolder(), "warps.yml");
        if (oldwarps.exists()) {
            getLogger().info("===========================");
            getLogger().info("Bssentials version 2.x warps found!");
            getLogger().info("Converting old warps to new format!");
            V2WarpConvert.convert(oldwarps);
            getLogger().info("===========================");
        }

        getLogger().info("Registering commands...");

        register("bssentials", new BssentialsCmd());

        register(
                new Warp(), new SetWarp(), new Nuke(), new Broadcast(), new SetSpawn(), new Spawn(),
                new Fly(), new Pm(), new Gamemode(), new Enderchest(), new Heal(), new Exp(), new SpawnMob(),
                new Uuid(), new Hat(), new Weather(), new Balance(), new Ping(), new Pay(), new Afk(), new Nick(),
                new Home(), new SetHome(), new DelHome(), new DelWarp()
                );
        register("underheal", new Heal());
        register("feed", new Heal());
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);

        getLogger().info("Registered " + registered + " commands");

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override public void run() {
                getLogger().info(" __   __   __   ___      ___              __  ");
                getLogger().info("|__) /__` /__` |__  |\\ |  |  |  /\\  |    /__` ");
                getLogger().info("|__) .__/ .__/ |___ | \\|  |  | /~~\\ |___ .__/ ");
                getLogger().info("                                                ");
                getLogger().info("Version " + getDescription().getVersion());

                if (Bukkit.getPluginManager().getPlugin("Essentials") == null) {
                    getLogger().warning("The EssentialsBridge plugin is not intstalled! "
                            + "Plugins that requrire the EssAPI will not function with Bssentials without this bridge");
                }
            }
        });
    }

    @Deprecated
    public void legacy_register(String name, BCommand base) {
        getCommand(name).setExecutor(base);
        getLogger().info("[Commands]: Registering: /" + name);

        registered++;
    }

    public void register(String name, BCommand base) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            getLogger().info("[Commands]: Registering: /" + name);
            CommandWrapper wrap = new CommandWrapper(name, base);
            commandMap.register(name, "bssentials", wrap);
         } catch(Exception e) { e.printStackTrace(); }
    }

    public void register(BCommand... bases) {
        for (BCommand bcmd : bases)
            register(bcmd.getClass().getSimpleName().toLowerCase(Locale.ENGLISH), bcmd);
    }

    public static Bssentials get() {
        return i;
    }

    public boolean hasPerm(CommandSender p, Command cmd) {
        String c = cmd.getName();
        return p.isOp() || p.hasPermission("bssentials.command." + c) || p.hasPermission("essentials." + c)
                || p.hasPermission("accentials.command." + c) || p.hasPermission("bssentials.command.*");
    }

    public boolean teleportPlayerToWarp(Player sender, String warpname) throws NumberFormatException, IOException {
        // TODO: Proper YAML parser
        Location l = sender.getLocation();
        for (String line : Files.readAllLines(Bssentials.spawn.toPath())) {
            if (line.startsWith("world")) l.setWorld(Bukkit.getWorld(line.substring("world: ".length())));

            if (line.startsWith("x:")) l.setX(Double.valueOf(line.substring(3)));

            if (line.startsWith("y:")) l.setY(Double.valueOf(line.substring(3)));

            if (line.startsWith("z:")) l.setZ(Double.valueOf(line.substring(3)));

            if (line.startsWith("pitch")) l.setPitch(new Float(Double.valueOf(line.substring("pitch: ".length()))));

            if (line.startsWith("yaw")) l.setYaw(new Float(Double.valueOf(line.substring("yaw: ".length()))));
        }
        return sender.teleport(l);
    }

    public boolean isSpawnSet() {
        try {
            return Files.readAllLines(spawn.toPath()).size() < 2;
        } catch (IOException e) {
            return false;
        }
    }

}