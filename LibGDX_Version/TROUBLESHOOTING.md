# 🔧 Guide de dépannage - LibGDX Version

## ⚠️ PROBLÈME FRÉQUENT : ClassNotFoundException au premier lancement

### Symptôme
```
Erreur : impossible de trouver ou de charger la classe principale com.pistolero.desktop.DesktopLauncher
Causé par : java.lang.ClassNotFoundException: com.pistolero.desktop.DesktopLauncher
```

### Cause
**Les dépendances LibGDX n'ont pas encore été téléchargées.** C'est normal au premier lancement !

### ✅ Solution - Télécharger les dépendances d'abord

**Étape 1 : Vérifier votre connexion internet**
```bash
# Test rapide
ping google.com
curl -I https://repo.maven.apache.org/maven2/
```

**Étape 2 : Télécharger les dépendances (~50 MB)**
```bash
cd /Users/work/clubmed/Pistolero_vs_Vampire/LibGDX_Version

# Télécharger et compiler (prend 1-2 minutes)
./gradlew desktop:build

# Une fois terminé, lancer le jeu
./gradlew desktop:run
```

**OU utiliser le script automatique :**
```bash
./run-macos.sh
```

### Notes importantes
- ⏱️ Le **premier build prend 1-2 minutes** (téléchargement + compilation)
- 📦 Environ **50 MB** de dépendances seront téléchargées
- 🌐 **Connexion internet requise** pour le premier lancement uniquement
- ✅ Les lancements suivants seront instantanés (tout est en cache)

---

## Problème : "task 'run' not found" ✅ RÉSOLU

Ce problème a été corrigé dans le dernier commit. La configuration Gradle inclut maintenant :
- Le plugin `application`
- La tâche `run` configurée
- Le répertoire `assets/`

## Problème : Erreurs de téléchargement des dépendances

### Symptôme
```
Could not resolve com.badlogicgames.gdx:gdx-backend-lwjgl3:1.12.1
Could not GET 'https://repo.maven.apache.org/maven2/...'
```

### Causes possibles

1. **Pas de connexion internet**
2. **Proxy/Firewall bloquant Maven Central**
3. **Cache Gradle corrompu**

### Solutions

#### Solution 1 : Vérifier la connexion internet
```bash
# Tester la connexion à Maven Central
curl -I https://repo.maven.apache.org/maven2/

# Si ça échoue, vérifier votre connexion internet
```

#### Solution 2 : Configuration proxy (si nécessaire)

Créez/éditez `~/.gradle/gradle.properties` :
```properties
systemProp.http.proxyHost=your.proxy.host
systemProp.http.proxyPort=8080
systemProp.https.proxyHost=your.proxy.host
systemProp.https.proxyPort=8080
```

#### Solution 3 : Nettoyer le cache Gradle
```bash
cd LibGDX_Version

# Nettoyer le cache
./gradlew clean --refresh-dependencies

# Supprimer le cache complet (plus radical)
rm -rf ~/.gradle/caches
./gradlew clean build
```

#### Solution 4 : Première build (téléchargement des dépendances)
```bash
# Le premier build télécharge ~50 MB de dépendances
./gradlew desktop:build

# Ensuite le run devrait fonctionner
./gradlew desktop:run
```

#### Solution 5 : Utiliser un VPN/DNS alternatif

Si Maven Central est bloqué :
- Essayer un VPN
- Changer de DNS (Google DNS : 8.8.8.8, Cloudflare : 1.1.1.1)

---

## Problème : "Java not found"

### Solution macOS
```bash
# Vérifier Java
java -version

# Installer avec Homebrew
brew install openjdk@21

# Lier Java
sudo ln -sfn /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk \
     /Library/Java/JavaVirtualMachines/openjdk-21.jdk
```

---

## Problème : "Permission denied" sur les scripts

### Solution
```bash
chmod +x run-macos.sh
chmod +x Lancer-le-Jeu.command
chmod +x gradlew
```

---

## Problème : Build réussit mais le jeu ne démarre pas

### Vérifications

