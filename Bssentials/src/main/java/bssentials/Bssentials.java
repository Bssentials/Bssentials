package bssentials;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import bssentials.commands.BCommand;
import bssentials.commands.Broadcast;
import bssentials.commands.BssentialsCmd;
import bssentials.commands.Debug;
import bssentials.commands.Enderchest;
import bssentials.commands.Fly;
import bssentials.commands.Gamemode;
import bssentials.commands.Nuke;
import bssentials.commands.Pm;
import bssentials.commands.SetSpawn;
import bssentials.commands.SetWarp;
import bssentials.commands.Spawn;
import bssentials.commands.Warp;
import bssentials.listeners.PlayerJoin;

public class Bssentials extends JavaPlugin {
    private static Bssentials i;
    public static File warpdir;
    public static File spawn;
    private int registered = 0;
    public boolean debug = false;

    @Override
    public void onEnable() {
        getLogger().info("===========================");
        getLogger().info("Bssentials 3");
        getLogger().info("Authors: ");
        for (String p : getDescription().getAuthors()) getLogger().info("  - " + p);
        getLogger().info("===========================");
        i = this;

        warpdir = new File(Bssentials.get().getDataFolder(), "warps");
        warpdir.mkdirs();

        spawn = new File(Bssentials.get().getDataFolder(), "spawn.yml");
        try {
            spawn.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe("Could not generate empty spawn.yml!");
        }

        File oldwarps = new File(getDataFolder(), "warps.yml");
        if (oldwarps.exists()) {
            getLogger().info("===========================");
            getLogger().info("Bssentials version 2.x warps found!");
            getLogger().info("Converting old warps to new format!");
            convertWarps(oldwarps);
            getLogger().info("===========================");
        }

        getLogger().info("Registering commands...");

        register("bssentials", new BssentialsCmd());

        register(
                new Warp(),
                new SetWarp(),
                new Nuke(),
                new Broadcast(),
                new SetSpawn(),
                new Spawn(),
                new Debug(),
                new Fly(),
                new Pm(), new Gamemode(), new Enderchest()
                );

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);

        getLogger().info("Registered " + registered + " commands");

        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override public void run() {
                getLogger().info(" __   __   __   ___      ___              __  ");
                getLogger().info("|__) /__` /__` |__  |\\ |  |  |  /\\  |    /__` ");
                getLogger().info("|__) .__/ .__/ |___ | \\|  |  | /~~\\ |___ .__/ ");
                getLogger().info("                                                ");
                getLogger().info("Version " + getDescription().getVersion());
            }
        });
    }

    private void convertWarps(File oldwarps) {
        System.out.println("[Bssentials3]: Method not implemented: convertWarps");
    }

    public void register(String name, BCommand base) {
        getCommand(name).setExecutor(base);
        if (debug) {
            getLogger().info("[DEBUG]: Registering command: /" + name);
        }
        registered++;
    }

    public void register(BCommand... bases) {
        for (BCommand bcmd : bases) register(bcmd.getClass().getSimpleName().toLowerCase(Locale.ENGLISH), bcmd);
    }

    public static Bssentials get() {
        return i;
    }

    public boolean hasPerm(CommandSender p, Command cmd) {
        String c = cmd.getName();
        return (p.isOp() | p.hasPermission("bssentials.command." + c) | p.hasPermission("essentials." + c)
                | p.hasPermission("accentials.command." + c) | p.hasPermission("dssentials.command." + c)
                | p.hasPermission("bssentials.command.*") | p.hasPermission("se." + c));
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