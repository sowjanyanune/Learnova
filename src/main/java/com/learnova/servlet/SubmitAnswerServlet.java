package com.learnova.servlet;

import com.learnova.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/submit-answer")
public class SubmitAnswerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = (Integer) request.getSession(false).getAttribute("userId");
        String answerChoice = request.getParameter("answerChoice");
        int missionId;
        try {
            missionId = Integer.parseInt(request.getParameter("missionId"));
        } catch (NumberFormatException exception) {
            result(request, response, "Invalid mission selected.");
            return;
        }
        if (missionId < 1 || answerChoice == null || !Set.of("A", "B", "C", "D").contains(answerChoice)) {
            result(request, response, "Select one answer choice before submitting.");
            return;
        }
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int previousXp;
                try (PreparedStatement lockUser = connection.prepareStatement(
                        "SELECT total_xp FROM users WHERE id = ? FOR UPDATE")) {
                    lockUser.setInt(1, userId);
                    try (ResultSet rows = lockUser.executeQuery()) {
                        if (!rows.next()) {
                            connection.rollback();
                            result(request, response, "Your student account no longer exists.");
                            return;
                        }
                        previousXp = rows.getInt("total_xp");
                    }
                }
                try (PreparedStatement prior = connection.prepareStatement(
                        "SELECT id FROM mission_submissions WHERE user_id = ? AND mission_id = ?")) {
                    prior.setInt(1, userId);
                    prior.setInt(2, missionId);
                    try (ResultSet rows = prior.executeQuery()) {
                        if (rows.next()) {
                            connection.rollback();
                            result(request, response, "You have already attempted this mission. Only one attempt is allowed.");
                            return;
                        }
                    }
                }
                String sql = "SELECT title, option_a, option_b, option_c, option_d, correct_answer, xp_reward "
                        + "FROM missions WHERE id = ?";
                String selected;
                String correctAnswer;
                String title;
                int xpReward;
                try (PreparedStatement question = connection.prepareStatement(sql)) {
                    question.setInt(1, missionId);
                    try (ResultSet rows = question.executeQuery()) {
                        if (!rows.next()) {
                            connection.rollback();
                            result(request, response, "This mission is no longer available.");
                            return;
                        }
                        title = rows.getString("title");
                        selected = switch (answerChoice) {
                            case "A" -> rows.getString("option_a");
                            case "B" -> rows.getString("option_b");
                            case "C" -> rows.getString("option_c");
                            default -> rows.getString("option_d");
                        };
                        correctAnswer = rows.getString("correct_answer");
                        xpReward = rows.getInt("xp_reward");
                    }
                }
                boolean correct = selected.equals(correctAnswer);
                try (PreparedStatement insert = connection.prepareStatement(
                        "INSERT INTO mission_submissions (user_id, mission_id, answer, is_correct) VALUES (?, ?, ?, ?)")) {
                    insert.setInt(1, userId);
                    insert.setInt(2, missionId);
                    insert.setString(3, selected);
                    insert.setBoolean(4, correct);
                    insert.executeUpdate();
                }
                if (correct) {
                    int newXp = previousXp + xpReward;
                    int newLevel = 1 + newXp / 100;
                    try (PreparedStatement update = connection.prepareStatement(
                            "UPDATE users SET total_xp = ?, player_level = ? WHERE id = ?")) {
                        update.setInt(1, newXp);
                        update.setInt(2, newLevel);
                        update.setInt(3, userId);
                        update.executeUpdate();
                    }
                }
                connection.commit();
                request.setAttribute("missionTitle", title);
                request.setAttribute("selectedAnswer", selected);
                request.setAttribute("correctAnswer", correctAnswer);
                request.setAttribute("correct", correct);
                request.setAttribute("earnedXp", correct ? xpReward : 0);
                request.getRequestDispatcher("/mission-result.jsp").forward(request, response);
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            }
        } catch (SQLException exception) {
            getServletContext().log("Mission submission database error", exception);
            result(request, response, exception.getErrorCode() == 1062
                    ? "You have already attempted this mission. Only one attempt is allowed."
                    : "Unable to submit your answer. Check the database and try again.");
        }
    }

    private void result(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/mission-result.jsp").forward(request, response);
    }
}
