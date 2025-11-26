#!/bin/bash

# Script de lancement pour Pistolero vs Vampire
# Avec fonctionnalités Vampire Survivors

echo "🎮 Lancement de Pistolero vs Vampire - Vampire Survivors Edition"
echo "=================================================================="

# Vérifier si Java est installé
if ! command -v java &> /dev/null; then
    echo "❌ Java n'est pas installé. Veuillez installer Java 11 ou supérieur."
    exit 1
fi

# Vérifier la version de Java
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d. -f1)
if [ "$JAVA_VERSION" -lt 11 ]; then
    echo "❌ Java 11 ou supérieur est requis. Version actuelle: $JAVA_VERSION"
    exit 1
fi

# Détecter le système d'exploitation
OS=$(uname -s)
echo "📟 Système d'exploitation détecté: $OS"

# Configuration du classpath JavaFX selon l'OS
if [ "$OS" = "Linux" ]; then
    # Linux
    if [ -d "/usr/share/java" ]; then
        JAVAFX_PATH="/usr/share/java/*"
    elif [ -d "/usr/share/openjfx/lib" ]; then
        JAVAFX_PATH="/usr/share/openjfx/lib/*"
    else
        echo "⚠️  JavaFX non trouvé dans les emplacements standards."
        echo "    Installez JavaFX avec: sudo apt-get install openjfx"
        echo "    Ou téléchargez depuis: https://openjfx.io/"
        exit 1
    fi
elif [ "$OS" = "Darwin" ]; then
    # macOS
    if [ -d "/Library/Java/JavaVirtualMachines/javafx-sdk/lib" ]; then
        JAVAFX_PATH="/Library/Java/JavaVirtualMachines/javafx-sdk/lib/*"
    else
        echo "⚠️  JavaFX non trouvé."
        echo "    Téléchargez JavaFX SDK depuis: https://openjfx.io/"
        exit 1
    fi
else
    # Windows (via Git Bash ou WSL)
    echo "⚠️  Pour Windows, veuillez utiliser run.bat"
    exit 1
fi

# Compiler si nécessaire
if [ ! -d "bin" ] || [ "src/*.java" -nt "bin" ]; then
    echo "🔨 Compilation du projet..."
    mkdir -p bin
    javac -d bin -cp "$JAVAFX_PATH:." src/*.java

    if [ $? -ne 0 ]; then
        echo "❌ Erreur de compilation"
        exit 1
    fi
    echo "✅ Compilation réussie"
fi

# Lancer le jeu
echo "🚀 Lancement du jeu..."
echo ""
echo "🎯 Nouvelles fonctionnalités Vampire Survivors:"
echo "   • Tir automatique sur les ennemis"
echo "   • Système de niveaux et d'XP"
echo "   • 8 types d'upgrades disponibles"
echo "   • Vagues infinies d'ennemis"
echo "   • Gemmes d'XP magnétiques"
echo ""
echo "📖 Consultez VAMPIRE_SURVIVORS_FEATURES.md pour plus d'infos"
echo "=================================================================="
echo ""

# Lancer avec JavaFX dans le module path
java -cp "bin:$JAVAFX_PATH:." --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media \
     Main

# Vérifier le code de sortie
if [ $? -ne 0 ]; then
    echo ""
    echo "❌ Le jeu s'est terminé avec une erreur"
    exit 1
fi

echo ""
echo "👋 Merci d'avoir joué à Pistolero vs Vampire!"
