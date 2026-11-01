@echo off
cd /d "%~dp0"
echo Learnova - MySQL Connection Setup
echo Your password will not be stored in the project or GitHub.
echo.
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0scripts\Configure-Database.ps1"
echo.
echo If setup is complete, return to Eclipse and restart Tomcat.
pause
