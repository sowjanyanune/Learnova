<%@ page import="com.learnova.model.QuestionSummary,java.util.List,java.util.Collections" %>
<% String pageTitle = "Manage questions"; %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<%
    List<QuestionSummary> questions = (List<QuestionSummary>) request.getAttribute("questions");
    if (questions == null) questions = Collections.emptyList();
%>
<section class="page-header"><span class="eyebrow">ADMIN / QUESTION LIBRARY</span><h1>All programming missions</h1><p>Every question below is loaded dynamically from MySQL.</p><a class="btn btn-primary" href="<%= basePath %>/admin/add-question">+ Add new mission</a></section>
<% if (request.getAttribute("error") != null) { %><div class="notice notice-error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<div class="table-card"><div class="table-header"><strong>Mission library</strong><span class="mini-tag"><%= questions.size() %> missions</span></div>
    <% if (questions.isEmpty()) { %><div class="empty-state">No questions available. <a href="<%= basePath %>/admin/add-question">Create your first mission</a>.</div><% } else { %>
    <div class="table-scroll"><table><thead><tr><th>ID</th><th>Question</th><th>Category</th><th>Difficulty</th><th>Reward</th><th>Created by</th></tr></thead><tbody>
    <% for (QuestionSummary question : questions) { %>
        <tr><td class="muted">#<%= question.getId() %></td><td class="table-title"><%= HtmlUtil.escape(question.getTitle()) %></td><td><span class="tag"><%= HtmlUtil.escape(question.getCategory()) %></span></td><td><span class="difficulty difficulty-<%= HtmlUtil.escape(question.getDifficulty().toLowerCase()) %>"><%= HtmlUtil.escape(question.getDifficulty()) %></span></td><td class="xp-text">+<%= question.getXpReward() %> XP</td><td><%= HtmlUtil.escape(question.getCreator()) %></td></tr>
    <% } %>
    </tbody></table></div>
    <% } %>
</div>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
