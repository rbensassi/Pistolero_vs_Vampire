# Installation et Lancement - Pistolero vs Vampire (Java 21)

## 📋 Prérequis

### 1. Java 21 (ou supérieur)
**Vérifier votre version**:
```bash
java -version
```

**Installation**:
- **Ubuntu/Debian**:
  ```bash
  sudo apt update
  sudo apt install openjdk-21-jdk
  ```

- **Mac**:
  ```bash
  brew install openjdk@21
  ```

- **Windows**: Téléchargez depuis [Adoptium](https://adoptium.net/) (Eclipse Temurin 21)

### 2. Maven
**Vérifier votre version**:
```bash
mvn -version
```

**Installation**:
- **Ubuntu/Debian**:
  ```bash
  sudo apt update
  sudo apt install maven
  ```

- **Mac**:
  ```bash
  brew install maven
  ```

- **Windows**:
  - Avec Chocolatey: `choco install maven`
  - Ou téléchargez depuis [maven.apache.org](https://maven.apache.org/download.cgi)

---

## 🚀 Méthode 1: Lancement Rapide (Recommandé)

### Linux / Mac
```bash
chmod +x run.sh
./run.sh
```

### Windows
Double-cliquez sur `run.bat` ou exécutez:
```cmd
run.bat
```

---

## 🛠️ Méthode 2: Commandes Maven Manuelles

### Compiler et lancer directement
```bash
mvn clean javafx:run
```

### Compiler uniquement
```bash
mvn clean compile
```

### Créer un JAR exécutable
```bash
mvn clean package
```

Le JAR sera créé dans `target/pistolero-vs-vampire-full.jar`

### Lancer le JAR (nécessite JavaFX)
```bash
java -jar target/pistolero-vs-vampire-full.jar
```

---

## 📦 Structure du Projet

```
Pistolero_vs_Vampire/
├── src/                    # Code source Java
├── res/                    # Ressources (images, sons)
├── pom.xml                 # Configuration Maven
├── run.sh                  # Script de lancement Linux/Mac
├── run.bat                 # Script de lancement Windows
├── configuration.xml       # Configuration des touches
└── GAME_FEEL_IMPROVEMENTS.md  # Documentation des améliorations
```

---

## 🎮 Configuration

### Touches par défaut (AZERTY)
Modifiez `configuration.xml` pour changer les touches:
- **Z** - Haut
- **S** - Bas
- **Q** - Gauche
- **D** - Droite
- **P** - Tirer
- **ESC** - Pause

---

## 🐛 Dépannage

### Erreur: "package javafx.* does not exist"
Maven n'a pas téléchargé JavaFX. Essayez:
```bash
mvn clean install
mvn javafx:run
```

### Erreur: "JavaFX runtime components are missing"
Utilisez Maven pour lancer:
```bash
mvn javafx:run
```

Ou ajoutez les modules JavaFX manuellement:
```bash
java --module-path /path/to/javafx-sdk/lib \
     --add-modules javafx.controls,javafx.fxml,javafx.media \
     -jar target/pistolero-vs-vampire-full.jar
```

### Erreur: "Java version mismatch"
Assurez-vous d'utiliser Java 21+:
```bash
java -version  # Doit afficher 21 ou supérieur
update-alternatives --config java  # Linux: choisir Java 21
```

### Problèmes de performance
- Augmentez la mémoire JVM:
  ```bash
  export MAVEN_OPTS="-Xmx2g"
  mvn javafx:run
  ```

### Sons ne fonctionnent pas
Vérifiez que les fichiers audio dans `res/` sont présents:
- `res/tire.mp3`
- `res/vampire.mp3`
- `res/musique_menu_final.mp3`

---

## 🎯 Développement

### Nettoyer le projet
```bash
mvn clean
```

### Recompiler après modifications
```bash
mvn compile
```

### Lancer avec debug
```bash
mvn javafx:run -X
```

### Créer une distribution
```bash
mvn clean package
# Le JAR sera dans target/
```

---

## 📊 Nouvelles Fonctionnalités (Version 2.0)

✨ **Améliorations majeures du game feel**:
- Screen shake dynamique
- Muzzle flash sur les tirs
- Particules de sang
- Traînées de balles
- Knockback physics
- Animations de mort améliorées
- Hit pause (freeze frame)
- Camera zoom punch
- Vignette de santé basse

Consultez `GAME_FEEL_IMPROVEMENTS.md` pour les détails complets.

---

## 🆘 Support

Si vous rencontrez des problèmes:
1. Vérifiez que Java 21+ est installé
2. Vérifiez que Maven est installé
3. Essayez `mvn clean install` puis `mvn javafx:run`
4. Consultez les logs pour les erreurs spécifiques

---

## ⚡ Quick Start (TL;DR)

```bash
# 1. Installer Java 21 et Maven
sudo apt install openjdk-21-jdk maven  # Ubuntu
brew install openjdk@21 maven          # Mac

# 2. Lancer le jeu
./run.sh        # Linux/Mac
run.bat         # Windows

# Ou directement avec Maven
mvn clean javafx:run
```

---

## 📝 Changelog

### Version 2.0 (2025)
- ✅ Migration vers Java 21
- ✅ Configuration Maven moderne
- ✅ Ajout de JavaFX 21
- ✅ Améliorations massives du game feel
- ✅ Scripts de lancement automatiques
- ✅ Documentation complète

### Version 1.0
- 🎮 Jeu de base fonctionnel
- 🧛 Système de vampires
- 🔫 Mécanique de tir
- 🗺️ Système de maps

---

Bon jeu! 🎮🔫🧛
