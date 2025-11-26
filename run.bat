@echo off
REM Script de lancement pour Pistolero vs Vampire - Windows
REM Avec fonctionnalités Vampire Survivors

echo ============================================================
echo 🎮 Pistolero vs Vampire - Vampire Survivors Edition
echo ============================================================
echo.

REM Vérifier si Java est installé
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java n'est pas installé.
    echo    Téléchargez et installez Java depuis: https://adoptium.net/
    pause
    exit /b 1
)

REM Afficher la version de Java
echo 📟 Version Java:
java -version
echo.

REM Configuration JavaFX
REM Modifier ce chemin selon votre installation JavaFX
set JAVAFX_PATH=C:\Program Files\Java\javafx-sdk-21\lib

REM Vérifier si JavaFX existe
if not exist "%JAVAFX_PATH%" (
    echo ❌ JavaFX non trouvé dans: %JAVAFX_PATH%
    echo.
    echo 📥 Pour installer JavaFX:
    echo    1. Téléchargez JavaFX SDK depuis: https://openjfx.io/
    echo    2. Extrayez le dans C:\Program Files\Java\
    echo    3. Modifiez JAVAFX_PATH dans run.bat si nécessaire
    echo.
    pause
    exit /b 1
)

REM Compiler si nécessaire
if not exist "bin" (
    echo 🔨 Compilation du projet...
    mkdir bin
    javac -d bin -cp "%JAVAFX_PATH%\*;." src\*.java

    if %ERRORLEVEL% NEQ 0 (
        echo ❌ Erreur de compilation
        pause
        exit /b 1
    )
    echo ✅ Compilation réussie
    echo.
)

REM Lancer le jeu
echo 🚀 Lancement du jeu...
echo.
echo 🎯 Nouvelles fonctionnalités Vampire Survivors:
echo    • Tir automatique sur les ennemis
echo    • Système de niveaux et d'XP
echo    • 8 types d'upgrades disponibles
echo    • Vagues infinies d'ennemis
echo    • Gemmes d'XP magnétiques
echo.
echo 📖 Consultez VAMPIRE_SURVIVORS_FEATURES.md pour plus d'infos
echo ============================================================
echo.

java -cp "bin;%JAVAFX_PATH%\*;." --module-path "%JAVAFX_PATH%" ^
     --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media ^
     Main

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ Le jeu s'est terminé avec une erreur
    pause
    exit /b 1
)

echo.
echo 👋 Merci d'avoir joué à Pistolero vs Vampire!
pause
