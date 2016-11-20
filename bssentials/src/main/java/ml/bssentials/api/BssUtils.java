package ml.bssentials.api;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;

import io.github.ramidzkh.utils.PlayerCheck;

public class BssUtils {

	/**
	 * Checks if the player has permission for the command!
	 * 
	 * @author Bssentials
	 * */
	public static boolean hasPermForCommand(Player player, String command) {
		Player p = player;
		return (player.isOp() | PlayerCheck.hasPerm(p, "bssentials.command"+command) | PlayerCheck.hasPerm(p, "essentials."+command) | PlayerCheck.hasPerm(p, "accentials.command"+command) | PlayerCheck.hasPerm(p, "dssentials.command"+command) | PlayerCheck.hasPerm(p, "bssentials.command.*") | PlayerCheck.hasPerm(p, "se."+command));
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
	 * Send the no permission message to the player!
	 * 
	 * @author Bssentials
	 */
	public static void noPermMsg(Player p, Command c) {
		p.sendMessage("You don't have permission: bssentials.command." + c.getName().toLowerCase());
	}
    
	
	/**
	 * Broadcast an message to the server
	 **/
	public static void broadcastMessage(String message) {
		Bukkit.broadcastMessage(message);
	}
	
	/**
	 * Broadcast an message to the server
	 **/
	public static void broadcastMessage(Object message) {
		broadcastMessage(message.toString());
	}

}
