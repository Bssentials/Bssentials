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
		if (main.getConfig().getBoolean("ranks.enable") != false) {
			Player player = event.getPlayer();
			String rankname;
			if (main.getConfig().getString("playerdata." + player.getName() + ".rank") != null) {
				rankname = main.getConfig().getString("playerdata." + player.getName() + ".rank");
			} else {
				rankname = "default";
				if (main.getConfig().getString("ranks." + rankname + ".prefix") == null) {
					main.getConfig().set("ranks."+rankname+".prefix", "&7[Default]&f");
				}
			}
			String rank = main.getConfig().getString("ranks." + rankname + ".prefix");
      
			//String format = "%rank% %s : %s";
			String format = main.getConfig().getString("ranks.format");
			if (format == null) {
				main.getConfig().set("ranks.format", "%rank% %s : %s");
			}
        
			event.setFormat(format.replaceAll("%rank%", ChatColor.translateAlternateColorCodes('&', rank)));
		}
    }
}
