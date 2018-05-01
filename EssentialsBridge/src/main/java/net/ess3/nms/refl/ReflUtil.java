package net.ess3.nms.refl;

public class ReflUtil {
    private static ReflUtil ref = new ReflUtil();
    public static final String V1_11_R1 = null;
    public static final String V1_8_R1 = null;
    public static final Object V1_9_R1 = null;

    public static ReflUtil getNmsVersionObject() {
        // TODO Auto-generated method stub
        return ref;
    }

    public static boolean isLowerThan(Object o) {
        return false; // TODO
    }

    public boolean isHigherThanOrEqualTo(String v18r1) {
        return true;
    }
}