package ml.bssentials.api;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ml.bssentials.main.Bssentials;

public class ChatAPI {
	public static Bssentials main;
	
	public ChatAPI(Bssentials bss) {
            main = bss;
	}
	
	/**
	 * Sets an player's display name.
	 * 
	 * @author Isaiah Patton
	 * */
	public static void nickName(Player player, String name) {
	   main.getConfig().set("playerdata." + player.getName() + ".nick", name);
           String thenickname = main.getConfig().getString("playerdata." + player.getName() + ".nick");
           thenickname = ChatColor.translateAlternateColorCodes('&', thenickname);
           player.setDisplayName(thenickname);
           main.saveConfig();
   }
}
