package com.learnova.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
    private PasswordUtil() { }

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean verify(String password, String storedHash) {
        if (password == null || password.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72 || storedHash == null || !storedHash.startsWith("$2")) {
            return false;
        }
        try {
            return BCrypt.checkpw(password, storedHash);
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
