package com.earth2me.essentials;

/**
 * TODO: Add language support. ~ Bssentials
 */
public class I18n {
    public I18n(Essentials essentials) {
        // TODO Auto-generated constructor stub
    }

    public static String tl(Object... s) {
        String strs = "[EssBridge]: ";
        for (Object z : s)
            strs += String.valueOf(z);
        return strs;
    }

    public void onDisable() {
        Essentials.fixme("com.earth2me.essentials.I18n#onDisable");
    }

    public void onEnable() {
        Essentials.fixme("com.earth2me.essentials.I18n#onEnable");
    }

    public void updateLocale(String s) {
        Essentials.fixme("com.earth2me.essentials.I18n#updateLocale");
    }
}