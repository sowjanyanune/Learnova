package com.learnova.servlet;

import com.learnova.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/add-question")
public class AddQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/add-question.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = field(request, "title");
        String description = field(request, "description");
        String category = field(request, "category");
        String difficulty = field(request, "difficulty");
        String a = field(request, "option_a");
        String b = field(request, "option_b");
        String c = field(request, "option_c");
        String d = field(request, "option_d");
        String correctChoice = field(request, "correctChoice");
        String reward = field(request, "xpReward");
        int xp;
        try {
            xp = Integer.parseInt(reward);
        } catch (NumberFormatException exception) {
            fail(request, response, "XP must be a whole number.");
            return;
        }
        if (title.isEmpty() || title.length() > 100 || description.isEmpty()
                || description.length() > 10000 || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()
                || a.length() > 255 || b.length() > 255 || c.length() > 255 || d.length() > 255
                || !Set.of("Java", "SQL", "DSA").contains(category)
                || !Set.of("Easy", "Medium", "Hard").contains(difficulty)
                || !Set.of("A", "B", "C", "D").contains(correctChoice)
                || xp < 10 || xp > 500) {
            fail(request, response, "Complete every field. Use valid categories, difficulties and XP from 10 to 500.");
            return;
        }
        Set<String> unique = new HashSet<>();
        for (String option : Arrays.asList(a, b, c, d)) {
            unique.add(option.toLowerCase(java.util.Locale.ROOT));
        }
        if (unique.size() != 4) {
            fail(request, response, "All four answer choices must be different.");
            return;
        }
        String correctAnswer = switch (correctChoice) {
            case "A" -> a;
            case "B" -> b;
            case "C" -> c;
            default -> d;
        };
        String sql = "INSERT INTO missions (title, description, category, difficulty, option_a, option_b, "
                + "option_c, option_d, correct_answer, xp_reward, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, category);
            statement.setString(4, difficulty);
            statement.setString(5, a);
            statement.setString(6, b);
            statement.setString(7, c);
            statement.setString(8, d);
            statement.setString(9, correctAnswer);
            statement.setInt(10, xp);
            statement.setInt(11, (Integer) request.getSession(false).getAttribute("adminId"));
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/admin/add-question?created=1");
        } catch (SQLException exception) {
            getServletContext().log("Question creation database error", exception);
            fail(request, response, "Question could not be saved. Check database connectivity.");
        }
    }

    private String field(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        return value == null ? "" : value.trim();
    }

    private void fail(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/add-question.jsp").forward(request, response);
    }
}
