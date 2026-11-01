package com.learnova.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.learnova.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/courses")
public class CoursesServlet extends HttpServlet {
    public static final class Lesson {
        public final String key, title, description, example;
        public Lesson(String key, String title, String description, String example) {
            this.key = key;
            this.title = title;
            this.description = description;
            this.example = example;
        }
    }
    private static final Map<String, List<Lesson>> COURSES = new LinkedHashMap<>();
    static {
        COURSES.put("java", new ArrayList<>());
        COURSES.put("sql", new ArrayList<>());
        COURSES.put("dsa", new ArrayList<>());
        add("java", "java-1", "Java Basics", "Java is a programming language used to build applications. A class contains Java code.", "public class Hello { public static void main(String[] args) { System.out.println(\"Hello\"); } }");
        add("java", "java-2", "Variables and Data Types", "Variables store values. int stores integers, double stores decimals, and String stores text.", "int age = 20; String name = \"Student\";");
        add("java", "java-3", "Conditions and Loops", "An if statement makes a decision. A for loop repeats a block of code.", "for (int i = 1; i <= 3; i++) { System.out.println(i); }");
        add("java", "java-4", "Methods and Objects", "A method groups reusable instructions. Objects are instances of classes.", "class Student { void greet() { System.out.println(\"Welcome\"); } }");
        add("java", "java-5", "Arrays", "An array stores multiple values of the same type.", "int[] scores = {70, 80, 90};");
        add("java", "java-6", "Exceptions", "Exception handling lets programs respond to unexpected errors.", "try { int result = 10 / 2; } catch (ArithmeticException e) { System.out.println(\"Error\"); }");
        add("sql", "sql-1", "Database Basics", "A relational database stores information in tables made of rows and columns.", "CREATE TABLE students (id INT PRIMARY KEY, name VARCHAR(50));");
        add("sql", "sql-2", "SELECT Queries", "SELECT retrieves columns from a table.", "SELECT name FROM students;");
        add("sql", "sql-3", "Filtering", "WHERE limits rows to those that match a condition.", "SELECT * FROM students WHERE id > 5;");
        add("sql", "sql-4", "Sorting and Grouping", "ORDER BY sorts results, while GROUP BY groups related rows.", "SELECT category, COUNT(*) FROM missions GROUP BY category;");
        add("sql", "sql-5", "JOIN Queries", "JOIN combines data from related tables using matching values.", "SELECT u.username, s.mission_id FROM users u JOIN mission_submissions s ON u.id = s.user_id;");
        add("dsa", "dsa-1", "Arrays and Complexity", "Arrays support indexed access. Big-O describes how work grows with input size.", "int[] data = {4, 8, 12};");
        add("dsa", "dsa-2", "Stacks", "A stack uses last-in, first-out order, like a pile of plates.", "push(10), push(20), pop() returns 20");
        add("dsa", "dsa-3", "Queues", "A queue uses first-in, first-out order, like people waiting in line.", "enqueue(10), enqueue(20), dequeue() returns 10");
        add("dsa", "dsa-4", "Linked Lists", "A linked list stores nodes where each node points to the next.", "10 -> 20 -> 30");
        add("dsa", "dsa-5", "Searching Algorithms", "Linear search checks elements sequentially; binary search works on sorted data.", "binary search: repeatedly inspect the middle element");
    }
    private static void add(String course, String key, String title, String description, String example) {
        COURSES.get(course).add(new Lesson(key, title, description, example));
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String course = request.getParameter("course");
        if (course == null || !COURSES.containsKey(course)) {
            course = "java";
        }
        Set<String> completed = new LinkedHashSet<>();
        HttpSession session = request.getSession(false);
        Integer userId = session == null ? null : (Integer) session.getAttribute("userId");
        if (userId != null) {
            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT lesson_key FROM lesson_completions WHERE user_id = ?")) {
                statement.setInt(1, userId);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) completed.add(rs.getString(1));
                }
            } catch (SQLException e) {
                getServletContext().log("Could not load course progress", e);
                request.setAttribute("error", "Progress unavailable. Run database/courses_upgrade.sql in MySQL Workbench.");
            }
        }
        request.setAttribute("course", course);
        request.setAttribute("courses", COURSES);
        request.setAttribute("completed", completed);
        request.getRequestDispatcher("/courses.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?loginRequired=1");
            return;
        }
        int userId = (Integer) session.getAttribute("userId");
        String course = request.getParameter("course");
        String lesson = request.getParameter("lesson");
        if (!COURSES.containsKey(course) || lesson == null
                || COURSES.get(course).stream().noneMatch(l -> l.key.equals(lesson))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "INSERT IGNORE INTO lesson_completions (user_id, lesson_key) VALUES (?, ?)")) {
            statement.setInt(1, userId);
            statement.setString(2, lesson);
            statement.executeUpdate();
        } catch (SQLException e) {
            getServletContext().log("Could not save lesson completion", e);
            response.sendError(500, "Could not save progress. Check course table setup.");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/courses?course=" + course);
    }
}
