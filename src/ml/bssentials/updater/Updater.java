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
	
	private Logger bsLogger;
	private Bssentials main;
	private String ver;
	private PluginDescriptionFile pdfFile;
	
    public Updater(Bssentials bs) {
    	this.bsLogger = bs.getLogger();
    	this.main = bs;
    	pdfFile = main.getDescription();
    	this.ver = pdfFile.getVersion();
    }
    
	public void checkForUpdate() {
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
        			if (main.getConfig().getBoolean("CheckForUpdate") == true) {
        				bsLogger.info("======= Bssentials v2 ========");
        				bsLogger.info("     Update found: " + Update);
        				bsLogger.info("==============================");
        			}
        		}
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
