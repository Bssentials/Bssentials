package com.earth2me.essentials.chat;

//import net.ess3.api.IEssentials;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//import static com.earth2me.essentials.I18n.tl;


public class EssentialsChat extends JavaPlugin {
    private static final Logger LOGGER = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        final PluginManager pluginManager = getServer().getPluginManager();
        // final IEssentials ess = (IEssentials)
        // pluginManager.getPlugin("Essentials");
        // if
        // (!this.getDescription().getVersion().equals(ess.getDescription().getVersion()))
        // {
        // LOGGER.log(Level.WARNING, tl("versionMismatchAll"));
        // }
        // if (!ess.isEnabled()) {
        // this.setEnabled(false);
        // return;
        // }

        final Map<AsyncPlayerChatEvent, ChatStore> chatStore = Collections.synchronizedMap(new HashMap<AsyncPlayerChatEvent, ChatStore>());

        final EssentialsChatPlayerListenerLowest playerListenerLowest = new EssentialsChatPlayerListenerLowest(
                getServer(), null, chatStore);
        final EssentialsChatPlayerListenerNormal playerListenerNormal = new EssentialsChatPlayerListenerNormal(
                getServer(), null, chatStore);
        final EssentialsChatPlayerListenerHighest playerListenerHighest = new EssentialsChatPlayerListenerHighest(
                getServer(), null, chatStore);
        pluginManager.registerEvents(playerListenerLowest, this);
        pluginManager.registerEvents(playerListenerNormal, this);
        pluginManager.registerEvents(playerListenerHighest, this);

    }
}
