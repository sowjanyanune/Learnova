package com.learnova.servlet;

import com.learnova.util.DBConnection;
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

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("appName", getServletContext().getInitParameter("appName"));
        try (Connection connection = DBConnection.getConnection()) {
            request.setAttribute("totalQuestions", count(connection, "SELECT COUNT(*) FROM missions"));
            request.setAttribute("totalStudents", count(connection, "SELECT COUNT(*) FROM users"));
            request.setAttribute("totalAttempts", count(connection, "SELECT COUNT(*) FROM mission_submissions"));
        } catch (SQLException exception) {
            getServletContext().log("Admin dashboard database error", exception);
            request.setAttribute("error", "Dashboard statistics are temporarily unavailable.");
        }
        request.getRequestDispatcher("/admin-dashboard.jsp").forward(request, response);
    }

    private int count(Connection connection, String query) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rows = statement.executeQuery()) {
            return rows.next() ? rows.getInt(1) : 0;
        }
    }
}
