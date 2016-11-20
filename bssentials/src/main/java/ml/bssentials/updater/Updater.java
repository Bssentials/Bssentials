package ml.bssentials.updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;

import ml.bssentials.main.Bssentials;

/**
 * Sends a message to the console if there is an update.
 * 
 * @author Isaiah Patton
 * @author ramidzkh
 */
public class Updater {
	
	private static Logger bsLogger;
	private static Bssentials main;
	private static String ver;
	private static PluginDescriptionFile pdfFile;
	
    public Updater(Bssentials bs) {
    	Updater.bsLogger = bs.getLogger();
    	Updater.main = bs;
    	Updater.pdfFile = main.getDescription();
    	Updater.ver = pdfFile.getVersion();
    }
    
	public static void checkForUpdate() {
		ver = pdfFile.getVersion();
        URL checkVerURL;
		try {
			checkVerURL = new URL("https://raw.githubusercontent.com/bssentials/bssentials-2/master/LastPublicVer.txt");
		
			BufferedReader UpdateFile = new BufferedReader(new InputStreamReader(checkVerURL.openStream()));
        	String Update = UpdateFile.readLine();
        	UpdateFile.close();
        	if(Update != ver) {
        		if (Update.contains("1.")) {
        			//Should not happen, Bssentials's version is v2.x not v1.x
        		} else {
        			double ver2 = Double.valueOf(ver.replaceAll("[^A-Za-z]", ""));
        			double Update2 = Double.valueOf(Update);
        			
        			if (main.getConfig().getBoolean("checkForUpdate") == true) {
        				if (Update2 > ver2) {
        					bsLogger.info(" ");
        					bsLogger.info("======= Bssentials v2 ========");
        					bsLogger.info("     Update found: " + Update);
        					bsLogger.info("==============================");
        					bsLogger.info(" ");
        				} else {
        					if (ver.contains("dev")) {
        						bsLogger.info("You are using an dev build of Bssentials!");
        						bsLogger.info("Dev builds might contain bugs!");
        					} else {
        						bsLogger.info("Oh no.. Our updater will not work! Don't worry!");
        					}
        				}
        			} else {
        				bsLogger.info("The update checker is disabled");
        			}
        		}
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
