<%@ page import="com.learnova.model.Mission,java.util.List,java.util.Collections" %>
<% String pageTitle = "Programming missions"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<%
    List<Mission> missions = (List<Mission>) request.getAttribute("missions");
    if (missions == null) missions = Collections.emptyList();
    int attemptedCount = 0;
    for (Mission m : missions) if (m.isAttempted()) attemptedCount++;
%>
<section class="page-header"><span class="eyebrow">PLAYER / MISSION BOARD</span><h1>Choose your challenge</h1><p>Java, SQL and DSA missions. Pick one answer per mission: every attempt counts.</p><div class="subtle-summary"><span><%= missions.size() %> available missions</span><span><%= attemptedCount %> attempted</span></div></section>
<% if (request.getAttribute("error") != null) { %><div class="notice notice-error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<% if (missions.isEmpty()) { %>
    <div class="empty-state">No missions are available yet. An administrator can add questions from the admin dashboard.</div>
<% } else { %>
    <div class="mission-grid">
    <% for (Mission mission : missions) { %>
        <article class="mission-card">
            <div class="mission-top"><span class="tag"><%= HtmlUtil.escape(mission.getCategory()) %></span><span class="difficulty difficulty-<%= HtmlUtil.escape(mission.getDifficulty().toLowerCase()) %>"><%= HtmlUtil.escape(mission.getDifficulty()) %></span></div>
            <h2><%= HtmlUtil.escape(mission.getTitle()) %></h2><p class="mission-desc"><%= HtmlUtil.escape(mission.getDescription()) %></p>
            <% if (mission.isAttempted()) { %>
                <div class="notice <%= mission.isPreviouslyCorrect() ? "notice-success" : "notice-info" %>"><%= mission.isPreviouslyCorrect() ? "Mission completed correctly" : "Mission attempted · No XP awarded" %></div>
            <% } else { %>
                <form class="choices" method="post" action="<%= basePath %>/submit-answer">
                    <input type="hidden" name="missionId" value="<%= mission.getId() %>">
                    <label class="choice"><input type="radio" name="answerChoice" value="A" required><span class="choice-key">A</span><span><%= HtmlUtil.escape(mission.getOptionA()) %></span></label>
                    <label class="choice"><input type="radio" name="answerChoice" value="B" required><span class="choice-key">B</span><span><%= HtmlUtil.escape(mission.getOptionB()) %></span></label>
                    <label class="choice"><input type="radio" name="answerChoice" value="C" required><span class="choice-key">C</span><span><%= HtmlUtil.escape(mission.getOptionC()) %></span></label>
                    <label class="choice"><input type="radio" name="answerChoice" value="D" required><span class="choice-key">D</span><span><%= HtmlUtil.escape(mission.getOptionD()) %></span></label>
                    <div class="mission-bottom"><span class="xp-chip">+<%= mission.getXpReward() %> XP</span><button type="submit" class="btn btn-primary btn-small">Submit answer →</button></div>
                </form>
            <% } %>
            <% if (mission.isAttempted()) { %><div class="mission-bottom"><span class="xp-chip">Reward: <%= mission.getXpReward() %> XP</span><span class="muted">One attempt only</span></div><% } %>
        </article>
    <% } %>
    </div>
<% } %>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
