package bssentials;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.bukkit.Bukkit;
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
        } else {
            try {
                List<String> list = Files.readAllLines(file.toPath());
                for (String el : list) {
                    String a = el.split("[:]")[0].trim();
                    String b = el.split("[:]")[1].trim();
                    if (a.equalsIgnoreCase("npc")) npc = Boolean.valueOf(b);
                    if (a.equalsIgnoreCase("lastAccountName")) lastAccountName = b;
                    if (a.equalsIgnoreCase("money")) money = new BigDecimal(b);
                    if (a.equalsIgnoreCase("nick")) nick = b;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void save() {
        try {
            String content = String.format("npc: %s\nlastAccountName: %s\nmoney: %s\nnick: %s", npc, lastAccountName,
                    money, nick);
            Files.write(file.toPath(), content.getBytes(), StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
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
        money = balance;
        save();
    }

    public void setNick(String n) {
        nick = n;
        save();
    }

    public boolean isNPC() {
        return false; // TODO: should Bssentials have NPC support?
    }

    @SuppressWarnings("deprecation")
    public static User getByName(String name) {
        return new User(Bukkit.getPlayerExact(name));
    }
}