package io.github.ramidzkh.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class PlayerCheck {

	public static boolean isPlayer(CommandSender sender){
		return (sender instanceof Player);
	}
	
	public static boolean isNotPlayer(CommandSender sender){
		return (!isPlayer(sender));
	}
	
	/**
	 * Checks if the <code>{@link Player}</code> has operator status or has the <code>{@link Permission}</code>.
	 * 
	 * @param player The player to check {@link Player}.
	 * @param perm The permission the player should have. {@link Permission} as the permission.
	 * @return <b>{@link Boolean}</b> If the player is an operator or has the permission; either <code>true</code> or <code>false</code>.
	 */
	public static boolean hasPerm(Player player, Permission perm) {
		return (player.isOp() | player.hasPermission(perm));
	}
	
	/**
	 * Checks if the <code>{@link Player}</code> has operator status or has the <code>{@link Permission}</code>.
	 * 
	 * @param player The player to check {@linkplain Player}.
	 * @param perm The permission the player should have. {@link String} as the permission.
	 * @return <b>{@link Boolean}</b> If the player is an operator or has the permission; either <code>true</code> or <code>false</code>.
	 */
	public static boolean hasPerm(Player player, String perm) {
		String command = perm.replace("bssentials.command.", "");
		//return (player.isOp() | player.hasPermission(perm));
		return (hasPermForCommand(player, command));
	}
	public static boolean hasPermForCommand(Player player, String command) {
		// If the server owner is switching from Essentials, EssentialsX, Accentials, Dssentials, they woun't have to change there permissions!
		return (player.isOp() | player.hasPermission("bssentials.command"+command) | player.hasPermission("essentials."+command) | player.hasPermission("accentials.command"+command) | player.hasPermission("dssentials.command"+command) | player.hasPermission("bssentials.command.*""));
	}
}
