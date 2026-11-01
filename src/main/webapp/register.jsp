<% String pageTitle = "Student registration"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<section class="form-wrap">
    <div class="form-heading"><span class="eyebrow">NEW PLAYER</span><h1>Create your account</h1><p>Join the quest. Your journey starts at Level 1 with 0 XP.</p></div>
    <div class="form-card">
        <% if (request.getAttribute("error") != null) { %><div class="notice notice-error" role="alert"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
        <form action="<%= basePath %>/register" method="post" class="form-grid">
            <label for="username">Username</label><input id="username" name="username" type="text" required maxlength="50" minlength="3" pattern="[A-Za-z0-9_]+" placeholder="your_username" autocomplete="username" value="<%= HtmlUtil.escape(request.getParameter("username")) %>">
            <label for="email">Email address</label><input id="email" name="email" type="email" maxlength="100" required placeholder="you@example.com" autocomplete="email" value="<%= HtmlUtil.escape(request.getParameter("email")) %>">
            <label for="password">Password</label><input id="password" name="password" type="password" required minlength="8" maxlength="72" placeholder="At least 8 characters" autocomplete="new-password">
            <label for="confirmPassword">Confirm password</label><input id="confirmPassword" name="confirmPassword" type="password" required minlength="8" maxlength="72" placeholder="Repeat your password" autocomplete="new-password">
            <button class="btn btn-primary btn-block" type="submit">Create account</button>
        </form>
        <div class="form-caption">Already have an account? <a href="<%= basePath %>/login.jsp">Sign in here</a></div>
    </div>
</section>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
