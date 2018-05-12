package bssentials;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.google.common.io.Files;

public class V2WarpConvert {
    public static File configf, warpsf, homesf, ranksf;
    public static FileConfiguration warps = new YamlConfiguration();
    public static FileConfiguration homes = new YamlConfiguration();

    public static void convert(File oldwarps) {
        Bssentials bss = Bssentials.get();

        warpsf = new File(bss.getDataFolder(), "warps.yml");
        homesf = new File(bss.getDataFolder(), "homes.yml");

        warps = new YamlConfiguration();
        homes = new YamlConfiguration();

        try {
            warps.load(warpsf);
            homes.load(homesf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        Set<String> keys = warps.getConfigurationSection("warps").getKeys(false);
        for (String warp : keys) {
            World w = Bukkit.getServer().getWorld(warps.getString("warps." + warp + ".world"));
            double x = warps.getDouble("warps." + warp + ".x");
            double y = warps.getDouble("warps." + warp + ".y");
            double z = warps.getDouble("warps." + warp + ".z");
            float yaw = warps.getInt("warps" + warp + ".yaw");
            float pitch = warps.getInt("warps" + warp + "pitch");
            File file = getFileForWarp(warp);

            String content = "world: " + w.getName() + "\n" +
                    "x: " + x + "\n" + "y: " + y + "\n" + "z: " + z + "\n" +
                    "pitch: " + pitch + "\n" + "yaw: " + yaw;
            try {
                Files.write(content.getBytes(), file);
                System.out.println("Converted old Bss2 warp '" + warp + "' to Bssentials 3 format.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unable to write to " + warp + ".yml");
            }
        }
        try {
            Files.move(oldwarps, new File(oldwarps.getParentFile().getAbsolutePath(), "bss2warps.bkup"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static File getFileForWarp(String warp) {
        if (warp.equalsIgnoreCase("spawn")) {
            return Bssentials.spawn;
        }
        return new File(Bssentials.warpdir, warp + ".yml");
    }
}
