package com.learnova.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getServletPath();
        httpResponse.setHeader("Cache-Control", "no-store");
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");
        httpResponse.setHeader("X-Frame-Options", "DENY");
        HttpSession session = httpRequest.getSession(false);
        boolean adminRequired = path.startsWith("/admin/") || path.equals("/admin-dashboard.jsp")
                || path.equals("/add-question.jsp") || path.equals("/view-questions.jsp");
        boolean studentRequired = path.equals("/dashboard") || path.equals("/dashboard.jsp")
                || path.equals("/missions") || path.equals("/missions.jsp")
                || path.equals("/submit-answer") || path.equals("/mission-result.jsp");

        if (adminRequired && (session == null || session.getAttribute("adminId") == null)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin-login.jsp?loginRequired=1");
            return;
        }
        if (studentRequired && (session == null || session.getAttribute("userId") == null)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?loginRequired=1");
            return;
        }
        String controller = switch (path) {
            case "/dashboard.jsp" -> "/dashboard";
            case "/missions.jsp" -> "/missions";
            case "/leaderboard.jsp" -> "/leaderboard";
            case "/admin-dashboard.jsp" -> "/admin/dashboard";
            case "/view-questions.jsp" -> "/admin/questions";
            case "/add-question.jsp" -> "/admin/add-question";
            default -> null;
        };
        if (controller != null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + controller);
            return;
        }
        chain.doFilter(request, response);
    }
}
