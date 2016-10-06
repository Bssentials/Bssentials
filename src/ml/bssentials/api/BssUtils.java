package ml.bssentials.api;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import io.github.ramidzkh.utils.PlayerCheck;

public class BssUtils {

	/**
	 * Checks if the player has permission for the command!
	 * 
	 * @author Bssentials
	 * */
	public static boolean hasPermForCommand(Player player, String command) {
		Player p = player;
		return (player.isOp() | PlayerCheck.hasPerm(p, "bssentials.command"+command) | PlayerCheck.hasPerm(p, "essentials."+command) | PlayerCheck.hasPerm(p, "accentials.command"+command) | PlayerCheck.hasPerm(p, "dssentials.command"+command) | PlayerCheck.hasPerm(p, "bssentials.command.*"));
	}
	
	/**
	 * Send the no permission message to the player!
	 * 
	 * @author Bssentials
	 * */
	public static void noPermMsg(Player p) {
		p.sendMessage("No permisson!");
	}
	
	/**
	 * Broadcast an message to the server
	 **/
	public static void broadcastMessage(Object message) {
		Bukkit.broadcastMessage(message);
	}

}
