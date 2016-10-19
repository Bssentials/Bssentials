package ml.bssentials.api;

import ml.bssentials.main.Bssentials;

public class WarpAPI {
	public static Bssentials main;
	
    @Deprecated
    public static void createWarp(Player p, String warpname) {
        Bssentials.createWarp(p,warpname);
    }
    
    @Deprecated
    public static void createHome(Player p) {
        Bssentials.createHome(p);
    }
    
    @Deprecated
    public static void delHome(Player p) {
    	Bssentials.delHome(p);
    }
}