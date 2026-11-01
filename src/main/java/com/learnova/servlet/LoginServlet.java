package com.learnova.servlet;

import com.learnova.util.DBConnection;
import com.learnova.util.PasswordUtil;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String identity = request.getParameter("identity");
        String password = request.getParameter("password");
        if (identity == null || identity.isBlank() || password == null || password.isEmpty()) {
            fail(request, response, "Enter your username or email and password.");
            return;
        }
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, username, password FROM users WHERE username = ? OR email = ?")) {
            statement.setString(1, identity.trim());
            statement.setString(2, identity.trim());
            try (ResultSet rows = statement.executeQuery()) {
                if (!rows.next() || !PasswordUtil.verify(password, rows.getString("password"))) {
                    fail(request, response, "Invalid username/email or password.");
                    return;
                }
                HttpSession old = request.getSession(false);
                if (old != null) {
                    old.invalidate();
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", rows.getInt("id"));
                session.setAttribute("username", rows.getString("username"));
                session.setMaxInactiveInterval(1800);
                Cookie lastUsername = new Cookie("learnova_last_username",
                        URLEncoder.encode(rows.getString("username"), StandardCharsets.UTF_8));
                lastUsername.setMaxAge(7 * 24 * 60 * 60);
                lastUsername.setHttpOnly(true);
                lastUsername.setSecure(request.isSecure());
                lastUsername.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
                response.addCookie(lastUsername);
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
        } catch (SQLException exception) {
            getServletContext().log("Student login database error", exception);
            fail(request, response, "Login is temporarily unavailable. Check database connectivity.");
        }
    }

    private void fail(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