1. **Classes Java manquantes**
```bash
# Vérifier que les classes sont compilées
ls -R core/src/com/pistolero/game/
ls -R desktop/src/com/pistolero/desktop/
```

2. **Répertoire assets manquant**
```bash
# Vérifier
ls assets/

# Si manquant, créer
mkdir -p assets
```

3. **Mauvaise classe principale**
```bash
# Vérifier dans build.gradle
grep mainClassName build.gradle
# Doit afficher : mainClassName = "com.pistolero.desktop.DesktopLauncher"
```

---

## Problème : Écran noir au lancement

### Causes possibles
- Pas d'assets (textures, sprites)
- Erreur dans GameScreen.java
- Problème OpenGL

### Solution
```bash
# Vérifier les logs
./gradlew desktop:run --info

# Chercher des erreurs OpenGL ou "FileNotFoundException"
```

---

## Problème : FPS bas / Lag

### Solutions

1. **Vérifier les specs système**
```bash
# macOS
system_profiler SPDisplaysDataType | grep "Chipset Model"
```

2. **Réduire la résolution**

Éditez `desktop/src/com/pistolero/desktop/DesktopLauncher.java` :
```java
config.setWindowedMode(1280, 720);  // Au lieu de 1920x1080
```

3. **Ajuster les JVM args**

Éditez `build.gradle` (section desktop run) :
```groovy
jvmArgs = [
    '-Xms1024m',    // Plus de RAM
    '-Xmx2048m',    // Plus de RAM
    '-XX:+UseG1GC'
]
```

---

## Problème : Erreurs de compilation Java

### "package com.badlogic.gdx does not exist"
```bash
# Forcer le re-téléchargement des dépendances
./gradlew clean
./gradlew --refresh-dependencies desktop:build
```

### "cannot find symbol" dans le code
- Vérifiez que tous les imports sont corrects
- Vérifiez que les fichiers Java sont dans les bons packages

---

## Commandes utiles pour diagnostiquer

```bash
# Lister toutes les tâches Gradle disponibles
./gradlew tasks --all

# Voir les dépendances
./gradlew dependencies

# Build avec logs détaillés
./gradlew desktop:run --info --stacktrace

# Vérifier la version de Gradle
./gradlew --version

# Nettoyer complètement
./gradlew clean
rm -rf build/ */build/
```

---

## Obtenir de l'aide

### Logs importants à fournir

Quand vous demandez de l'aide, incluez :
```bash
# 1. Version Java
java -version

# 2. Version Gradle
./gradlew --version

# 3. Système d'exploitation
uname -a

# 4. Logs complets de l'erreur
./gradlew desktop:run --stacktrace > error.log 2>&1
```

### Ressources
- **LibGDX Wiki** : https://libgdx.com/wiki/
- **LibGDX Discord** : https://discord.gg/6pgDK9F
- **Stack Overflow** : Tag `libgdx`

---

## Tests de santé du projet

Exécutez ce script pour vérifier l'état :
```bash
#!/bin/bash

echo "🔍 Diagnostic LibGDX Project"
echo ""

echo "✓ Checking Java..."
java -version 2>&1 | grep version

echo ""
echo "✓ Checking Gradle..."
./gradlew --version | grep "Gradle"

echo ""
echo "✓ Checking project structure..."
[ -d "core/src" ] && echo "  ✓ core/src exists"
[ -d "desktop/src" ] && echo "  ✓ desktop/src exists"
[ -d "assets" ] && echo "  ✓ assets exists"
[ -f "build.gradle" ] && echo "  ✓ build.gradle exists"

echo ""
echo "✓ Checking executables..."
[ -x "gradlew" ] && echo "  ✓ gradlew is executable"
[ -x "run-macos.sh" ] && echo "  ✓ run-macos.sh is executable"

echo ""
echo "✓ Testing Gradle tasks..."
./gradlew tasks | grep "desktop:run" && echo "  ✓ run task available"

echo ""
echo "Done!"
```

---

**Dernière mise à jour** : Correction de la configuration Gradle et ajout de la tâche `run`
