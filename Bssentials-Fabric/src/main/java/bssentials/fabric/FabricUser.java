package bssentials.fabric;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import bssentials.Bssentials;
import bssentials.api.IWorld;
import bssentials.api.Location;
import bssentials.api.User;
import bssentials.configuration.BssConfiguration;
import bssentials.configuration.Configs;
import bssentials.include.ConfigurationSection;
import bssentials.include.FileConfiguration;
import bssentials.include.InvalidConfigurationException;
import bssentials.include.YamlConfiguration;
import cyber.permissions.v1.CyberPermissions;
import cyber.permissions.v1.Permissible;
import cyber.permissions.v1.Permission;
import cyber.permissions.v1.PermissionDefaults;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.GameMode;
import net.minecraft.world.level.ServerWorldProperties;

public class FabricUser implements User {

    public ServerPlayerEntity base;
    private static File folder;
    private File file;

    public boolean npc = false;
    public String lastAccountName;
    public BigDecimal money = new BigDecimal(100);
    public String nick = "_null_";

    public ArrayList<String> homes = new ArrayList<>();

    public FileConfiguration user = new YamlConfiguration();

    public FabricUser(ServerPlayerEntity p) {
        this.base = p;
        if (null == folder)
            folder = new File(Bssentials.DATA_FOLDER, "userdata");

        this.file = new File(folder, base.getUuid().toString() + ".yml");
        this.lastAccountName = base.getName().asString();
        this.user = new YamlConfiguration();
        folder.mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unable to create file: " + folder.getAbsolutePath());
            }
            user.set("npc", false);
            user.set("lastAccountName", base.getName().asString());
            user.set("money", 100.0); // Default
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
        }
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public FileConfiguration getConfig() {
        return user;
    }

    public Location getHome(String home) {
        if (user.getConfigurationSection("homes." + home) == null)
            return null;

        String w = user.getString("homes." + home + ".world");
        double x = user.getDouble("homes." + home + ".x");
        double y = user.getDouble("homes." + home + ".y");
        double z = user.getDouble("homes." + home + ".z");
        
        Location loc = new Location();
        loc.world = w;
        loc.x = x;
        loc.y = y;
        loc.z = z;

        return loc;
    }

    public void save() {
        try {
            user.save(file);
            npc = user.getBoolean("npc", false);
            lastAccountName = user.getString("lastAccountName");
            try {
                money = new BigDecimal((double) user.get("money"));
            } catch (Exception e) {
                Object mon = user.get("money");
                money = mon instanceof BigDecimal ? (BigDecimal) mon : BigDecimal.valueOf(Double.valueOf((int) mon));
            }
            nick = user.getString("nick");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to write to file: " + folder.getAbsolutePath());
        }
    }

    public BigDecimal getMoney() {
        return money;
    }

    public boolean isAuthorized(String path) {
        Permissible permissible = CyberPermissions.getPermissible(base);
        Permission perm = new Permission(path, "An Bssentials Permission", PermissionDefaults.OPERATOR);
        return permissible.hasPermission(perm) || base.isCreativeLevelTwoOp();
    }

    public void setMoney(BigDecimal balance) {
        user.set("money", balance.doubleValue());
        save();
    }

    public void setNick(String n) throws Exception {
        BssConfiguration config = Configs.MAIN;
        int maxLength = config.getInt("max-nick-length");

        String s = config.getBoolean("ignore-colors-in-max-nick-length", true) ? n : n;

        if (maxLength > 0 && s.length() > maxLength)
            throw new Exception("Nickname is too long");

        if (config.contains("nickname-prefix"))
            n = config.getString("nickname-prefix") + n;

        List<String> blackList = config.getStringList("nick-blacklist");
        for (String str : blackList) {
            String strip = n;
            if ((strip.equalsIgnoreCase(str) || strip.contains(str)) && !isAuthorized("bssentials.nick.blacklist.bypass"))
                throw new Exception("Nick blacklisted");
        }

        user.set("nick", n);
        save();
        base.setCustomNameVisible(true);
        base.setCustomName(new LiteralText(n));
        //if (Configs.MAIN.getBoolean("change-playerlist", true))
        //    base.setPlayerListName(base.getDisplayName());
    }

    public boolean isNPC() {
        return false; // TODO: should we have NPC support?
    }

    public void setHome(String home, Location l) {
        user.set("homes." + home + ".world", l.world);
        user.set("homes." + home + ".x", l.x);
        user.set("homes." + home + ".y", l.y);
        user.set("homes." + home + ".z", l.z);
        save();
    }

    public void delHome(String home) {
        user.set("homes." + home + ".world", null);
        user.set("homes." + home + ".x", null);
        user.set("homes." + home + ".y", null);
        user.set("homes." + home + ".z", null);
        user.set("homes." + home, null);
        save();
    }

    public void sendMessage(String txt) {
        base.sendMessage(new LiteralText(Bssentials.translateAlternateColorCodes('&', txt)), false);
    }

    public Set<String> getHomes() {
        ConfigurationSection section = user.getConfigurationSection("homes");
        return section.getValues(false).keySet();
    }

    public UUID getUniqueId() {
        return base.getUuid();
    }

    public String getName(boolean custom) {
        return custom ? base.getCustomName().asString() : base.getName().asString();
    }

    public Location getLocation() {
        return BssentialsMod.copy(base, base.getBlockPos());
    }

    @Override
    public Location getTargetBlock(int maxDistance) {
        return BssentialsMod.copy(base, base.getBlockPos().add(1, 1, 1));
    }

    public void clearInventory(String item) {
        if (null == item) {
            base.inventory.clear();
        }// TODO else base.inventory.remove(Material.valueOf(item));
    }

    public boolean isOnline() {
        return !base.isDisconnected();
    }

    public void openEnderchest(User target) {
        ((ServerPlayerEntity)target.getBase()).getEnderChestInventory().onOpen(base);
    }

    public int getExpLevel() {
        return base.experienceLevel;
    }

    public void setExpLevel(int i) {
        base.setExperienceLevel(i);
    }

    public void setAllowFly(boolean allow) {
        base.abilities.allowFlying = allow;
        base.abilities.flying = allow;
        base.sendAbilitiesUpdate();
    }

    public boolean isAllowedToFly() {
        return base.canFly();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setGameMode(int i) {
        GameMode mode = GameMode.byId(i);
        base.setGameMode(mode);
    }

    @SuppressWarnings("deprecation")
    public int getItemInMainHand() {
        return Item.getRawId(base.inventory.getMainHandStack().getItem());
    }

    public void setHelmentToMainHandItem() {
        // TODO
    }

    public void setHealthAndFoodLevel(int h, int f) {
        if (h != -1) base.setHealth(h);
        if (f != -1) base.getHungerManager().setFoodLevel(f);
    }

    public boolean isOp() {
        return base.hasPermissionLevel(1);
    }

    public boolean teleport(Location l) {
        base.teleport(l.x, l.y, l.z);
        return true;
    }

    public void openOtherUserInventory(User target) {
       // TODO: Check this
       // base.openHandledScreen(((ServerPlayerEntity)target.getBase()).playerScreenHandler);
    }

    @Override
    public Object getBase() {
        return base;
    }

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public IWorld getWorld() {
        return Bssentials.getInstance().getWorld(((ServerWorldProperties)base.getServerWorld().getLevelProperties()).getLevelName());
    }

    @Override
    public int getPing() {
        return base.pingMilliseconds;
    }

}
