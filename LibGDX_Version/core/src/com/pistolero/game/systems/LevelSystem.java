package com.pistolero.game.systems;

/**
 * Level and XP system (Vampire Survivors style)
 * XP requirements scale exponentially with level
 */
public class LevelSystem {
	private int currentXP;
	private int currentLevel;
	private int xpToNextLevel;
	private boolean levelUpReady;

	private static final int BASE_XP_REQUIREMENT = 10;
	private static final double XP_SCALING_FACTOR = 1.5;

	public LevelSystem() {
		this.currentXP = 0;
		this.currentLevel = 1;
		this.xpToNextLevel = BASE_XP_REQUIREMENT;
		this.levelUpReady = false;
	}

	/**
	 * Add XP and check for level up
	 * @param xp Amount of XP to add
	 * @return true if leveled up
	 */
	public boolean addXP(int xp) {
		currentXP += xp;

		// Check for level up
		if (currentXP >= xpToNextLevel) {
			levelUp();
			return true;
		}
		return false;
	}

	/**
	 * Level up the player
	 */
	private void levelUp() {
		currentLevel++;
		currentXP = currentXP - xpToNextLevel;

		// Calculate next level XP requirement (exponential scaling)
		int nextRequirement = (int) (BASE_XP_REQUIREMENT * Math.pow(XP_SCALING_FACTOR, currentLevel - 1));
		xpToNextLevel = nextRequirement;

		levelUpReady = true;
	}

	/**
	 * Check if level up is ready (for showing upgrade menu)
	 */
	public boolean isLevelUpReady() {
		return levelUpReady;
	}

	/**
	 * Consume the level up (after showing upgrade menu)
	 */
	public void consumeLevelUp() {
		levelUpReady = false;
	}

	public int getCurrentXP() {
		return currentXP;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public int getXPToNextLevel() {
		return xpToNextLevel;
	}

	/**
	 * Get progress percentage to next level (0-100)
	 */
	public double getLevelProgress() {
		return (currentXP / (double) xpToNextLevel) * 100.0;
	}
}
