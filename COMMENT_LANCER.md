# 🎮 Comment lancer Pistolero vs Vampire

Guide d'installation et de lancement du jeu avec les nouvelles fonctionnalités Vampire Survivors.

---

## 📋 Prérequis

### 1. Java 11 ou supérieur

**Vérifier si Java est installé :**
```bash
java -version
```

**Si Java n'est pas installé :**

- **Windows/Mac/Linux** : Téléchargez depuis [Adoptium](https://adoptium.net/)
- **Linux (Ubuntu/Debian)** :
  ```bash
  sudo apt-get update
  sudo apt-get install openjdk-21-jdk
  ```

### 2. JavaFX

JavaFX est nécessaire pour l'interface graphique.

#### Linux (Ubuntu/Debian)
```bash
sudo apt-get install openjfx
```

#### macOS
1. Téléchargez JavaFX SDK depuis [openjfx.io](https://openjfx.io/)
2. Extrayez dans `/Library/Java/JavaVirtualMachines/javafx-sdk/`

#### Windows
1. Téléchargez JavaFX SDK depuis [openjfx.io](https://openjfx.io/)
2. Extrayez dans `C:\Program Files\Java\javafx-sdk-21\`
3. Modifiez `JAVAFX_PATH` dans `run.bat` si nécessaire

---

## 🚀 Lancement

### Linux / macOS

```bash
./run.sh
```

Si vous obtenez une erreur de permission :
```bash
chmod +x run.sh
./run.sh
```

### Windows

Double-cliquez sur `run.bat` ou exécutez dans le terminal :
```cmd
run.bat
```

---

## 🛠️ Compilation manuelle

Si les scripts ne fonctionnent pas, vous pouvez compiler et lancer manuellement.

### Compilation

**Linux/macOS :**
```bash
mkdir -p bin
javac -d bin -cp "/usr/share/java/*:." src/*.java
```

**Windows :**
```cmd
mkdir bin
javac -d bin -cp "C:\Program Files\Java\javafx-sdk-21\lib\*;." src\*.java
```

### Lancement

**Linux/macOS :**
```bash
java -cp "bin:/usr/share/java/*:." \
     --module-path "/usr/share/java/*" \
     --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media \
     Main
```

**Windows :**
```cmd
java -cp "bin;C:\Program Files\Java\javafx-sdk-21\lib\*;." ^
     --module-path "C:\Program Files\Java\javafx-sdk-21\lib" ^
     --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media ^
     Main
```

---

## 🎯 Utilisation depuis Eclipse

Si vous utilisez Eclipse (comme mentionné dans le README original) :

1. **Importer le projet**
   - File → Import → Existing Projects into Workspace
   - Sélectionnez le dossier du jeu

2. **Configurer JavaFX**
   - Right-click sur le projet → Properties
   - Java Build Path → Libraries → Add External JARs
   - Ajoutez tous les JARs JavaFX depuis votre installation

3. **Lancer**
   - Right-click sur `Main.java` → Run As → Java Application

---

## ❌ Dépannage

### Erreur : "JavaFX not found"

**Solution :** JavaFX n'est pas dans le classpath
- Vérifiez que JavaFX est installé
- Vérifiez le chemin dans le script de lancement
- Linux : installez `openjfx` avec apt

### Erreur : "module javafx.controls not found"

**Solution :** Modules JavaFX manquants
- Ajoutez `--add-modules javafx.controls,javafx.graphics,javafx.media` aux arguments Java

### Erreur de compilation : "package javafx.application does not exist"

**Solution :** JavaFX n'est pas dans le classpath de compilation
- Ajoutez `-cp "/chemin/vers/javafx/*"` à la commande javac

### Le jeu ne démarre pas (Linux)

**Solution :** Problème de display
- Vérifiez que vous avez un serveur X en cours d'exécution
- Essayez : `export DISPLAY=:0` avant de lancer

### Fichiers audio ne jouent pas

**Solution :** JavaFX Media manquant
- Vérifiez que le module `javafx.media` est bien ajouté
- Certains codecs peuvent nécessiter des bibliothèques supplémentaires

---

## 🎮 Nouvelles Fonctionnalités

Une fois le jeu lancé, profitez des nouvelles mécaniques Vampire Survivors :

- **Tir automatique** : Le joueur tire automatiquement sur les ennemis
- **XP et Niveaux** : Collectez les gemmes d'XP pour monter de niveau
- **Upgrades** : Choisissez parmi 8 types d'améliorations différentes
- **Vagues infinies** : Survivez face à des hordes croissantes d'ennemis
- **HUD amélioré** : Suivez vos stats en temps réel

📖 **Consultez `VAMPIRE_SURVIVORS_FEATURES.md` pour la documentation complète**

---

## 📝 Configuration système recommandée

- **OS** : Windows 10+, macOS 10.14+, Ubuntu 20.04+
- **Java** : OpenJDK 11 ou supérieur
- **RAM** : 2 Go minimum
- **Résolution** : 1280x720 minimum

---

## 🆘 Support

Si vous rencontrez des problèmes :

1. Vérifiez que Java et JavaFX sont correctement installés
2. Consultez ce guide de dépannage
3. Vérifiez les logs d'erreur dans le terminal
4. Assurez-vous que tous les fichiers du projet sont présents

---

**Bon jeu ! 🎮🧛‍♂️**
