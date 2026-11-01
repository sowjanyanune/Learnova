# Create the first Learnova administrator

The project stores administrator passwords as BCrypt hashes, not plain text.

1. Finish database/schema.sql and SETUP_MYSQL.bat first.
2. Open Eclipse and select `com.learnova.util.AdminSetup` in `src/main/java`.
3. The existing AdminSetup tool requires a real terminal console (not Eclipse's Console). In Windows Terminal, change to the Learnova project directory, then run:

   `mvn -q dependency:build-classpath -Dmdep.outputFile=classpath.txt`

   `mvn -q compile`

   `java -cp "target/classes;$(Get-Content classpath.txt)" com.learnova.util.AdminSetup`

   These commands are for **PowerShell** with Maven available on PATH. The administrator username and password are prompted securely (password will not be shown).
4. Return to MySQL Workbench and execute `database/seed.sql` once, after the administrator exists.
5. In Eclipse start Tomcat and visit `http://localhost:8080/Learnova/admin-login.jsp`.

If Maven is unavailable in Terminal, install Maven or configure its path. Maven is bundled within Eclipse for IDE operations, but not necessarily for Windows Terminal. Never enter your admin password into a SQL script or a Git-tracked file.
