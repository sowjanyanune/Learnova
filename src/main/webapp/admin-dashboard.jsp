<% String pageTitle = "Admin dashboard"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<section class="page-header"><span class="eyebrow">ADMIN / OVERVIEW</span><h1>Mission control</h1><p>Welcome, <strong><%= HtmlUtil.escape(session.getAttribute("adminName")) %></strong>. Manage the Learnova learning experience.</p></section>
<% if (request.getAttribute("error") != null) { %><div class="notice notice-error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<div class="stats-grid">
    <div class="stat-card"><span class="stat-label">Total missions</span><strong class="stat-number"><%= HtmlUtil.escape(request.getAttribute("totalQuestions") == null ? "—" : request.getAttribute("totalQuestions")) %></strong><span class="stat-extra">Programming challenges</span></div>
    <div class="stat-card"><span class="stat-label">Registered students</span><strong class="stat-number"><%= HtmlUtil.escape(request.getAttribute("totalStudents") == null ? "—" : request.getAttribute("totalStudents")) %></strong><span class="stat-extra">Learning community</span></div>
    <div class="stat-card"><span class="stat-label">Total attempts</span><strong class="stat-number"><%= HtmlUtil.escape(request.getAttribute("totalAttempts") == null ? "—" : request.getAttribute("totalAttempts")) %></strong><span class="stat-extra">Missions attempted</span></div>
</div>
<section class="section-block"><div class="section-heading align-left"><span class="eyebrow">ADMIN ACTIONS</span><h2>Manage learning content</h2></div>
    <div class="feature-grid">
        <article class="feature-card"><div class="feature-icon">+</div><h3>Add a question</h3><p>Create a new Java, SQL or DSA mission with four choices, an answer and XP reward.</p><a class="text-link" href="<%= basePath %>/admin/add-question">Create mission →</a></article>
        <article class="feature-card"><div class="feature-icon">≡</div><h3>View questions</h3><p>See all programming missions stored in MySQL and review their settings.</p><a class="text-link" href="<%= basePath %>/admin/questions">Manage questions →</a></article>
        <article class="feature-card"><div class="feature-icon">★</div><h3>Leaderboard</h3><p>See how students compare based on their total earned experience points.</p><a class="text-link" href="<%= basePath %>/leaderboard">View leaderboard →</a></article>
    </div>
</section>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
