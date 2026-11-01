<% String pageTitle = "Create question"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<section class="page-header"><span class="eyebrow">ADMIN / CREATE MISSION</span><h1>Create a programming mission</h1><p>Questions are saved directly in the MySQL database and appear on the student missions page.</p></section>
<div class="editor-card">
    <% if (request.getAttribute("error") != null) { %><div class="notice notice-error" role="alert"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
    <% if ("1".equals(request.getParameter("created"))) { %><div class="notice notice-success" role="status">Mission created successfully. <a href="<%= basePath %>/admin/questions">View all questions</a></div><% } %>
    <form action="<%= basePath %>/admin/add-question" method="post" class="form-grid">
        <div class="form-section-title">01 / Mission details</div>
        <label for="title">Question title</label><input id="title" name="title" type="text" maxlength="100" required placeholder="e.g. What is a Java interface?" value="<%= HtmlUtil.escape(request.getParameter("title")) %>">
        <label for="description">Question description</label><textarea id="description" name="description" rows="5" maxlength="10000" required placeholder="Write the programming question here"><%= HtmlUtil.escape(request.getParameter("description")) %></textarea>
        <div class="two-column">
            <div><label for="category">Category</label><select id="category" name="category" required><option value="">Select category</option><% for (String item : new String[]{"Java", "SQL", "DSA"}) { %><option value="<%= item %>" <%= item.equals(request.getParameter("category")) ? "selected" : "" %>><%= item %></option><% } %></select></div>
            <div><label for="difficulty">Difficulty</label><select id="difficulty" name="difficulty" required><option value="">Select difficulty</option><% for (String item : new String[]{"Easy", "Medium", "Hard"}) { %><option value="<%= item %>" <%= item.equals(request.getParameter("difficulty")) ? "selected" : "" %>><%= item %></option><% } %></select></div>
        </div>
        <div class="form-section-title">02 / Answer choices</div>
        <label for="option_a">Option A</label><input id="option_a" name="option_a" type="text" maxlength="255" required value="<%= HtmlUtil.escape(request.getParameter("option_a")) %>" placeholder="First choice">
        <label for="option_b">Option B</label><input id="option_b" name="option_b" type="text" maxlength="255" required value="<%= HtmlUtil.escape(request.getParameter("option_b")) %>" placeholder="Second choice">
        <label for="option_c">Option C</label><input id="option_c" name="option_c" type="text" maxlength="255" required value="<%= HtmlUtil.escape(request.getParameter("option_c")) %>" placeholder="Third choice">
        <label for="option_d">Option D</label><input id="option_d" name="option_d" type="text" maxlength="255" required value="<%= HtmlUtil.escape(request.getParameter("option_d")) %>" placeholder="Fourth choice">
        <div class="two-column">
            <div><label for="correctChoice">Correct choice</label><select name="correctChoice" id="correctChoice" required><option value="">Select correct option</option><% for (String choice : new String[]{"A", "B", "C", "D"}) { %><option value="<%= choice %>" <%= choice.equals(request.getParameter("correctChoice")) ? "selected" : "" %>>Option <%= choice %></option><% } %></select></div>
            <div><label for="xpReward">XP reward (10-500)</label><input id="xpReward" name="xpReward" type="number" min="10" max="500" required value="<%= HtmlUtil.escape(request.getParameter("xpReward") == null ? "50" : request.getParameter("xpReward")) %>"></div>
        </div>
        <div class="button-row form-bottom"><button class="btn btn-primary" type="submit">Save mission</button><a class="btn btn-secondary" href="<%= basePath %>/admin/questions">View all questions</a></div>
    </form>
</div>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
