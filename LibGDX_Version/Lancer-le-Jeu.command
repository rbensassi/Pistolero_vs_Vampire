#!/bin/bash

# ============================================================================
# 🎮 Pistolero vs Vampire - Double-click launcher pour macOS
# Ce fichier peut être double-cliqué depuis le Finder
# ============================================================================

# Obtenir le répertoire où se trouve ce script
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Se déplacer dans le répertoire du script
cd "$SCRIPT_DIR"

# Lancer le script principal
exec ./run-macos.sh
