package bssentials;

import java.io.File;

import bssentials.api.IBssentials;

public class Bssentials {

    private static IBssentials i;
    public static File warpdir;

    public static File DATA_FOLDER;

    public static IBssentials getInstance() {
        return i;
    }

    public static void setInstance(IBssentials bss, File dataFolder) {
        if (null == i) {
            Bssentials.i = bss;
            Bssentials.DATA_FOLDER = dataFolder;
        }
    }

    // Below copied from Bukkit
    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i+1]) > -1) {
                b[i] = '\u00A7';
                b[i+1] = Character.toLowerCase(b[i+1]);
            }
        }
        return new String(b);
    }

}
