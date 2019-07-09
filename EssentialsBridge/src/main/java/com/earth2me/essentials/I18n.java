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
        Essentials.fixme(I18n.class, "onDisable");
    }

    public void onEnable() {
        Essentials.fixme(I18n.class, "onEnable");
    }

    public void updateLocale(String s) {
        Essentials.fixme(I18n.class, "updateLocale");
    }

}