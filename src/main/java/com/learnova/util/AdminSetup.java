package com.learnova.util;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

public final class AdminSetup {
    private AdminSetup() { }

    public static void main(String[] args) throws Exception {
        Console console = System.console();
        if (console == null) {
            throw new IllegalStateException("Run this command in Windows Terminal, not the Eclipse console.");
        }
        String username = console.readLine("New administrator username: ").trim();
        if (!username.matches("[A-Za-z0-9_]{3,50}")) {
            throw new IllegalArgumentException("Use 3-50 letters, numbers or underscores.");
        }
        char[] password = console.readPassword("New administrator password: ");
        char[] confirmation = console.readPassword("Confirm password: ");
        try {
            String secret = new String(password);
            if (secret.length() < 12 || secret.getBytes(StandardCharsets.UTF_8).length > 72
                    || !Arrays.equals(password, confirmation)) {
                throw new IllegalArgumentException("Use at least 12 characters, at most 72 UTF-8 bytes, and matching passwords.");
            }
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO admins (username, password) VALUES (?, ?)")) {
                statement.setString(1, username);
                statement.setString(2, PasswordUtil.hash(secret));
                statement.executeUpdate();
            }
            console.printf("Administrator created. Existing accounts were not changed.%n");
        } finally {
            Arrays.fill(password, '\0');
            Arrays.fill(confirmation, '\0');
        }
    }
}
