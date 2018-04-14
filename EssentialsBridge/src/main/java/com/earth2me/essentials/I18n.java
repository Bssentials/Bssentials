package com.earth2me.essentials;

/**
 * TODO: Add language support. ~ Bssentials
 */
public class I18n {
    public static String tl(String... s) {
        String strs = "[EssBridge]: ";
        for (String z : s)
            strs += z;
        return strs;
    }
}