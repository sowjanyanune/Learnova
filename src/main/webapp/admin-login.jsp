<% String pageTitle = "Administrator login"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<section class="form-wrap">
    <div class="form-heading"><span class="eyebrow">ADMINISTRATOR ACCESS</span><h1>Mission control</h1><p>Manage programming questions and track activity.</p></div>
    <div class="form-card">
        <% if (request.getAttribute("error") != null) { %><div class="notice notice-error" role="alert"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
        <% if ("1".equals(request.getParameter("loginRequired"))) { %><div class="notice notice-info" role="status">Administrator authentication is required.</div><% } %>
        <form action="<%= basePath %>/admin-login" method="post" class="form-grid">
            <label for="username">Admin username</label><input id="username" name="username" type="text" required autocomplete="username" placeholder="admin" value="<%= HtmlUtil.escape(request.getParameter("username")) %>">
            <label for="password">Password</label><input id="password" name="password" type="password" required autocomplete="current-password" placeholder="Administrator password">
            <button type="submit" class="btn btn-primary btn-block">Open mission control</button>
        </form>
        <div class="form-caption">Student account? <a href="<%= basePath %>/login.jsp">Use student login</a></div>
    </div>
</section>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
