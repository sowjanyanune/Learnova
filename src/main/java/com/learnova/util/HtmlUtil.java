package com.learnova.util;

public final class HtmlUtil {
    private HtmlUtil() { }

    public static String escape(Object value) {
        if (value == null) {
            return "";
        }
        String text = String.valueOf(value);
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
