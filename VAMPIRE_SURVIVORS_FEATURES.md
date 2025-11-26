# Vampire Survivors Style Features

Ce document décrit toutes les nouvelles fonctionnalités inspirées de **Vampire Survivors** ajoutées au jeu Pistolero vs Vampire.

## 🎮 Vue d'ensemble

Le jeu a été transformé en un jeu de survie inspiré de Vampire Survivors avec :
- Système de progression (XP et niveaux)
- Tir automatique
- Vagues d'ennemis progressives
- Système d'upgrades
- Gemmes d'XP
- HUD amélioré

---

## 📊 Systèmes Principaux

### 1. Système de Niveau et XP

**Classes concernées:** `LevelSystem.java`

Le joueur gagne de l'XP en tuant des ennemis et monte de niveau.

**Fonctionnalités:**
- Progression exponentielle : chaque niveau nécessite plus d'XP
- Formule : `BASE_XP * (SCALING_FACTOR ^ (level - 1))`
- Valeur de base : 10 XP pour le niveau 2
- Facteur d'échelle : 1.5x par niveau
- Menu d'upgrade automatique au level up

**Utilisation:**
```java
LevelSystem levelSystem = new LevelSystem();
boolean leveledUp = levelSystem.addXP(5); // Ajouter 5 XP

if (leveledUp) {
    // Le joueur a monté de niveau !
}
```

---

### 2. Gemmes d'XP

**Classes concernées:** `XPGem.java`, `XPGemView.java`

Les ennemis vaincus laissent tomber des gemmes d'XP qui sont attirées vers le joueur.

**Fonctionnalités:**
- Effet magnétique : les gemmes sont attirées dans un rayon configurable
- 3 couleurs selon la valeur :
  - **Vert clair** : 1-4 XP
  - **Cyan** : 5-9 XP
  - **Or** : 10+ XP
- Rayon de ramassage augmentable avec l'upgrade "Magnet"

**Valeurs par défaut:**
- Rayon magnétique : 100 pixels
- Force magnétique : 200 pixels/seconde
- XP par kill de vampire : 5 XP

---

### 3. Système d'Upgrades

**Classes concernées:** `Upgrade.java`, `UpgradeManager.java`, `UpgradeMenu.java`

Quand le joueur monte de niveau, il peut choisir parmi 3 upgrades aléatoires.

**Types d'upgrades disponibles:**

| Upgrade | Nom | Description | Niveaux Max | Effet par niveau |
|---------|-----|-------------|-------------|------------------|
| WEAPON_DAMAGE | Might | Augmente les dégâts | 5 | +10% |
| WEAPON_SPEED | Fire Rate | Augmente la cadence de tir | 5 | +15% |
| WEAPON_PROJECTILE_COUNT | Multishot | Ajoute des projectiles | 3 | +1 projectile |
| MOVE_SPEED | Speed | Augmente la vitesse | 5 | +10% |
| MAX_HEALTH | Max Health | Augmente la vie max | 5 | +1 PV |
| PICKUP_RANGE | Magnet | Augmente le rayon de ramassage | 5 | +20% |
| ARMOR | Armor | Réduit les dégâts reçus | 3 | -10% dégâts (max 75%) |
| COOLDOWN_REDUCTION | Cooldown | Réduit les temps de recharge | 5 | -10% |

---

### 4. Tir Automatique

**Classes concernées:** `AutoFireSystem.java`, `AutoBullet.java`

Le joueur tire automatiquement sur les ennemis les plus proches.

**Fonctionnalités:**
- Cible automatique : vise l'ennemi le plus proche
- Cadence de base : 2 tirs/seconde
- Amélioration avec l'upgrade "Fire Rate"
- Projectiles multiples avec "Multishot"
- Spread automatique pour les tirs multiples

**Caractéristiques:**
- Vitesse des projectiles : 400 pixels/seconde
- Angle de spread : 0.2 radians entre chaque projectile
- Taille des projectiles : 10x10 pixels

---

### 5. Système de Vagues

**Classes concernées:** `WaveManager.java`

Les ennemis apparaissent par vagues de plus en plus difficiles.

**Mécaniques:**
- **Vagues programmées** : toutes les 10 secondes
- **Spawn continu** : 1-3 ennemis toutes les 2 secondes
- **Scaling progressif** :
  - Nombre d'ennemis : `baseCount * (1.2 ^ wave)`
  - Santé : +1 PV tous les 5 vagues
  - Dégâts : +1 dégât toutes les 10 vagues
  - Vitesse : +2 pixels/seconde par vague

**Spawn des ennemis:**
- Apparaissent aux bords de l'écran (haut, bas, gauche, droite)
- Maximum de 100 ennemis par vague
- Position aléatoire sur le bord sélectionné

---

### 6. HUD Amélioré

**Classes concernées:** `VampireSurvivorsHUD.java`

Affichage des statistiques en jeu.

