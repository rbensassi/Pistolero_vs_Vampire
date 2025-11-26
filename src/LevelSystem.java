import javafx.beans.property.SimpleIntegerProperty;

/**
 * Level and XP system (Vampire Survivors style)
 * XP requirements scale exponentially with level
 */
public class LevelSystem {
	private SimpleIntegerProperty currentXP;
	private SimpleIntegerProperty currentLevel;
	private SimpleIntegerProperty xpToNextLevel;
	private boolean levelUpReady;

	private static final int BASE_XP_REQUIREMENT = 10;
	private static final double XP_SCALING_FACTOR = 1.5;

	public LevelSystem() {
		this.currentXP = new SimpleIntegerProperty(0);
		this.currentLevel = new SimpleIntegerProperty(1);
		this.xpToNextLevel = new SimpleIntegerProperty(BASE_XP_REQUIREMENT);
		this.levelUpReady = false;
	}

	/**
	 * Add XP and check for level up
	 * @param xp Amount of XP to add
	 * @return true if leveled up
	 */
	public boolean addXP(int xp) {
		currentXP.set(currentXP.get() + xp);

		// Check for level up
		if (currentXP.get() >= xpToNextLevel.get()) {
			levelUp();
			return true;
		}
		return false;
	}

	/**
	 * Level up the player
	 */
	private void levelUp() {
		currentLevel.set(currentLevel.get() + 1);
		currentXP.set(currentXP.get() - xpToNextLevel.get());

		// Calculate next level XP requirement (exponential scaling)
		int nextRequirement = (int) (BASE_XP_REQUIREMENT * Math.pow(XP_SCALING_FACTOR, currentLevel.get() - 1));
		xpToNextLevel.set(nextRequirement);

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
		return currentXP.get();
	}

	public int getCurrentLevel() {
		return currentLevel.get();
	}

	public int getXPToNextLevel() {
		return xpToNextLevel.get();
	}

	public SimpleIntegerProperty currentXPProperty() {
		return currentXP;
	}

	public SimpleIntegerProperty currentLevelProperty() {
		return currentLevel;
	}

	public SimpleIntegerProperty xpToNextLevelProperty() {
		return xpToNextLevel;
	}

	/**
	 * Get progress percentage to next level (0-100)
	 */
	public double getLevelProgress() {
		return (currentXP.get() / (double) xpToNextLevel.get()) * 100.0;
	}
}
