package com.earth2me.essentials.protect;

//import com.earth2me.essentials.IConf;
//import net.ess3.api.IEssentials;
import org.bukkit.plugin.Plugin;

//import java.util.logging.Level;
//import java.util.logging.Logger;

//import static com.earth2me.essentials.I18n.tl;


public class EssentialsConnect {
    // private static final Logger LOGGER = Logger.getLogger("Minecraft");
    public EssentialsConnect(Plugin essPlugin, Plugin essProtect) {
        // if
        // (!essProtect.getDescription().getVersion().equals(essPlugin.getDescription().getVersion()))
        // {
        // LOGGER.log(Level.WARNING, tl("versionMismatchAll"));
        // }
        new ProtectReloader();
    }

    // public IEssentials getEssentials() {
    // return ess;
    // }

    private class ProtectReloader /* implements IConf */ {
        // @Override
        // public void reloadConfig() {
        // for (ProtectConfig protectConfig : ProtectConfig.values()) {
        // if (protectConfig.isList()) {
        // protect.getSettingsList().put(protectConfig,
        // ess.getSettings().getProtectList(protectConfig.getConfigName()));
        // } else if (protectConfig.isString()) {
        // protect.getSettingsString().put(protectConfig,
        // ess.getSettings().getProtectString(protectConfig.getConfigName()));
        // } else {
        // protect.getSettingsBoolean().put(protectConfig,
        // ess.getSettings().getProtectBoolean(protectConfig.getConfigName(),
        // protectConfig.getDefaultValueBoolean()));
        // }
        // }
        // }
    }
}