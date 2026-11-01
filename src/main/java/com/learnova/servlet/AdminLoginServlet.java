package com.learnova.servlet;

import com.learnova.util.DBConnection;
import com.learnova.util.PasswordUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(value = "/admin-login", initParams = @WebInitParam(name = "role", value = "administrator"))
public class AdminLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.isBlank() || password == null || password.isEmpty()) {
            fail(request, response, "Enter your admin username and password.");
            return;
        }
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, username, password FROM admins WHERE username = ?")) {
            statement.setString(1, username.trim());
            try (ResultSet rows = statement.executeQuery()) {
                if (!rows.next() || !PasswordUtil.verify(password, rows.getString("password"))) {
                    fail(request, response, "Invalid administrator credentials.");
                    return;
                }
                HttpSession old = request.getSession(false);
                if (old != null) {
                    old.invalidate();
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("adminId", rows.getInt("id"));
                session.setAttribute("adminName", rows.getString("username"));
                session.setAttribute("adminRole", getServletConfig().getInitParameter("role"));
                session.setMaxInactiveInterval(1800);
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            }
        } catch (SQLException exception) {
            getServletContext().log("Admin login database error", exception);
            fail(request, response, "Admin login is temporarily unavailable. Check database connectivity.");
        }
    }

    private void fail(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/admin-login.jsp").forward(request, response);
    }
}
