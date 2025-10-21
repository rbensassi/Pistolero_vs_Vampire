#!/bin/bash

# Pistolero vs Vampire - Launch Script (Linux/Mac)
# Java 21 with JavaFX

echo "==================================="
echo "  Pistolero vs Vampire - Java 21"
echo "==================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null
then
    echo "❌ Maven n'est pas installé!"
    echo ""
    echo "Installation sur Ubuntu/Debian:"
    echo "  sudo apt update"
    echo "  sudo apt install maven"
    echo ""
    echo "Installation sur Mac:"
    echo "  brew install maven"
    echo ""
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 11 ]; then
    echo "❌ Java 11+ requis. Version actuelle: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java version: $(java -version 2>&1 | head -n 1)"
echo "✅ Maven version: $(mvn -version | head -n 1)"
echo ""

# Build and run
echo "🔨 Compilation et lancement du jeu..."
echo ""

mvn clean javafx:run

# Alternative: if you want to build a JAR first
# mvn clean package
# java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml,javafx.media -jar target/pistolero-vs-vampire-full.jar
