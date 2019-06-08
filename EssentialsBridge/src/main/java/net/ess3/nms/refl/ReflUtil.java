package net.ess3.nms.refl;

import org.bukkit.Bukkit;

public class ReflUtil {

    public static final NMSVersion V1_8_R1 = NMSVersion.fromString("v1_8_R1");
    public static final NMSVersion V1_8_R2 = NMSVersion.fromString("v1_8_R2");
    public static final NMSVersion V1_8_R3 = NMSVersion.fromString("v1_8_R3");
    public static final NMSVersion V1_9_R1 = NMSVersion.fromString("v1_9_R1");
    public static final NMSVersion V1_10_R1 = NMSVersion.fromString("v1_10_R1");
    public static final NMSVersion V1_11_R1 = NMSVersion.fromString("v1_11_R1");
    public static final NMSVersion V1_12_R1 = NMSVersion.fromString("v1_12_R1");
    public static final NMSVersion V1_13_R1 = NMSVersion.fromString("v1_13_R1");
    public static final NMSVersion V1_13_R2 = NMSVersion.fromString("v1_13_R2");

    private static NMSVersion nmsVersionObject;
    private static String nmsVersion;

    public static String getNMSVersion() {
        if (nmsVersion == null) {
            String name = Bukkit.getServer().getClass().getName();
            String[] parts = name.split("\\.");
            nmsVersion = parts[3];
        }
        return nmsVersion;
    }

    public static NMSVersion getNmsVersionObject() {
        return nmsVersionObject == null ? (nmsVersionObject = NMSVersion.fromString(getNMSVersion())) : nmsVersionObject ;
    }

    public static boolean isLowerThan(NMSVersion n) {
        return false;
    }

    public boolean isHigherThanOrEqualTo(NMSVersion n) {
        return true;
    }

    public static class NMSVersion implements Comparable<NMSVersion> {
        private final int major, minor, release;
        
        public static NMSVersion fromString(String s) {
            String[] strs = s.split("_");
            return new NMSVersion(Integer.valueOf(strs[0].substring(1)), Integer.valueOf(strs[1]), Integer.valueOf(strs[2].substring(1)));
        }

        private NMSVersion(int major, int minor, int release) {
            this.major = major;
            this.minor = minor;
            this.release = release;
        }

        public boolean isHigherThan(NMSVersion o) {
            return compareTo(o) > 0;
        }

        public boolean isHigherThanOrEqualTo(NMSVersion o) {
            return compareTo(o) >= 0;
        }

        public boolean isLowerThan(NMSVersion o) {
            return compareTo(o) < 0;
        }

        public boolean isLowerThanOrEqualTo(NMSVersion o) {
            return compareTo(o) <= 0;
        }

        public int getMajor() {
            return major;
        }

        public int getMinor() {
            return minor;
        }

        public int getRelease() {
            return release;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (o == null || getClass() != o.getClass())
                return false;

            NMSVersion that = (NMSVersion) o;
            return major == that.major && minor == that.minor && release == that.release;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "v" + major + "_" + minor  + "_R" + release;
        }

        @Override
        public int compareTo(NMSVersion o) {
            if (major < o.major) {
                return -1;
            } else if (major > o.major) {
                return 1;
            } else { // equal major
                if (minor < o.minor) {
                    return -1;
                } else if (minor > o.minor)
                    return 1;
                else return Integer.compare(release, o.release);
            }
        }
    }

}