**Informations affichées:**
- Niveau actuel
- Barre de progression XP
- Barre de vie
- Timer (temps de survie)
- Numéro de vague actuelle
- Nombre d'ennemis vivants

**Mise à jour:**
Le HUD doit être mis à jour manuellement dans la boucle de jeu :
```java
vampireSurvivorsHUD.update();
```

---

## 🔧 Intégration dans le Jeu

### Modifications de `Pistoleros.java`

Nouvelles propriétés ajoutées :
- `LevelSystem levelSystem` - Gestion XP/niveau
- `UpgradeManager upgradeManager` - Gestion des upgrades
- `AutoFireSystem autoFireSystem` - Tir automatique
- `double damageMultiplier` - Multiplicateur de dégâts
- `double armorReduction` - Réduction des dégâts reçus
- `double pickupRange` - Rayon de ramassage XP

Nouvelles méthodes :
```java
boolean addXP(int xp)                      // Ajouter de l'XP
void applyUpgrade(Upgrade upgrade)         // Appliquer un upgrade
int getDammage()                           // Retourne dégâts avec multiplicateur
```

---

### Modifications de `Container.java`

Nouvelles propriétés :
- `ArrayList<XPGem> xpGems` - Liste des gemmes d'XP
- `WaveManager waveManager` - Gestionnaire de vagues
- `double continuousSpawnTimer` - Timer pour spawn continu

Nouvelles méthodes privées :
```java
void updateAutoFire(double speed)         // Gère le tir auto
void updateXPGems(double speed)           // Met à jour les gemmes
void updateWaves(double speed)            // Gère les vagues
void updateContinuousSpawn(double speed)  // Spawn continu
void spawnXPGem(double x, double y, int xp) // Créer une gemme
```

---

## 🎯 Équilibrage

### Progression du joueur

**Niveau 1:**
- Dégâts : 50
- Cadence : 2 tirs/sec
- Vitesse : 100
- Projectiles : 1

**Niveau 10 (exemple avec upgrades):**
- Dégâts : ~75-100 (avec Might x5)
- Cadence : ~4.5 tirs/sec (avec Fire Rate x5)
- Vitesse : ~150 (avec Speed x5)
- Projectiles : 4 (avec Multishot x3)

### Progression des ennemis

**Vague 1:**
- 5 vampires
- 3 PV chacun
- 1 dégât
- Vitesse 100

**Vague 10:**
- ~31 vampires
- 5 PV chacun
- 2 dégâts
- Vitesse 120

**Vague 20:**
- ~192 vampires (cap à 100/vague)
- 7 PV chacun
- 3 dégâts
- Vitesse 140

---

## 📝 Notes d'implémentation

### Points importants

1. **Auto-fire vs Tir manuel** : Le tir automatique et le tir manuel (espace) coexistent
2. **Pause automatique** : Le jeu se met en pause lors du menu d'upgrade
3. **Spawn d'ennemis** : Les ennemis peuvent spawner hors écran et se diriger vers le joueur
4. **Gemmes d'XP** : Ne disparaissent jamais, restent jusqu'à être ramassées
5. **Upgrades** : Une fois max level, n'apparaissent plus dans le menu

### Améliorations futures possibles

- [ ] Armes secondaires (comme dans Vampire Survivors)
- [ ] Objets passifs (cercle de protection, aura de feu, etc.)
- [ ] Boss toutes les 5 vagues
- [ ] Évolution d'armes (fusion de 2 upgrades)
- [ ] Coffres et power-ups temporaires
- [ ] Différents types d'ennemis avec comportements uniques
- [ ] Sauvegarde de progression
- [ ] Méta-progression (déblocables permanents)

---

## 🚀 Comment jouer

1. Les ennemis apparaissent automatiquement par vagues
2. Le joueur tire automatiquement sur les ennemis proches
3. Tuer des ennemis fait apparaître des gemmes d'XP
4. Ramasser des gemmes fait gagner de l'XP
5. Monter de niveau ouvre un menu pour choisir un upgrade
6. Survivre le plus longtemps possible !

**Objectif:** Tenir le plus longtemps possible face aux vagues infinies d'ennemis !

---

## 🎨 Interface utilisateur

### HUD (en haut à gauche)
```
Level: 5
Experience [████████░░] 80%
Health [████████░░] 80%
Time: 2:34
Wave: 3
Enemies: 47
```

### Menu d'upgrade (centre écran)
```
┌─────────────────────────────┐
│       LEVEL UP!             │
│         Level 5             │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │ Might    Level 2/5      │ │
│ │ Increases damage by 10% │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ Speed    Level 1/5      │ │
│ │ Increases move speed... │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ Magnet   Level 0/5      │ │
│ │ Increases pickup range..│ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

---

## 📚 Références

Inspiré de **Vampire Survivors** par poncle :
- Steam : https://store.steampowered.com/app/1794680/Vampire_Survivors/
- Wiki : https://vampire-survivors.fandom.com/

---

**Développé par:** Claude AI
**Date:** 2025
**Version:** 1.0 - Vampire Survivors Edition
