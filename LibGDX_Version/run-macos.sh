#!/bin/bash

# ============================================================================
# 🎮 Pistolero vs Vampire - LibGDX Edition
# Script de lancement optimisé pour macOS
# ============================================================================

# Couleurs pour les messages
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
BOLD='\033[1m'
NC='\033[0m' # No Color

# Variables
GAME_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
JAVA_VERSION_REQUIRED=11

echo ""
echo -e "${CYAN}╔═══════════════════════════════════════════════════════════╗${NC}"
echo -e "${CYAN}║${NC}  ${BOLD}🎮 PISTOLERO VS VAMPIRE - VAMPIRE SURVIVORS EDITION${NC}  ${CYAN}║${NC}"
echo -e "${CYAN}║${NC}          ${GREEN}LibGDX High Performance Version${NC}              ${CYAN}║${NC}"
echo -e "${CYAN}╚═══════════════════════════════════════════════════════════╝${NC}"
echo ""

# Fonction pour afficher les messages
info() {
    echo -e "${BLUE}ℹ${NC}  $1"
}

success() {
    echo -e "${GREEN}✓${NC}  $1"
}

error() {
    echo -e "${RED}✗${NC}  $1"
}

warning() {
    echo -e "${YELLOW}⚠${NC}  $1"
}

# Vérifier que nous sommes bien sur macOS
if [[ "$OSTYPE" != "darwin"* ]]; then
    error "Ce script est conçu pour macOS"
    echo "  Pour Linux, utilisez: ./gradlew desktop:run"
    exit 1
fi

success "Système macOS détecté"

# Vérifier si Java est installé
info "Vérification de Java..."

if ! command -v java &> /dev/null; then
    error "Java n'est pas installé"
    echo ""
    echo "  📥 Pour installer Java sur macOS:"
    echo ""
    echo "  Option 1 - Homebrew (recommandé):"
    echo "    brew install openjdk@21"
    echo ""
    echo "  Option 2 - Téléchargement direct:"
    echo "    https://adoptium.net/temurin/releases/?os=mac"
    echo ""
    exit 1
fi

# Vérifier la version de Java
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d. -f1)

if [ "$JAVA_VERSION" -lt "$JAVA_VERSION_REQUIRED" ]; then
    error "Java $JAVA_VERSION_REQUIRED ou supérieur est requis"
    echo "  Version actuelle: Java $JAVA_VERSION"
    echo "  Installez Java $JAVA_VERSION_REQUIRED+ depuis https://adoptium.net/"
    exit 1
fi

success "Java $JAVA_VERSION détecté"

# Vérifier si Gradle est disponible
info "Vérification de Gradle..."

cd "$GAME_DIR"

# Utiliser le wrapper Gradle si disponible, sinon installer
if [ ! -f "./gradlew" ]; then
    warning "Gradle wrapper non trouvé, installation..."

    if ! command -v gradle &> /dev/null; then
        error "Gradle n'est pas installé"
        echo ""
        echo "  📥 Installation avec Homebrew:"
        echo "    brew install gradle"
        echo ""
        exit 1
    fi

    gradle wrapper
fi

success "Gradle prêt"

# Vérifier les permissions du wrapper
if [ ! -x "./gradlew" ]; then
    info "Ajout des permissions d'exécution..."
    chmod +x ./gradlew
fi

echo ""
echo -e "${BOLD}🚀 Lancement du jeu...${NC}"
echo ""

# Vérifier si c'est la première exécution (pas de .gradle dans le projet)
if [ ! -d ".gradle" ]; then
    warning "Première exécution détectée"
    info "Téléchargement des dépendances LibGDX (~50 MB)..."
    info "Cela peut prendre 1-2 minutes selon votre connexion"
    echo ""
fi

echo -e "${CYAN}╔═══════════════════════════════════════════════╗${NC}"
echo -e "${CYAN}║${NC}  ${BOLD}Nouvelles fonctionnalités:${NC}                ${CYAN}║${NC}"
echo -e "${CYAN}║${NC}  • Tir automatique sur les ennemis        ${CYAN}║${NC}"
echo -e "${CYAN}║${NC}  • Système de niveaux et d'XP             ${CYAN}║${NC}"
echo -e "${CYAN}║${NC}  • 8 types d'upgrades disponibles         ${CYAN}║${NC}"
echo -e "${CYAN}║${NC}  • Vagues infinies d'ennemis              ${CYAN}║${NC}"
echo -e "${CYAN}║${NC}  • Gemmes d'XP magnétiques                ${CYAN}║${NC}"
echo -e "${CYAN}║${NC}  • Performance 60 FPS garanti             ${CYAN}║${NC}"
echo -e "${CYAN}╚═══════════════════════════════════════════════╝${NC}"
echo ""
echo -e "${YELLOW}📖 Consultez README.md, MIGRATION_LIBGDX.md et TROUBLESHOOTING.md${NC}"
echo ""

# Options macOS spécifiques pour de meilleures performances
export JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"

# Désactiver le mode HiDPI si nécessaire (décommenter si problème d'affichage)
# export JAVA_OPTS="$JAVA_OPTS -Dsun.java2d.uiScale=1.0"

# Lancer le jeu
info "Exécution : ./gradlew desktop:run"
echo ""

./gradlew desktop:run

# Vérifier le code de sortie
EXIT_CODE=$?

echo ""
if [ $EXIT_CODE -eq 0 ]; then
    success "Le jeu s'est terminé correctement"
else
    error "Le jeu s'est terminé avec une erreur (code: $EXIT_CODE)"
    echo ""
    echo "  💡 Dépannage:"
    echo "     - Vérifiez que Java est bien installé"
    echo "     - Si erreur de téléchargement: vérifiez votre connexion internet"
    echo "     - Consultez TROUBLESHOOTING.md pour les solutions"
    echo "     - Logs complets ci-dessus"
    echo ""
fi

echo ""
echo -e "${BOLD}👋 Merci d'avoir joué à Pistolero vs Vampire!${NC}"
echo ""

exit $EXIT_CODE
