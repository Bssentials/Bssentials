package com.earth2me.essentials.antibuild;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;


public class EssentialsConnect {
    private static final Logger LOGGER = Logger.getLogger("Minecraft");
    //private final transient IEssentials ess;
    private final transient IAntiBuild protect;

    public EssentialsConnect(Plugin essPlugin, Plugin essProtect) {
        //ess = essPlugin;
        protect = (IAntiBuild) essProtect;
        AntiBuildReloader pr = new AntiBuildReloader();
        pr.reloadConfig();
        //ess.addReloadListener(pr);
    }

    public void onDisable() {
    }

    /*public IEssentials getEssentials() {
        return ess;
    }*/

    public void alert(final Player user, final String item, final String type) {
        final Location loc = user.getLocation();
        final String warnMessage = "TODO";/*tl("alertFormat", user.getName(), type, item, loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());*/
        LOGGER.log(Level.WARNING, warnMessage);
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.hasPermission("essentials.protect.alerts")) {
                p.sendMessage(warnMessage);
            }
        }
    }


    private class AntiBuildReloader /*implements IConf*/ {
        // @Override
        public void reloadConfig() {
            /*for (AntiBuildConfig protectConfig : AntiBuildConfig.values()) {
                if (protectConfig.isList()) {
                    protect.getSettingsList().put(protectConfig, ess.getSettings().getProtectList(protectConfig.getConfigName()));
                } else {
                    protect.getSettingsBoolean().put(protectConfig, ess.getSettings().getProtectBoolean(protectConfig.getConfigName(), protectConfig.getDefaultValueBoolean()));
                }

            }*/

        }
    }
}
