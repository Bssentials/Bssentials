package bssentials.utils;

public class StringUtils {

    public static String join(String[] strs, String seperator) {
        String str = "";
        for (String s : strs)
            str += s + seperator;
        return str;
    }

}