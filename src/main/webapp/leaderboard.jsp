<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.learnova.model.LeaderboardEntry,java.util.List,java.util.Collections" %>
<% String pageTitle = "Global leaderboard"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<%
    List<LeaderboardEntry> ranking = (List<LeaderboardEntry>) request.getAttribute("ranking");
    if (ranking == null) ranking = Collections.emptyList();
%>
<section class="page-header"><span class="eyebrow">GLOBAL RANKINGS</span><h1>The Learnova leaderboard</h1><p>Every XP matters. Rankings update automatically when you refresh this page.</p></section>
<% if (request.getAttribute("error") != null) { %><div class="notice notice-error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<div class="table-card"><div class="table-header"><strong>Top players</strong><span class="mini-tag"><%= ranking.size() %> players</span></div>
    <% if (ranking.isEmpty()) { %><div class="empty-state">No students are ranked yet. <a href="<%= basePath %>/register.jsp">Be the first player</a>.</div><% } else { %>
    <div class="table-scroll"><table><thead><tr><th>Rank</th><th>Player</th><th>Level</th><th>Completed missions</th><th>Total XP</th></tr></thead><tbody>
    <% for (int index = 0; index < ranking.size(); index++) { LeaderboardEntry entry = ranking.get(index); int rank = index + 1; %>
        <tr><td><span class="rank rank-<%= Math.min(rank, 4) %>"><%=rank == 1 ? "*":rank == 2 ? "*":rank == 3 ? "*":""%>#<%= rank %></span></td><td class="table-title"><%= HtmlUtil.escape(entry.getUsername()) %></td><td>Level <%= entry.getPlayerLevel() %></td><td><%= entry.getCompleted() %></td><td class="xp-text"><%= entry.getTotalXp() %> XP</td></tr>
    <% } %>
    </tbody></table></div>
    <% } %>
</div>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
