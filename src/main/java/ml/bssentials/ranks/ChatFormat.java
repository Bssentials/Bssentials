package ml.bssentials.ranks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ml.bssentials.main.Bssentials;

public class ChatFormat implements Listener {
	private Bssentials main;
	
	public ChatFormat(Bssentials bs) {
    	    this.main = bs;
        }
	
	@EventHandler
        public void onChat(AsyncPlayerChatEvent event) {
		if (main.getRankConfig().getBoolean("ranks.enable") != false) {
			Player player = event.getPlayer();
			String rankname;
			if (main.getRankConfig().getString("playerdata." + player.getName() + ".rank") != null) {
				rankname = main.getConfig().getString("playerdata." + player.getName() + ".rank");
			} else {
				rankname = "default";
				if (main.getRankConfig().getString("ranks." + rankname + ".prefix") == null) {
					main.getRankConfig().set("ranks."+rankname+".prefix", "&7[Default]&f");
				}
			}
			String rank = main.getRankConfig().getString("ranks." + rankname + ".prefix");

			String format = main.getRankConfig().getString("ranks.format");
			if (format == null) {
				main.getRankConfig().set("ranks.format", "%rank% %s : %s");
			}
        
			event.setFormat(format.replaceAll("%rank%", ChatColor.translateAlternateColorCodes('&', rank)));
		}
    }
}
