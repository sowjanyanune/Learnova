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

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = (Integer) request.getSession(false).getAttribute("userId");
        String sql = "SELECT u.username, u.total_xp, u.player_level, "
                + "(SELECT COUNT(*) FROM mission_submissions s WHERE s.user_id = u.id AND s.is_correct = TRUE) AS completed, "
                + "(SELECT COUNT(*) FROM mission_submissions s WHERE s.user_id = u.id) AS attempted, "
                + "(SELECT COUNT(*) FROM missions) AS available "
                + "FROM users u WHERE u.id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rows = statement.executeQuery()) {
                if (!rows.next()) {
                    request.getSession(false).invalidate();
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    return;
                }
                int xp = rows.getInt("total_xp");
                request.setAttribute("username", rows.getString("username"));
                request.setAttribute("totalXp", xp);
                request.setAttribute("playerLevel", rows.getInt("player_level"));
                request.setAttribute("completed", rows.getInt("completed"));
                request.setAttribute("attempted", rows.getInt("attempted"));
                request.setAttribute("available", rows.getInt("available"));
                request.setAttribute("progressPercent", xp % 100);
                request.setAttribute("xpToNext", 100 - (xp % 100));
            }
        } catch (SQLException exception) {
            getServletContext().log("Student dashboard database error", exception);
            request.setAttribute("error", "Progress could not be loaded. Check database connectivity.");
        }
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
