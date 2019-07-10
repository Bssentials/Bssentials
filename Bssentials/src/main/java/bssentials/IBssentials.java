package bssentials;

import org.bukkit.entity.Player;

public interface IBssentials {

    public Warps getWarps();

    public boolean teleportPlayerToWarp(Player plr, String warpname);

}