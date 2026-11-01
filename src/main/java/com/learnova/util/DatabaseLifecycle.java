package com.learnova.util;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public final class DatabaseLifecycle implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        AbandonedConnectionCleanupThread.checkedShutdown();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        ClassLoader loader = getClass().getClassLoader();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == loader) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException exception) {
                    event.getServletContext().log("Unable to release JDBC driver", exception);
                }
            }
        }
    }
}
