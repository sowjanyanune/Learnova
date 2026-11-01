package com.learnova.servlet;

import com.learnova.util.DBConnection;
import com.learnova.util.PasswordUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = trim(request.getParameter("username"));
        String email = trim(request.getParameter("email"));
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        if (!username.matches("[A-Za-z0-9_]{3,50}")) {
            fail(request, response, "Username must be 3-50 characters using letters, numbers or underscores.");
            return;
        }
        if (email.length() > 100 || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            fail(request, response, "Enter a valid email address (maximum 100 characters).");
            return;
        }
        if (password == null || password.length() < 8 || password.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72) {
            fail(request, response, "Password must have at least 8 characters and at most 72 UTF-8 bytes.");
            return;
        }
        if (!password.equals(confirm)) {
            fail(request, response, "Passwords do not match.");
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            try (PreparedStatement check = connection.prepareStatement(
                    "SELECT id FROM users WHERE username = ? OR email = ?")) {
                check.setString(1, username);
                check.setString(2, email);
                try (ResultSet rows = check.executeQuery()) {
                    if (rows.next()) {
                        fail(request, response, "Username or email already exists.");
                        return;
                    }
                }
            }
            try (PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO users (username, email, password, total_xp, player_level) VALUES (?, ?, ?, 0, 1)")) {
                insert.setString(1, username);
                insert.setString(2, email);
                insert.setString(3, PasswordUtil.hash(password));
                insert.executeUpdate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp?registered=1");
        } catch (SQLException exception) {
            if (exception.getErrorCode() == 1062) {
                fail(request, response, "Username or email already exists.");
            } else {
                getServletContext().log("Registration database error", exception);
                fail(request, response, "Registration is temporarily unavailable. Verify the database configuration.");
            }
        }
    }

    private String trim(String input) {
        return input == null ? "" : input.trim();
    }

    private void fail(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
