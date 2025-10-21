# 🚀 Guide de Démarrage Rapide - Pistolero vs Vampire

## Option 1: Script Automatique (RECOMMANDÉ) ⭐

Le script télécharge JavaFX automatiquement et lance le jeu!

### Linux / Mac
```bash
chmod +x compile-and-run.sh
./compile-and-run.sh
```

### Windows
Double-cliquez sur `compile-and-run.bat`

---

## Option 2: Avec Maven

Si vous avez Maven installé:

```bash
mvn clean javafx:run
```

**Installation Maven**:
- Ubuntu: `sudo apt install maven`
- Mac: `brew install maven`
- Windows: https://maven.apache.org/download.cgi

---

## Option 3: Manuel (JavaFX déjà installé)

### 1. Télécharger JavaFX SDK

Allez sur: https://gluonhq.com/products/javafx/

Téléchargez **JavaFX 21.0.1 SDK** pour votre OS:
- Linux: `openjfx-21.0.1_linux-x64_bin-sdk.zip`
- Mac: `openjfx-21.0.1_macos-x64_bin-sdk.zip`
- Windows: `openjfx-21.0.1_windows-x64_bin-sdk.zip`

### 2. Extraire JavaFX

Extrayez le ZIP dans le dossier du projet:
```
Pistolero_vs_Vampire/
├── javafx-sdk-21.0.1/
│   └── lib/
│       ├── javafx.base.jar
│       ├── javafx.controls.jar
│       └── ...
├── src/
└── ...
```

### 3. Lancer le script
```bash
# Linux/Mac
./compile-and-run.sh

# Windows
compile-and-run.bat
```

---

## 🐛 Problèmes Courants

### "Java n'est pas installé"
```bash
# Ubuntu/Debian
sudo apt install openjdk-21-jdk

# Mac
brew install openjdk@21

# Windows
# Télécharger depuis: https://adoptium.net/
```

### "JavaFX runtime components are missing"
Utilisez le script `compile-and-run.sh` qui gère JavaFX automatiquement!

### "Permission denied" (Linux/Mac)
```bash
chmod +x compile-and-run.sh
```

### Problème de réseau lors du téléchargement
1. Téléchargez JavaFX SDK manuellement: https://gluonhq.com/products/javafx/
2. Extrayez dans le dossier du projet
3. Relancez le script

---

## ✅ Vérification de l'Installation

### Vérifier Java
```bash
java -version
# Doit afficher: openjdk version "21.x.x" ou supérieur
```

### Vérifier Maven (optionnel)
```bash
mvn -version
# Doit afficher: Apache Maven 3.x.x
```

---

## 🎮 Une Fois Lancé

Le jeu devrait s'ouvrir avec:
- Un menu principal
- Options de paramètres (vitesse, musique)
- Sélection de carte
- Jeu complet avec tous les effets visuels!

**Contrôles par défaut**:
- Z/Q/S/D - Déplacement
- P - Tirer
- ESC - Pause

---

## 📚 Plus d'Infos

- **Installation détaillée**: [INSTALLATION.md](INSTALLATION.md)
- **Documentation complète**: [README.md](README.md)
- **Améliorations du jeu**: [GAME_FEEL_IMPROVEMENTS.md](GAME_FEEL_IMPROVEMENTS.md)

---

## 💡 Astuce

**Première fois?** Utilisez simplement:
```bash
./compile-and-run.sh    # Linux/Mac
compile-and-run.bat     # Windows
```

Le script fait TOUT automatiquement! 🎯
