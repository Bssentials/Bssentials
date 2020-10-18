package bssentials;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import bssentials.api.IBssentials;
import bssentials.api.IWarps;
import bssentials.api.Location;
import bssentials.api.User;

public class Warps implements IWarps {

    private final File folder;
    private final File spawn;

    public Warps(IBssentials bssentialsPlugin, File folder) {
        this.folder = folder;
        folder.mkdirs();

        spawn = new File(Bssentials.DATA_FOLDER, "spawn.yml");
        try {
            spawn.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not generate empty spawn.yml!");
        }

        File esswarps = new File(Bssentials.DATA_FOLDER.getAbsolutePath().replace("Bssentials", "Essentials"), "warps");
        if (esswarps.exists()) {
            System.out.println("BSSENTIALS: ===========================");
            System.out.println("BSSENTIALS: EssentialsX warps found!");
            System.out.println("BSSENTIALS: To use your old warps move them from the /Essentials/warps/ folder into /Bssentials/warps/");
            System.out.println("BSSENTIALS: ===========================");
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
    public bssentials.api.Location getWarp(String name) {
        Location l = new Location();
        try {
            for (String line : Files.readAllLines(getWarpFile(name).toPath())) {
                if (line.startsWith("world"))
                    l.world = line.substring("world: ".length());

                if (line.startsWith("x:")) l.x = (Double.valueOf(line.substring(3)));
                if (line.startsWith("y:")) l.y = (Double.valueOf(line.substring(3)));
                if (line.startsWith("z:")) l.z = (Double.valueOf(line.substring(3)));

                if (line.startsWith("pitch")) l.pitch = (new Float(Double.valueOf(line.substring("pitch: ".length()))));
                if (line.startsWith("yaw")) l.yaw = (new Float(Double.valueOf(line.substring("yaw: ".length()))));
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return l;
    }

    @Override
    public void setWarp(String name, bssentials.api.Location loc) throws IOException {
        File file = getWarpFile(name);

        String content = "world: " + loc.world + "\n" + 
                "x: " + loc.x + "\n" + "y: " + loc.y + "\n" + "z: " + loc.z + "\n" +
                "pitch: " + loc.pitch + "\n" + "yaw: " + loc.yaw;

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
        bssentials.api.Location l = getWarp(warpName);
        return user.teleport(l == null ? user.getLocation() : l);
    }

}