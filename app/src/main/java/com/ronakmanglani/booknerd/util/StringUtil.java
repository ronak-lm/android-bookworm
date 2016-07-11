package com.ronakmanglani.booknerd.util;

public class StringUtil {

    // Private constructor to prevent instantiation
    private StringUtil() {
    }

    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.length() == 0 ||
                s.toLowerCase().equals("null") ||
                s.toLowerCase().equals("none"));
    }

    public static String toTitleCase(String s) {
        final String ACTIONABLE_DELIMITERS = " -/";

        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        char charArray[] = s.toCharArray();
        for (char c : charArray) {
            c = (capNext)
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0);
        }
        return sb.toString();
    }
}
