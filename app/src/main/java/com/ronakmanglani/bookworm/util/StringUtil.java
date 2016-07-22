package com.ronakmanglani.bookworm.util;

public class StringUtil {

    private StringUtil() { }

    // Check if string is empty or null
    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.length() == 0 ||
                s.toLowerCase().equals("null") ||
                s.toLowerCase().equals("none"));
    }

    // Convert string to title case
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

    // Remove all special characters and spaces from string
    public static String cleanText(String s) {
        return s.toLowerCase().trim().replaceAll("[^A-Za-z0-9]", "");
    }
}
