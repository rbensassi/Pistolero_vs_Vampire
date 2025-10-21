@echo off
REM Pistolero vs Vampire - Compilation et Lancement Manuel (Windows)

echo ===================================
echo   Pistolero vs Vampire - Java 21
echo   Compilation manuelle avec JavaFX
echo ===================================
echo.

REM Configuration
set JAVAFX_VERSION=21.0.1
set JAVAFX_DIR=javafx-sdk-%JAVAFX_VERSION%
set JAVAFX_LIB=%JAVAFX_DIR%\lib
set SRC_DIR=src
set BIN_DIR=bin
set RES_DIR=res

REM Vérifier Java
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [91mJava n'est pas installe![0m
    pause
    exit /b 1
)

echo [92mJava detecte[0m
java -version
echo.

REM Vérifier si JavaFX existe
if not exist "%JAVAFX_DIR%" (
    echo [93mJavaFX SDK non trouve![0m
    echo.
    echo Telechargez JavaFX SDK %JAVAFX_VERSION% pour Windows depuis:
    echo https://gluonhq.com/products/javafx/
    echo.
    echo Extraire le ZIP et placez le dossier ici:
    echo %CD%\%JAVAFX_DIR%
    echo.
    pause
    exit /b 1
)

echo [92mJavaFX SDK trouve: %JAVAFX_DIR%[0m
echo.

REM Créer le dossier de sortie
if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"

REM Compiler
echo [96mCompilation du code source...[0m
dir /s /B "%SRC_DIR%\*.java" > sources.txt

javac --module-path "%JAVAFX_LIB%" ^
      --add-modules javafx.controls,javafx.fxml,javafx.media ^
      -d "%BIN_DIR%" ^
      -encoding UTF-8 ^
      @sources.txt

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [91mErreur de compilation![0m
    del sources.txt
    pause
    exit /b 1
)

del sources.txt
echo [92mCompilation reussie![0m
echo.

REM Copier les ressources
echo [96mCopie des ressources...[0m
xcopy /E /I /Y "%RES_DIR%" "%BIN_DIR%\%RES_DIR%" >nul
copy /Y configuration.xml "%BIN_DIR%\" >nul
echo [92mRessources copiees[0m
echo.

REM Lancer le jeu
echo [96mLancement du jeu...[0m
echo.

cd "%BIN_DIR%"
java --module-path "..\%JAVAFX_LIB%" ^
     --add-modules javafx.controls,javafx.fxml,javafx.media ^
     --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED ^
     Main

echo.
echo Jeu termine!
pause
