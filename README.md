# Learnova — simple programming learning website

A standalone, light-theme Java 21 Maven/JSP/Servlet/MySQL project for Eclipse and Tomcat 10.1. It is separate from Elifa.

**Start with `START_HERE.txt`.** The Windows `SETUP_MYSQL.bat` helper configures MySQL safely without editing Java code. Use `docs/ADMIN_SETUP.md` once to create the initial administrator.

## Modules

Student registration, login/logout, personal dashboard, Java/SQL/DSA missions, one submission per mission, XP and level calculation, leaderboard. Admin login/logout, dashboard, question creation and list. JDBC uses PreparedStatement with MySQL.

## GitHub safety

The password is kept under your Windows home folder, outside the repository. Do not add it, screenshots with secrets, or any generated class/WAR files to Git. `.gitignore` excludes build output and local secret files. Push only after your approval and tests.

## Testing

Run the manual checks in `docs/TEST_CHECKLIST.md`. The source has previously been checked in a separate runtime; this simplified package has not been tested in your local Windows Eclipse and MySQL environment. No GitHub push has been made.
