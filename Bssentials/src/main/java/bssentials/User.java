package bssentials;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import bssentials.Bssentials;

public class User {
    private Player base;
    private File folder;
    private File file;

    public boolean npc = false;
    public String lastAccountName;
    public BigDecimal money = new BigDecimal(100);
    public String nick = "_null_";

    private Map<String, Object> homemap;
    public ArrayList<String> homes = new ArrayList<>();

    public static FileConfiguration user = new YamlConfiguration();

    public User(Player base) {
        this.base = base;
        this.folder = new File(Bssentials.get().getDataFolder(), "userdata");
        this.file = new File(folder, base.getUniqueId().toString() + ".yml");
        this.lastAccountName = base.getName();
        folder.mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Unable to create file: " + folder.getAbsolutePath());
                e.printStackTrace();
            }
            npc = false;
            lastAccountName = base.getName();
            money = BigDecimal.valueOf(100.0); // Default
            save();
        }

        try {
            user.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        if (file.exists()) {
            npc = user.getBoolean("npc", false);
            lastAccountName = user.getString("lastAccountName");
            try {
                money = new BigDecimal((double) user.get("money"));
            } catch (Exception e) {
                money = BigDecimal.valueOf(Double.valueOf((int) user.get("money")));
            }
            nick = user.getString("nick");
            homemap = _getHomes();
        }
    }

    private Map<String, Object> _getHomes() {
        if (user.isConfigurationSection("homes")) {
            return user.getConfigurationSection("homes").getValues(false);
        }
        return new HashMap<>();
    }

    public List<String> getHomes() {
        return new ArrayList<>(homemap.keySet());
    }

    public Location getHome(String home) {
        if (user.getConfigurationSection("homes." + home) == null) {
            return null;
        }
        World w = Bukkit.getServer().getWorld(user.getString("homes." + home + ".world"));
        double x = user.getDouble("homes." + home + ".x");
        double y = user.getDouble("homes." + home + ".y");
        double z = user.getDouble("homes." + home + ".z");
        return new Location(w, x, y, z);
    }

    private void save() {
        try {
            user.save(file);
            npc = user.getBoolean("npc", false);
            lastAccountName = user.getString("lastAccountName");
            try {
                money = new BigDecimal((double) user.get("money"));
            } catch (Exception e) {
                money = BigDecimal.valueOf(Double.valueOf((int) user.get("money")));
            }
            nick = user.getString("nick");
            homemap = _getHomes();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to write to file: " + folder.getAbsolutePath());
        }
    }

    public BigDecimal getMoney() {
        return money;
    }

    public boolean isAuthorized(String string) {
        return base.hasPermission(string);
    }

    public void setMoney(BigDecimal balance) {
        user.set("money", balance.doubleValue());
        save();
    }

    public void setNick(String n) {
        user.set("nick", n);
        save();
    }

    public boolean isNPC() {
        return false; // TODO: should Bssentials have NPC support?
    }

    @SuppressWarnings("deprecation")
    public static User getByName(String name) {
        return new User(Bukkit.getPlayerExact(name));
    }

    public void setHome(String home, Location l) {
        user.set("homes." + home + ".world", l.getWorld().getName());
        user.set("homes." + home + ".x", l.getX());
        user.set("homes." + home + ".y", l.getY());
        user.set("homes." + home + ".z", l.getZ());
        save();
    }
}