package org.bh.utility;

public class MapperHelper {
    public static String nullSafeTrim(String toTrim) {
        if (toTrim == null) {
            return null;
        }
        return toTrim.trim();
    }
}
