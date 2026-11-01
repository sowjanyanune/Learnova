<% String pageTitle = "Student dashboard"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<%
    int xp = request.getAttribute("totalXp") instanceof Integer ? (Integer) request.getAttribute("totalXp") : 0;
    int progressPercent = xp % 100;
    int level = request.getAttribute("playerLevel") instanceof Integer ? (Integer) request.getAttribute("playerLevel") : 1;
%>
<section class="page-header"><span class="eyebrow">PLAYER / DASHBOARD</span><h1>Welcome back, <span class="gradient-text"><%= HtmlUtil.escape(request.getAttribute("username") == null ? session.getAttribute("username") : request.getAttribute("username")) %></span></h1><p>Your programming journey is in progress. Keep solving to reach your next level.</p></section>
<% if (request.getAttribute("error") != null) { %><div class="notice notice-error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<div class="stats-grid stats-grid-four">
    <div class="stat-card"><span class="stat-label">Total experience</span><strong class="stat-number"><%= xp %> <span class="stat-suffix">XP</span></strong><span class="stat-extra">Keep earning rewards</span></div>
    <div class="stat-card"><span class="stat-label">Current level</span><strong class="stat-number"><%= level %></strong><span class="stat-extra">+1 level every 100 XP</span></div>
    <div class="stat-card"><span class="stat-label">Completed correctly</span><strong class="stat-number"><%= HtmlUtil.escape(request.getAttribute("completed") == null ? "—" : request.getAttribute("completed")) %></strong><span class="stat-extra">Solved missions</span></div>
    <div class="stat-card"><span class="stat-label">Missions attempted</span><strong class="stat-number"><%= HtmlUtil.escape(request.getAttribute("attempted") == null ? "—" : request.getAttribute("attempted")) %></strong><span class="stat-extra">Out of <%= HtmlUtil.escape(request.getAttribute("available") == null ? "—" : request.getAttribute("available")) %> available</span></div>
</div>
<div class="dashboard-grid">
    <div class="progress-panel"><span class="eyebrow">YOUR LEVEL PROGRESSION</span><div class="level-row"><h2>Level <%= level %></h2><span class="mini-tag">LEVEL <%= level + 1 %> NEXT</span></div><p>Earn <strong><%= 100 - progressPercent %> XP</strong> to reach the next level.</p><div class="progress-track"><div class="progress-fill" style="width:<%= progressPercent %>%"></div></div><div class="progress-labels"><span><%= progressPercent %> / 100 XP</span><span><%= progressPercent %>% complete</span></div></div>
    <div class="next-mission-panel"><span class="eyebrow">NEXT STEP</span><h2>A new challenge awaits</h2><p>Browse programming missions, test your skills and grow your XP.</p><a class="btn btn-primary" href="<%= basePath %>/missions">Explore missions →</a></div>
</div>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
