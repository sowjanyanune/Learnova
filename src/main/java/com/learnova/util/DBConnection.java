
package com.learnova.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection {

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();

        try (InputStream input = DBConnection.class.getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input == null) {
                throw new SQLException("database.properties file not found");
            }

            properties.load(input);

            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );

        } catch (Exception e) {
            throw new SQLException("Database connection failed: " + e.getMessage(), e);
        }
    }
}
