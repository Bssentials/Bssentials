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
		return (player.isOp() | player.hasPermission(perm));
	}
}
