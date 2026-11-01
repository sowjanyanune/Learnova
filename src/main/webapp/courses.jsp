<%@ page import="java.util.*,com.learnova.servlet.CoursesServlet.Lesson,com.learnova.util.HtmlUtil" %>
<%
String pageTitle = "Courses";
Map<String,List<Lesson>> courses = (Map<String,List<Lesson>>) request.getAttribute("courses");
Set<String> completed = (Set<String>) request.getAttribute("completed");
String selected = (String) request.getAttribute("course");
String[] ids = {"java","sql","dsa"};
String[] names = {"Java Programming","SQL Fundamentals","Data Structures & Algorithms"};
boolean loggedIn = session.getAttribute("userId") != null;
%>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<section class="course-intro">
    <span class="eyebrow">Learnova Learning Library</span>
    <h1>Explore courses</h1>
    <p>Learn the basics, complete lessons, then practice your skills in Missions.</p>
</section>
<div class="course-tabs">
<% for (int i=0; i<ids.length; i++) { %>
    <a class="course-tab <%= selected.equals(ids[i]) ? "active" : "" %>" href="<%= basePath %>/courses?course=<%= ids[i] %>"><%= names[i] %></a>
<% } %>
</div>
<% if (request.getAttribute("error") != null) { %>
    <p class="course-error"><%= HtmlUtil.escape((String)request.getAttribute("error")) %></p>
<% } %>
<%
List<Lesson> lessons = courses.get(selected);
int done = 0;
for (Lesson lesson : lessons) if (completed.contains(lesson.key)) done++;
%>
<div class="course-progress"><strong><%= done %> / <%= lessons.size() %> lessons completed</strong><div class="course-track"><div style="width:<%= 100*done/lessons.size() %>%"></div></div></div>
<div class="lesson-grid">
<% int number=0; for (Lesson lesson : lessons) { number++; boolean finished=completed.contains(lesson.key); %>
<article class="lesson-card">
    <div class="lesson-top"><span class="lesson-number">Lesson <%= number %></span><span class="lesson-status"><%= finished ? "Completed" : "Not completed" %></span></div>
    <h2><%= HtmlUtil.escape(lesson.title) %></h2>
    <p><%= HtmlUtil.escape(lesson.description) %></p>
    <pre><code><%= HtmlUtil.escape(lesson.example) %></code></pre>
    <% if (finished) { %>
        <span class="lesson-done">✓ Completed</span>
    <% } else if (loggedIn) { %>
        <form action="<%= basePath %>/courses" method="post">
            <input type="hidden" name="course" value="<%= selected %>">
            <input type="hidden" name="lesson" value="<%= lesson.key %>">
            <button type="submit" class="lesson-button">Mark as Completed</button>
        </form>
    <% } else { %>
        <a class="lesson-button" href="<%= basePath %>/login.jsp">Log in to save progress</a>
    <% } %>
</article>
<% } %>
</div>
<a class="lesson-button" href="<%= basePath %>/missions">Practice with missions</a>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
