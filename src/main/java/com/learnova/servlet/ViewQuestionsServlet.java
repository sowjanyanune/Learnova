package com.learnova.servlet;

import com.learnova.model.QuestionSummary;
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

@WebServlet("/admin/questions")
public class ViewQuestionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<QuestionSummary> questions = new ArrayList<>();
        String sql = "SELECT m.id, m.title, m.category, m.difficulty, m.xp_reward, a.username AS creator "
                + "FROM missions m JOIN admins a ON a.id = m.created_by ORDER BY m.id DESC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                questions.add(new QuestionSummary(rows.getInt("id"), rows.getString("title"),
                        rows.getString("category"), rows.getString("difficulty"),
                        rows.getInt("xp_reward"), rows.getString("creator")));
            }
        } catch (SQLException exception) {
            getServletContext().log("Question management database error", exception);
            request.setAttribute("error", "Unable to load questions. Check database connectivity.");
        }
        request.setAttribute("questions", questions);
        request.getRequestDispatcher("/view-questions.jsp").forward(request, response);
    }
}
