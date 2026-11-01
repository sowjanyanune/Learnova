<% String pageTitle = "Mission result"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<section class="result-wrap">
    <% if (request.getAttribute("error") != null) { %>
        <div class="result-card"><div class="result-symbol">!</div><span class="eyebrow">MISSION UPDATE</span><h1>We could not score this attempt</h1><div class="notice notice-error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><a href="<%= basePath %>/missions" class="btn btn-primary">Back to missions</a></div>
    <% } else if (Boolean.TRUE.equals(request.getAttribute("correct"))) { %>
        <div class="result-card"><div class="result-symbol success-symbol">✓</div><span class="eyebrow">MISSION COMPLETE</span><h1>Correct answer!</h1><p>You completed <strong><%= HtmlUtil.escape(request.getAttribute("missionTitle")) %></strong> and earned new experience points.</p><div class="award-box">+<%= HtmlUtil.escape(request.getAttribute("earnedXp")) %> XP</div><p class="answer-feedback">Your answer: <strong><%= HtmlUtil.escape(request.getAttribute("selectedAnswer")) %></strong></p><div class="button-row centered"><a href="<%= basePath %>/missions" class="btn btn-primary">More missions →</a><a href="<%= basePath %>/dashboard" class="btn btn-secondary">View progress</a></div></div>
    <% } else if (Boolean.FALSE.equals(request.getAttribute("correct"))) { %>
        <div class="result-card"><div class="result-symbol">×</div><span class="eyebrow">MISSION ATTEMPTED</span><h1>Not quite right</h1><p>You attempted <strong><%= HtmlUtil.escape(request.getAttribute("missionTitle")) %></strong>. This attempt earns 0 XP.</p><div class="answer-review"><p><span>Your answer</span><strong><%= HtmlUtil.escape(request.getAttribute("selectedAnswer")) %></strong></p><p><span>Correct answer</span><strong><%= HtmlUtil.escape(request.getAttribute("correctAnswer")) %></strong></p></div><p class="muted">Each mission allows only one attempt. Keep learning with another challenge.</p><div class="button-row centered"><a href="<%= basePath %>/missions" class="btn btn-primary">Try another mission →</a><a href="<%= basePath %>/dashboard" class="btn btn-secondary">My dashboard</a></div></div>
    <% } else { %>
        <div class="result-card"><span class="eyebrow">MISSION BOARD</span><h1>Choose a mission first</h1><p>Open the mission board to submit an answer and see your result.</p><a href="<%= basePath %>/missions" class="btn btn-primary">Browse missions →</a></div>
    <% } %>
</section>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
