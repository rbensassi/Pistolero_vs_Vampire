@echo off
REM Pistolero vs Vampire - Launch Script (Windows)
REM Java 21 with JavaFX

echo ===================================
echo   Pistolero vs Vampire - Java 21
echo ===================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [91mMaven n'est pas installe![0m
    echo.
    echo Telechargez Maven depuis: https://maven.apache.org/download.cgi
    echo Ou installez avec Chocolatey: choco install maven
    echo.
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [91mJava n'est pas installe![0m
    echo.
    echo Telechargez Java 21 depuis: https://adoptium.net/
    echo.
    pause
    exit /b 1
)

echo [92mJava detecte[0m
java -version
echo.
echo [92mMaven detecte[0m
mvn -version | findstr "Apache Maven"
echo.

REM Build and run
echo [96mCompilation et lancement du jeu...[0m
echo.

mvn clean javafx:run

pause
