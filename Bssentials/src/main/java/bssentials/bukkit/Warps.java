package bssentials.bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import bssentials.Bssentials;
import bssentials.api.IWarps;
import bssentials.api.User;

public class Warps implements IWarps {

    private final File folder;
    private final Logger logger;
    private final File spawn;

    public Warps(Bssentials bss, File folder) {
        this.logger = bss.getLogger();
        this.folder = folder;
        folder.mkdirs();

        spawn = new File(bss.getDataFolder(), "spawn.yml");
        try {
            spawn.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("Could not generate empty spawn.yml!");
        }

        File esswarps = new File(bss.getDataFolder().getAbsolutePath().replace("Bssentials", "Essentials"), "warps");
        if (esswarps.exists()) {
            logger.info("===========================");
            logger.info("EssentialsX warps found!");
            logger.info("To use your old warps move them from the /Essentials/warps/ folder into /Bssentials/warps/");
            logger.info("===========================");
        }
    }

    @Override
    public boolean isEmpty() {
        return folder.listFiles() == null || folder.listFiles().length <= 0;
    }

    @Override
    public File[] getWarpFiles() {
        return folder.listFiles();
    }

    @Override
    public Location getWarp(String name) {
        Location l = new Location(null, 0, 0, 0);
        try {
            for (String line : Files.readAllLines(getWarpFile(name).toPath())) {
                if (line.startsWith("world"))
                    l.setWorld(Bukkit.getWorld(line.substring("world: ".length())));

                if (line.startsWith("x:")) l.setX(Double.valueOf(line.substring(3)));
                if (line.startsWith("y:")) l.setY(Double.valueOf(line.substring(3)));
                if (line.startsWith("z:")) l.setZ(Double.valueOf(line.substring(3)));

                if (line.startsWith("pitch")) l.setPitch(new Float(Double.valueOf(line.substring("pitch: ".length()))));
                if (line.startsWith("yaw")) l.setYaw(new Float(Double.valueOf(line.substring("yaw: ".length()))));
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return l;
    }

    @Override
    public void setWarp(String name, Location loc) throws IOException {
        File file = getWarpFile(name);

        String content = "world: " + loc.getWorld().getName() + "\n" + 
                "x: " + loc.getX() + "\n" + "y: " + loc.getY() + "\n" + "z: " + loc.getZ() + "\n" +
                "pitch: " + loc.getPitch() + "\n" + "yaw: " + loc.getYaw();

        com.google.common.io.Files.write(content.getBytes(), file);
    }

    @Override
    public boolean removeWarp(String name) {
        return getWarpFile(name).delete();
    }

    @Override
    public File getWarpFile(String name) {
        if (name.equalsIgnoreCase("spawn")) return spawn;

        return new File(folder, name + ".yml");
    }

    @Override
    public boolean isSpawnSet() {
        try {
            return Files.readAllLines(spawn.toPath()).size() < 2;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public int getCount() {
        return getWarpFiles().length;
    }

    @Override
    public boolean teleportToWarp(User user, String warpName) {
        Location l = getWarp(warpName);
        return user.teleport(l == null ? user.getLocation() : l);
    }

}