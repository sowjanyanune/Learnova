package com.learnova.servlet;

import com.learnova.model.Mission;
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

@WebServlet("/missions")
public class MissionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = (Integer) request.getSession(false).getAttribute("userId");
        List<Mission> missions = new ArrayList<>();
        String sql = "SELECT m.id, m.title, m.description, m.category, m.difficulty, m.option_a, "
                + "m.option_b, m.option_c, m.option_d, m.xp_reward, "
                + "CASE WHEN s.id IS NULL THEN FALSE ELSE TRUE END AS attempted, s.is_correct "
                + "FROM missions m LEFT JOIN mission_submissions s ON s.mission_id = m.id AND s.user_id = ? "
                + "ORDER BY FIELD(m.category, 'Java', 'SQL', 'DSA'), "
                + "FIELD(m.difficulty, 'Easy', 'Medium', 'Hard'), m.id";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet rows = statement.executeQuery()) {
                while (rows.next()) {
                    missions.add(new Mission(rows.getInt("id"), rows.getString("title"),
                            rows.getString("description"), rows.getString("category"),
                            rows.getString("difficulty"), rows.getString("option_a"),
                            rows.getString("option_b"), rows.getString("option_c"),
                            rows.getString("option_d"), rows.getInt("xp_reward"),
                            rows.getBoolean("attempted"), rows.getBoolean("is_correct")));
                }
            }
        } catch (SQLException exception) {
            getServletContext().log("Missions database error", exception);
            request.setAttribute("error", "Missions could not be loaded. Check database connectivity.");
        }
        request.setAttribute("missions", missions);
        request.getRequestDispatcher("/missions.jsp").forward(request, response);
    }
}
