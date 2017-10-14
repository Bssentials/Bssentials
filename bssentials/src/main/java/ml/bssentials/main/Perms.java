package ml.bssentials.main;

import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

/**
 * @author ramidzkh
 * @author Isaiah Patton
 */
public enum Perms {
    STAFFLIST("staff"), STAFFADD("staff.add"), STAFFREMOVE("staff.remove"),
    HEAL_OUTHER("heal.outher"), FEED_OUTHER("feed_outher"),
    SETWARP_OR("setwarp.or"),
    WARP_OTHERS("warp.outhers"),
    INVISABLE("god"),
    PLUGIN_INFO("bssentials"),

    GAMEMODE, INVSEE, SETWARP, SETRULES, SPAWNMOB, HEAL, FEED, FLY, WELCOME, WARP,
    GOOGLE, WIKI("mcwiki"), YOUTUBE, BUKKIT, PLUGINS, PM, SETSPAWN, BROADCAST, NUKE;

    public final Permission permission;

    private Perms() {
        this.permission = new Permission("bssentials.command." + this.toString().toLowerCase(Locale.ENGLISH));
        this.permission.addParent(Bssentials.ALL_PERM, true);
        Bukkit.getServer().getPluginManager().addPermission(this.permission);
    }

    private Perms(String perm) {
        this.permission = new Permission("bssentials.command." + perm);
        this.permission.addParent(Bssentials.ALL_PERM, true);
        Bukkit.getServer().getPluginManager().addPermission(this.permission);
    }
}