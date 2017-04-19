package ml.bssentials.main;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

/**
 * @author ramidzkh
 * @author Isaiah Patton
 */
public enum Perms {
    GAMEMODE("gamemode"),
    STAFFLIST("staff"),
    STAFFADD("staff.add"),
    STAFFREMOVE("staff.remove"),
    INVSEE("invsee"),
    SETWARP("setwarp"),
    SETWARP_OR("setwarp.or"),
    SETRULES("setrules"),
    SPAWNMOB("spawnmob"),
    HEAL("heal"),
    HEAL_OUTHER("heal.outher"),
    FEED("feed"),
    FEED_OUTHER("feed_outher"),
    FLY("fly"),
    WELCOME("welcome"),
    WARP("warp"),
    WARP_OTHERS("warp.outhers"),
    GOOGLE("google"),
    WIKI("mcwiki"),
    YOUTUBE("youtube"),
    BUKKIT("bukkit"),
    PLUGINS("plugins"),
    PM("pm"),
    INVISABLE("god"),
    PLUGIN_INFO("bssentials"),
    SETSPAWN("setspawn"),
    BROADCAST("broadcast"),
    NUKE("nuke");

    public final Permission permission;

    private Perms(String perm) {
        this.permission = new Permission("bssentials.command." + perm);
        this.permission.addParent(Bssentials.ALL_PERM, true);
        Bukkit.getServer().getPluginManager().addPermission(this.permission);
    }
}