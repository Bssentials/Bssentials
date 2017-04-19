package ml.bssentials.listeners;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ml.bssentials.main.Bssentials;

public class ChatLis implements Listener {
    private Bssentials main;

    public ChatLis(Bssentials bs) {
        this.main = bs;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (main.ranks.getBoolean("ranks.enable") != false) {
            String rankname;
            if (main.ranks.getString("playerdata." + player.getName() + ".rank") != null) rankname = main.getConfig().getString("playerdata." + player.getName() + ".rank");
            else {
                rankname = "default";
                if (main.ranks.getString("ranks." + rankname + ".prefix") == null) main.ranks.set("ranks."+rankname+".prefix", "&7[Default]&f");
            }
            String rank = main.ranks.getString("ranks." + rankname + ".prefix");

            String format = main.ranks.getString("ranks.format");
            if (format == null) {
                format = "%rank% %s : %s";
                main.ranks.set("ranks.format", "%rank% %s : %s");
            }

            event.setFormat(format.replaceAll("%rank%", ChatColor.translateAlternateColorCodes('&', rank)));
        }

        for (String word : main.getConfig().getStringList("antiswear"))
            if (Bukkit.getServer().getPluginManager().getPlugin("BSwear") == null) if (event.getMessage().equalsIgnoreCase(word)) {
                event.setMessage(event.getMessage().replaceAll(word, StringUtils.repeat("*", word.length())));
                player.sendMessage(ChatColor.RED + "[Bssentials] The word: "+word+" is blocked!");
            }
    }
}
