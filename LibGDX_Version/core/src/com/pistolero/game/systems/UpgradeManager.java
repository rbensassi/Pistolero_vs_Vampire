package com.pistolero.game.systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Manages all available upgrades and applies them to the player
 */
public class UpgradeManager {
	private HashMap<Upgrade.UpgradeType, Upgrade> availableUpgrades;
	private Random random;

	public UpgradeManager() {
		this.availableUpgrades = new HashMap<>();
		this.random = new Random();
		initializeUpgrades();
	}

	/**
	 * Initialize all available upgrades
	 */
	private void initializeUpgrades() {
		// Weapon upgrades
		availableUpgrades.put(Upgrade.UpgradeType.WEAPON_DAMAGE,
			new Upgrade(Upgrade.UpgradeType.WEAPON_DAMAGE, "Might",
				"Increases damage by 10%", 0.1, 5));

		availableUpgrades.put(Upgrade.UpgradeType.WEAPON_SPEED,
			new Upgrade(Upgrade.UpgradeType.WEAPON_SPEED, "Fire Rate",
				"Increases fire rate by 15%", 0.15, 5));

		availableUpgrades.put(Upgrade.UpgradeType.WEAPON_PROJECTILE_COUNT,
			new Upgrade(Upgrade.UpgradeType.WEAPON_PROJECTILE_COUNT, "Multishot",
				"Adds +1 projectile", 1.0, 3));

		// Player upgrades
		availableUpgrades.put(Upgrade.UpgradeType.MOVE_SPEED,
			new Upgrade(Upgrade.UpgradeType.MOVE_SPEED, "Speed",
				"Increases movement speed by 10%", 0.1, 5));

		availableUpgrades.put(Upgrade.UpgradeType.MAX_HEALTH,
			new Upgrade(Upgrade.UpgradeType.MAX_HEALTH, "Max Health",
				"Increases max health by 1", 1.0, 5));

		availableUpgrades.put(Upgrade.UpgradeType.PICKUP_RANGE,
			new Upgrade(Upgrade.UpgradeType.PICKUP_RANGE, "Magnet",
				"Increases pickup range by 20%", 0.2, 5));

		availableUpgrades.put(Upgrade.UpgradeType.ARMOR,
			new Upgrade(Upgrade.UpgradeType.ARMOR, "Armor",
				"Reduces damage taken by 10%", 0.1, 3));

		availableUpgrades.put(Upgrade.UpgradeType.COOLDOWN_REDUCTION,
			new Upgrade(Upgrade.UpgradeType.COOLDOWN_REDUCTION, "Cooldown",
				"Reduces cooldowns by 10%", 0.1, 5));
	}

	/**
	 * Get random upgrades for level up choice
	 * @param count Number of upgrades to return
	 * @return List of random upgrades that aren't maxed
	 */
	public List<Upgrade> getRandomUpgrades(int count) {
		List<Upgrade> available = new ArrayList<>();

		// Get all non-maxed upgrades
		for (Upgrade upgrade : availableUpgrades.values()) {
			if (!upgrade.isMaxLevel()) {
				available.add(upgrade);
			}
		}

		// Shuffle and return requested count
		List<Upgrade> selected = new ArrayList<>();
		int numToSelect = Math.min(count, available.size());

		for (int i = 0; i < numToSelect; i++) {
			int index = random.nextInt(available.size());
			selected.add(available.remove(index));
		}

		return selected;
	}

	/**
	 * Apply an upgrade
	 */
	public void applyUpgrade(Upgrade upgrade) {
		upgrade.apply();
	}

	/**
	 * Get specific upgrade
	 */
	public Upgrade getUpgrade(Upgrade.UpgradeType type) {
		return availableUpgrades.get(type);
	}

	/**
	 * Get all upgrades
	 */
	public HashMap<Upgrade.UpgradeType, Upgrade> getAllUpgrades() {
		return availableUpgrades;
	}
}
