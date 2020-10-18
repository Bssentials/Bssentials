package bssentials.bukkit;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import bssentials.api.IWorld;
import bssentials.api.Location;
import bssentials.api.User;
import bssentials.include.FileConfiguration;

public class ConsoleUser implements User {

    public BigDecimal money = new BigDecimal(100);
    private CommandSender sender;

    public ConsoleUser(CommandSender sender) {
        this.sender = sender;
    } 

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public FileConfiguration getConfig() {
        return null;
    }

    @Override
    public Location getHome(String home) {
        return null;
    }

    @Override
    public void save() {
    }

    @Override
    public BigDecimal getMoney() {
        return money;
    }

    @Override
    public boolean isAuthorized(String string) {
        return true;
    }

    @Override
    public void setMoney(BigDecimal balance) {
    }

    @Override
    public void setNick(String n) throws Exception {
    }

    @Override
    public boolean isNPC() {
        return false;
    }

    @Override
    public void setHome(String home, Location l) {
    }

    @Override
    public void delHome(String home) {
    }

    @Override
    public void sendMessage(String txt) {
        Bukkit.getConsoleSender().sendMessage(txt);
    }

    @Override
    public Set<String> getHomes() {
        return null;
    }

    @Override
    public UUID getUniqueId() {
        return UUID.randomUUID();
    }

    @Override
    public String getName(boolean custom) {
        return "org.bukkit.command.ConsoleCommandSender";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public Location getTargetBlock(int maxDistance) {
        return null;
    }

    @Override
    public void clearInventory(String item) {
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public void openEnderchest(User target) {
    }

    @Override
    public int getExpLevel() {
        return 0;
    }

    @Override
    public void setExpLevel(int i) {
    }

    @Override
    public void setAllowFly(boolean allow) {
    }

    @Override
    public boolean isAllowedToFly() {
        return false;
    }

    @Override
    public void setGameMode(int mode) {
    }

    @Override
    public int getItemInMainHand() {
        return 0;
    }

    @Override
    public void setHelmentToMainHandItem() {
    }

    @Override
    public void setHealthAndFoodLevel(int h, int f) {
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public boolean teleport(Location l) {
        return false;
    }

    @Override
    public void openOtherUserInventory(User target) {
    }

    @Override
    public Object getBase() {
        return sender;
    }

    @Override
    public String getNick() {
        return "org.bukkit.command.ConsoleCommandSender";
    }

    @Override
    public IWorld getWorld() {
        return null;
    }

    @Override
    public int getPing() {
        return -1;
    }

}
