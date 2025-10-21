#!/bin/bash

# Pistolero vs Vampire - Compilation et Lancement Manuel
# Alternative sans Maven (utilise javac directement)

echo "==================================="
echo "  Pistolero vs Vampire - Java 21"
echo "  Compilation manuelle avec JavaFX"
echo "==================================="
echo ""

# Configuration
JAVAFX_VERSION="21.0.1"
JAVAFX_DIR="javafx-sdk-$JAVAFX_VERSION"
JAVAFX_LIB="$JAVAFX_DIR/lib"
SRC_DIR="src"
BIN_DIR="bin"
RES_DIR="res"

# Vérifier Java
if ! command -v java &> /dev/null; then
    echo "❌ Java n'est pas installé!"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 11 ]; then
    echo "❌ Java 11+ requis. Version actuelle: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java version: $(java -version 2>&1 | head -n 1)"
echo ""

# Télécharger JavaFX si nécessaire
if [ ! -d "$JAVAFX_DIR" ]; then
    echo "📦 Téléchargement de JavaFX SDK $JAVAFX_VERSION..."

    # Détecter le système d'exploitation
    OS=$(uname -s)
    case "$OS" in
        Linux*)
            JAVAFX_URL="https://download2.gluonhq.com/openjfx/21.0.1/openjfx-21.0.1_linux-x64_bin-sdk.zip"
            ;;
        Darwin*)
            JAVAFX_URL="https://download2.gluonhq.com/openjfx/21.0.1/openjfx-21.0.1_macos-x64_bin-sdk.zip"
            ;;
        *)
            echo "❌ Système d'exploitation non supporté: $OS"
            echo "   Téléchargez JavaFX SDK manuellement depuis: https://gluonhq.com/products/javafx/"
            exit 1
            ;;
    esac

    echo "   URL: $JAVAFX_URL"

    # Télécharger
    if command -v wget &> /dev/null; then
        wget -O javafx.zip "$JAVAFX_URL"
    elif command -v curl &> /dev/null; then
        curl -L -o javafx.zip "$JAVAFX_URL"
    else
        echo "❌ wget ou curl requis pour télécharger JavaFX"
        echo "   Installez avec: sudo apt install wget"
        exit 1
    fi

    # Extraire
    echo "📂 Extraction de JavaFX..."
    unzip -q javafx.zip
    rm javafx.zip

    if [ ! -d "$JAVAFX_DIR" ]; then
        echo "❌ Erreur lors de l'extraction de JavaFX"
        exit 1
    fi

    echo "✅ JavaFX SDK téléchargé et extrait"
    echo ""
fi

# Créer le dossier de sortie
mkdir -p "$BIN_DIR"

# Compiler tous les fichiers Java
echo "🔨 Compilation du code source..."
find "$SRC_DIR" -name "*.java" > sources.txt

javac --module-path "$JAVAFX_LIB" \
      --add-modules javafx.controls,javafx.fxml,javafx.media \
      -d "$BIN_DIR" \
      -encoding UTF-8 \
      @sources.txt

if [ $? -ne 0 ]; then
    echo ""
    echo "❌ Erreur de compilation!"
    rm sources.txt
    exit 1
fi

rm sources.txt
echo "✅ Compilation réussie!"
echo ""

# Copier les ressources
echo "📦 Copie des ressources..."
cp -r "$RES_DIR" "$BIN_DIR/"
cp configuration.xml "$BIN_DIR/"
echo "✅ Ressources copiées"
echo ""

# Lancer le jeu
echo "🎮 Lancement du jeu..."
echo ""

cd "$BIN_DIR"
java --module-path "../$JAVAFX_LIB" \
     --add-modules javafx.controls,javafx.fxml,javafx.media \
     --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED \
     Main

echo ""
echo "👋 Jeu terminé!"
