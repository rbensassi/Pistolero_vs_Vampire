# 🚀 Guide de démarrage rapide - Pistolero vs Vampire (LibGDX)

## Premier lancement sur macOS

### Prérequis
✅ Java 11+ installé
✅ Connexion internet active
✅ Terminal ouvert

### Option 1 : Script automatique (Recommandé)

```bash
# 1. Aller dans le dossier
cd /Users/work/clubmed/Pistolero_vs_Vampire/LibGDX_Version

# 2. Donner les permissions d'exécution
chmod +x run-macos.sh

# 3. Lancer le jeu
./run-macos.sh
```

Le script va automatiquement :
- Vérifier Java
- Télécharger les dépendances (~50 MB au premier lancement)
- Compiler le jeu
- Le lancer

### Option 2 : Commandes Gradle directes

```bash
# 1. Aller dans le dossier
cd /Users/work/clubmed/Pistolero_vs_Vampire/LibGDX_Version

# 2. Build complet (première fois)
./gradlew desktop:build

# 3. Lancer le jeu
./gradlew desktop:run
```

---

## ⏱️ Temps de chargement

### Premier lancement
- **Téléchargement** : 30-60 secondes (~50 MB)
- **Compilation** : 30-60 secondes
- **Total** : 1-2 minutes

### Lancements suivants
- **Instantané** : ~5 secondes (tout est en cache)

---

## ❌ En cas d'erreur

### "ClassNotFoundException"
➡️ **C'est normal !** Les dépendances ne sont pas encore téléchargées.

**Solution :**
```bash
./gradlew desktop:build
./gradlew desktop:run
```

### "Could not resolve dependencies"
➡️ Problème de connexion internet

**Solution :**
1. Vérifier votre connexion : `ping google.com`
2. Vérifier Maven Central : `curl -I https://repo.maven.apache.org/maven2/`
3. Réessayer le build

### "Java not found"
➡️ Java n'est pas installé

**Solution :**
```bash
# Installer avec Homebrew
brew install openjdk@21

# Ou télécharger depuis
# https://adoptium.net/temurin/releases/?os=mac
```

---

## 🎮 Contrôles du jeu

Une fois le jeu lancé :

- **ZQSD / WASD** : Déplacer le personnage
- **Souris** : Le personnage tire automatiquement vers les ennemis
- **ESC** : Quitter

---

## 📊 Mécaniques Vampire Survivors

- 🎯 **Tir automatique** : Vise automatiquement l'ennemi le plus proche
- 💎 **Gemmes d'XP** : Les ennemis lâchent de l'XP à leur mort
- 🧲 **Attraction magnétique** : Les gemmes sont attirées vers le joueur
- 📈 **Système de niveaux** : Gain de niveau en récoltant de l'XP
- ⬆️ **Upgrades** : 8 types d'améliorations disponibles
  - Dégâts d'arme (+10%)
  - Vitesse de tir (+15%)
  - Nombre de projectiles (+1)
  - Vitesse de déplacement (+10%)
  - Points de vie max (+1)
  - Portée de ramassage (+20%)
  - Armure (-10% dégâts reçus)
  - Réduction de cooldown (-10%)
- 🌊 **Vagues infinies** : Difficulté croissante avec le temps

---

## 📚 Documentation complète

- **README.md** : Vue d'ensemble du projet
- **MIGRATION_LIBGDX.md** : Détails de la migration depuis JavaFX
- **INSTALLATION_MACOS.md** : Installation détaillée sur macOS
- **TROUBLESHOOTING.md** : Solutions aux problèmes courants

---

## 🆘 Besoin d'aide ?

1. Consultez **TROUBLESHOOTING.md** pour les problèmes courants
2. Vérifiez les logs dans le terminal
3. Exécutez avec `--info` pour plus de détails :
   ```bash
   ./gradlew desktop:run --info
   ```

---

**Bon jeu ! 🎮**
