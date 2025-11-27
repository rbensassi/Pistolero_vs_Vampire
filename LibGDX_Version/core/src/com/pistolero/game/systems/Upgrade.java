package com.pistolero.game.systems;

/**
 * Represents an upgrade that can be applied to the player
 * Vampire Survivors style upgrades
 */
public class Upgrade {
	public enum UpgradeType {
		// Weapon upgrades
		WEAPON_DAMAGE,
		WEAPON_SPEED,
		WEAPON_PROJECTILE_COUNT,
		WEAPON_RANGE,

		// Player upgrades
		MOVE_SPEED,
		MAX_HEALTH,
		HEALTH_REGEN,
		PICKUP_RANGE,

		// Passive upgrades
		COOLDOWN_REDUCTION,
		AREA_SIZE,
		DURATION,
		ARMOR
	}

	private UpgradeType type;
	private String name;
	private String description;
	private int level;
	private int maxLevel;
	private double value;

	public Upgrade(UpgradeType type, String name, String description, double value, int maxLevel) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.value = value;
		this.level = 0;
		this.maxLevel = maxLevel;
	}

	/**
	 * Apply this upgrade and increase its level
	 */
	public void apply() {
		if (level < maxLevel) {
			level++;
		}
	}

	public boolean isMaxLevel() {
		return level >= maxLevel;
	}

	public UpgradeType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getLevel() {
		return level;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public double getValue() {
		return value;
	}

	public double getCurrentValue() {
		return value * level;
	}
}
