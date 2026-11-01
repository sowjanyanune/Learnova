# Learnova acceptance checklist

| Requirement | Where it is implemented | Suggested test |
|---|---|---|
| Dark home page and navigation | index.html, css/style.css | Open homepage and navigate to every link |
| Student registration and validation | register.jsp, RegisterServlet | Register; retry same email; mismatch password |
| Student login and logout | login.jsp, LoginServlet, LogoutServlet | Login with username and email; logout |
| Admin separate login and dashboard | admin-login.jsp, AdminLoginServlet, AdminDashboardServlet | Login as admin; check stats; logout |
| Database and JDBC | database/schema.sql, DBConnection | Verify all four tables with MySQL Workbench |
| Admin question creation | add-question.jsp, AddQuestionServlet | Create Java/SQL/DSA mission |
| Admin question list | view-questions.jsp, ViewQuestionsServlet | Verify recently created mission |
| Student personalized dashboard | dashboard.jsp, DashboardServlet | Compare counts and XP with database |
| Dynamic missions | missions.jsp, MissionServlet | Add mission as admin; view as student |
| Submission and transaction XP | SubmitAnswerServlet, mission-result.jsp | Correct/incorrect; duplicate attempt |
| Leaderboard | leaderboard.jsp, LeaderboardServlet | Two students with different XP |
| Cookie and sessions | LoginServlet, LogoutServlet, AccessFilter | Remember username; protected URLs after logout |
| ServletConfig and ServletContext | AdminLoginServlet, AdminDashboardServlet, WEB-INF/web.xml | Inspect role and app name configuration |

# Database check queries

```sql
USE learnova_db;
SELECT id, username, email, total_xp, player_level FROM users;
SELECT id, title, category, difficulty, xp_reward FROM missions;
SELECT user_id, mission_id, answer, is_correct FROM mission_submissions;
SELECT username FROM admins;
```
