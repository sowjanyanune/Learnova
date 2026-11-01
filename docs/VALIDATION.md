# Learnova verification report

Verified on 29 October 2026 in an isolated Linux test environment, not on the user's Windows laptop.

## Build and runtime

- OpenJDK 21.0.12: Maven compilation and WAR packaging passed.
- Apache Tomcat 10.1.61: application deployed and JSP responses compiled successfully.
- MySQL 8.0.46: supplied schema and seed executed in a fresh temporary database.
- Temporary random credentials were used and are not included in the deliverables.
- JDBC cleanup now releases the driver and cleanup thread when Tomcat stops.

## Passed checks

- Learnova home page on Tomcat 10.1 / Java 21
- HTTP 200 /register
- Student registration
- HTTP 200 /register
- Duplicate registration rejected
- HTTP 200 /login
- Invalid login rejected
- HTTP 200 /login
- Email login and zero-XP dashboard
- New student starts at 0 XP and level 1
- Student cannot access admin
- HTTP 200 /admin-login
- Admin login
- HTTP 200 /admin/add-question
- Admin question creation
- Admin question list
- Dynamic student missions
- HTTP 200 /submit-answer
- Correct answer rewards 150 XP
- HTTP 200 /submit-answer
- Duplicate attempt rejected
- HTTP 200 /submit-answer
- Wrong answer earns zero
- Database confirms 150 XP and level 2
- One stored attempt per mission
- Direct dashboard JSP loads live data
- Concurrent duplicate only awards once
- 200 XP advances to level 3
- HTTP 200 /register
- Leaderboard ordering and XP
- No-store cache header
- Server-rendered dashboard snapshot saved
- HTTP 200 /logout
- Student logout invalidates access
- HTTP 200 /login
- Username login
- HTTP 200 /admin-logout
- Admin logout invalidates access

## Additional static checks

- Maven and web.xml are valid XML.
- Source contains no comments and uses Learnova package/UI naming.
- No real credentials, default administrator password/hash or private configuration are in the ZIP.
- database.properties is excluded from Maven resources and the generated WAR.
- Git ignore checks pass for database.properties, .env and target/Learnova.war.
- No commits, GitHub remote configuration or pushes were performed.

## Remaining local verification

- Windows Eclipse import, installed JDK/runtime selection, existing MySQL configuration, and PowerShell credential script execution cannot be performed from this session.
- The interactive administrator setup command was compiled; its Windows console interaction still needs local verification. Test admin login used a temporary BCrypt account in the database.
- Browser visual verification could not run because the Chromium download was unavailable. The saved dashboard is the actual HTML response from the passing test run, with CSS inlined and a snapshot banner. It is not a live hosted app.
- End-to-end checks used real HTTP requests, Tomcat/JSP, JDBC and MySQL, not mocks. They do not establish production readiness.

Follow README.md on Windows, then complete docs/TEST_CHECKLIST.md in the browser. Ask the owner for explicit approval before pushing to GitHub.
