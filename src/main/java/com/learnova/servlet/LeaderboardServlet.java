package com.learnova.servlet;

import com.learnova.model.LeaderboardEntry;
import com.learnova.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<LeaderboardEntry> ranking = new ArrayList<>();
        String sql = "SELECT u.username, u.total_xp, u.player_level, "
                + "COALESCE(c.completed, 0) AS completed FROM users u "
                + "LEFT JOIN (SELECT user_id, COUNT(*) AS completed FROM mission_submissions "
                + "WHERE is_correct = TRUE GROUP BY user_id) c ON c.user_id = u.id "
                + "ORDER BY u.total_xp DESC, completed DESC, u.username ASC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                ranking.add(new LeaderboardEntry(rows.getString("username"), rows.getInt("total_xp"),
                        rows.getInt("player_level"), rows.getInt("completed")));
            }
        } catch (SQLException exception) {
            getServletContext().log("Leaderboard database error", exception);
            request.setAttribute("error", "Leaderboard is temporarily unavailable. Check database connectivity.");
        }
        request.setAttribute("ranking", ranking);
        request.getRequestDispatcher("/leaderboard.jsp").forward(request, response);
    }
}
