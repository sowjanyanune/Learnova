<%@ page import="java.net.URLDecoder,java.nio.charset.StandardCharsets,jakarta.servlet.http.Cookie" %>
<%
    String pageTitle = "Student login";
    String rememberedUsername = "";
    if (request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
            if ("learnova_last_username".equals(cookie.getName())) {
                try {
                    rememberedUsername = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                } catch (IllegalArgumentException exception) {
                    rememberedUsername = "";
                }
                break;
            }
        }
    }
    if (request.getParameter("identity") != null) rememberedUsername = request.getParameter("identity");
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<section class="form-wrap">
    <div class="form-heading"><span class="eyebrow">WELCOME BACK</span><h1>Continue your quest</h1><p>Sign in to your student account and keep growing your skills.</p></div>
    <div class="form-card">
        <% if (request.getAttribute("error") != null) { %><div class="notice notice-error" role="alert"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
        <% if ("1".equals(request.getParameter("registered"))) { %><div class="notice notice-success" role="status">Registration successful. Sign in to start your missions.</div><% } %>
        <% if ("1".equals(request.getParameter("loginRequired"))) { %><div class="notice notice-info" role="status">Please sign in to access student missions and progress.</div><% } %>
        <form action="<%= basePath %>/login" method="post" class="form-grid">
            <label for="identity">Username or email</label><input id="identity" name="identity" required type="text" maxlength="100" autocomplete="username" placeholder="username or email" value="<%= HtmlUtil.escape(rememberedUsername) %>">
            <label for="password">Password</label><input id="password" name="password" required type="password" autocomplete="current-password" placeholder="Enter your password">
            <button type="submit" class="btn btn-primary btn-block">Sign in</button>
        </form>
        <div class="form-caption">New player? <a href="<%= basePath %>/register.jsp">Create an account</a></div>
    </div>
</section>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
