package bssentials.api;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import bssentials.bukkit.BukkitUser;

public interface User {

    public boolean isPlayer();

    public FileConfiguration getConfig();

    public Location getHome(String home);

    public void save();

    public BigDecimal getMoney();

    public boolean isAuthorized(String string);

    public void setMoney(BigDecimal balance);

    public void setNick(String n) throws Exception;

    public boolean isNPC();

    public void setHome(String home, Location l);

    public void delHome(String home);

    public void sendMessage(String txt);

    public Set<String> getHomes();

    public UUID getUniqueId();

    public String getName(boolean custom);

    public Location getLocation();

    public Block getTargetBlock(Set<Material> transparent, int maxDistance);

    public void clearInventory(String item);

    public boolean isOnline();

    public void openEnderchest(User target);

    public int getExpLevel();

    public void setExpLevel(int i);

    public void setAllowFly(boolean allow);

    public boolean isAllowedToFly();

    public void setGameMode(GameMode mode);

    public int getItemInMainHand();

    public void setHelmentToMainHandItem();

    public void setHealthAndFoodLevel(int h, int f);

    public boolean isOp();

    public boolean teleport(Location l);

    public void openOtherUserInventory(User target);

    public Object getBase();

    @Deprecated
    public static User getByName(String name) {
        return BukkitUser.getByName(name);
    }

    public String getNick();

}