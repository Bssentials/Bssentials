package bssentials;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.Files;

public class V2WarpConvert {

    public static File configf, warpsf, homesf, ranksf;
    public static FileConfiguration warps;
    public static FileConfiguration homes;

    public static void convert(File oldwarps, Warps newWarps) {
        File dataFolder = oldwarps.getParentFile();

        warpsf = new File(dataFolder, "warps.yml");
        homesf = new File(dataFolder, "homes.yml");

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

            float yaw = warps.getInt("warps." + warp + ".yaw");
            float pitch = warps.getInt("warps." + warp + ".pitch");

            try {
                newWarps.setWarp(warp, new Location(w, 
                        warps.getDouble("warps." + warp + ".x"),
                        warps.getDouble("warps." + warp + ".y"),
                        warps.getDouble("warps." + warp + ".z"), yaw, pitch));
                System.out.println("Converted old warp '" + warp + "' to new format.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unable to convert new warp");
            }
        }

        Set<String> h = homes.getConfigurationSection("homes").getKeys(false);
        for (String plr : h) {
            World w = Bukkit.getServer().getWorld(homes.getString("homes." + plr + ".world"));
            double x = homes.getDouble("homes." + plr + ".x");
            double y = homes.getDouble("homes." + plr + ".y");
            double z = homes.getDouble("homes." + plr + ".z");

            @SuppressWarnings("deprecation")
            OfflinePlayer off = Bukkit.getOfflinePlayer(plr);
            if (off.hasPlayedBefore()) {
                FileConfiguration user = new YamlConfiguration();
                File userconfig = new File(new File(dataFolder, "userdata"), off.getUniqueId() + ".yml");
                try {
                    user.load(userconfig);
                } catch (IOException | InvalidConfigurationException e) {e.printStackTrace();}
                user.set("homes.home.world", w.getName());
                user.set("homes.home.x", x);
                user.set("homes.home.y", y);
                user.set("homes.home.z", z);
                try {
                    user.save(userconfig);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else System.out.println("Unable to convert home for: " + plr + ". Reason: Player has not played before.");
        }

        try {
            Files.move(oldwarps, new File(oldwarps.getParentFile().getAbsolutePath(), "bss2warps.bkup"));
        } catch (IOException e) {e.printStackTrace();}
    }

}