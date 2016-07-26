package io.github.isaiah.updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import io.github.isaiah.bssentials.Bssentials;

/**
 * Sends a message to the console if there is an update.
 * 
 * @author Isaiah Patton
 * @author ramidzkh
 */
public class Updater {
	
	private Logger bsLogger;
	
    public Updater(Bssentials bs) {this.bsLogger = bs.getLogger();}
    
    public void checkForUpdate() {
        String ver = "2.0";
        URL checkVerURL;
		try {
			checkVerURL = new URL("https://raw.githubusercontent.com/IsaiahPatton/bssentials/master/updater.txt");
		
			BufferedReader UpdateFile = new BufferedReader(new InputStreamReader(checkVerURL.openStream()));
        	String Update = UpdateFile.readLine();
        	UpdateFile.close(); 
        	if(Update > ver) {
            	JLog("========= Bssentials =========");
            	JLog("     Update found: " + Update);
            	JLog("==============================");
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void JLog(String text) {
        bsLogger.info(text);
    }
}
