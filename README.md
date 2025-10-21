# Pistolero vs Vampire 🔫🧛

Un shoot'em up top-down avec des effets visuels de niveau AAA!

## 📸 Captures d'écran

### Menu Principal
![Menu](Menu.PNG)

### Paramètres
![Settings](settings.PNG)

### En Jeu
![In-Game](in-game.PNG)

---

## 🚀 Lancement Rapide

### Option 1: Scripts Automatiques (Recommandé)

**Linux/Mac**:
```bash
chmod +x compile-and-run.sh
./compile-and-run.sh
```

**Windows**:
```cmd
compile-and-run.bat
```

### Option 2: Avec Maven (si disponible)
```bash
mvn clean javafx:run
```

---

## 📋 Prérequis

- **Java 21** ou supérieur
- **JavaFX 21** (téléchargé automatiquement par le script)

### Installation Java 21

**Ubuntu/Debian**:
```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

**Mac**:
```bash
brew install openjdk@21
```

**Windows**: [Télécharger Java 21](https://adoptium.net/)

---

## ✨ Nouveautés Version 2.0 (2025)

### Améliorations Majeures du Game Feel

- ✅ **Screen Shake** - Tremblement dynamique de l'écran
- ✅ **Muzzle Flash** - Flash lumineux sur les tirs
- ✅ **Particules de Sang** - Effets physiques réalistes
- ✅ **Traînées de Balles** - Meilleure visibilité
- ✅ **Knockback Physics** - Recul des ennemis
- ✅ **Death Animations** - Rotations et scaling
- ✅ **Hit Pause** - Freeze frame sur impacts
- ✅ **Camera Zoom** - Punch cinématique
- ✅ **Low Health Vignette** - Alerte visuelle

📖 **Détails complets**: [GAME_FEEL_IMPROVEMENTS.md](GAME_FEEL_IMPROVEMENTS.md)

### Améliorations Techniques

- 🔧 Migration vers **Java 21**
- 📦 Configuration **Maven** moderne
- 🎮 Support **JavaFX 21**
- 📜 Scripts de lancement automatiques
- 📚 Documentation complète

---

## 🎮 Contrôles

**Touches par défaut (AZERTY)**:
- **Z** - Haut
- **S** - Bas
- **Q** - Gauche
- **D** - Droite
- **P** - Tirer
- **ESC** - Pause

Modifiez `configuration.xml` pour changer les touches.

---

## 📚 Documentation

- **[INSTALLATION.md](INSTALLATION.md)** - Guide d'installation détaillé
- **[GAME_FEEL_IMPROVEMENTS.md](GAME_FEEL_IMPROVEMENTS.md)** - Documentation des améliorations
- **pom.xml** - Configuration Maven

---

## 🛠️ Développement

### Structure du Projet
```
Pistolero_vs_Vampire/
├── src/                 # Code source Java (38 classes)
├── res/                 # Ressources (images, sons, fonts)
├── pom.xml              # Configuration Maven
├── compile-and-run.sh   # Compilation manuelle (Linux/Mac)
├── compile-and-run.bat  # Compilation manuelle (Windows)
├── run.sh               # Lancement Maven (Linux/Mac)
└── run.bat              # Lancement Maven (Windows)
```

### Compiler Manuellement
```bash
# Avec Maven
mvn clean compile

# Sans Maven (script automatique)
./compile-and-run.sh
```

---

## 🐛 Dépannage

Consultez [INSTALLATION.md](INSTALLATION.md) pour:
- Résolution des problèmes courants
- Configuration JavaFX
- Paramètres de performance
- Debug et logs

---

## 👥 Auteurs

**Développeurs Originaux**:
- Labadens Lucas
- Ben Sassi Rached

**Améliorations Version 2.0**:
- Migration Java 21 + Effets visuels AAA par Claude AI

---

## 📄 License

Projet académique - Utilisation libre

---

## 🎯 Quick Start (TL;DR)

```bash
# Linux/Mac
./compile-and-run.sh

# Windows
compile-and-run.bat

# Avec Maven
mvn javafx:run
```

**C'est tout!** Le jeu se lance automatiquement. 🎮

---

Bon jeu! 🔫🧛‍♂️💀
