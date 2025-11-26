import java.util.ArrayList;
import java.util.Random;

/**
 * Manages enemy waves (Vampire Survivors style)
 * Spawns enemies progressively harder over time
 */
public class WaveManager {
	private int currentWave;
	private double timeSinceLastWave;
	private double waveInterval;
	private int baseEnemyCount;
	private double enemyScaling;
	private Random random;
	private double maxX;
	private double maxY;

	private static final double INITIAL_WAVE_INTERVAL = 10.0; // seconds
	private static final int INITIAL_ENEMY_COUNT = 5;
	private static final double ENEMY_SCALING_FACTOR = 1.2;

	public WaveManager(double maxX, double maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.currentWave = 0;
		this.timeSinceLastWave = 0;
		this.waveInterval = INITIAL_WAVE_INTERVAL;
		this.baseEnemyCount = INITIAL_ENEMY_COUNT;
		this.enemyScaling = ENEMY_SCALING_FACTOR;
		this.random = new Random();
	}

	/**
	 * Update wave timer
	 * @param deltaTime Time since last update
	 * @return true if a new wave should spawn
	 */
	public boolean update(double deltaTime) {
		timeSinceLastWave += deltaTime;

		if (timeSinceLastWave >= waveInterval) {
			timeSinceLastWave = 0;
			currentWave++;
			return true;
		}
		return false;
	}

	/**
	 * Generate a new wave of enemies
	 * @return List of vampires for this wave
	 */
	public ArrayList<Vampire> generateWave() {
		ArrayList<Vampire> enemies = new ArrayList<>();

		// Calculate enemy count for this wave
		int enemyCount = (int) (baseEnemyCount * Math.pow(enemyScaling, currentWave - 1));
		enemyCount = Math.min(enemyCount, 100); // Cap at 100 enemies per wave

		// Spawn enemies around the edges of the screen
		for (int i = 0; i < enemyCount; i++) {
			Vampire vampire = spawnEnemyAtEdge();
			enemies.add(vampire);
		}

		return enemies;
	}

	/**
	 * Spawn a single enemy at a random edge of the screen
	 */
	private Vampire spawnEnemyAtEdge() {
		double posX, posY;
		int edge = random.nextInt(4); // 0=top, 1=right, 2=bottom, 3=left

		switch (edge) {
			case 0: // Top
				posX = random.nextDouble() * maxX;
				posY = 0;
				break;
			case 1: // Right
				posX = maxX - 32;
				posY = random.nextDouble() * maxY;
				break;
			case 2: // Bottom
				posX = random.nextDouble() * maxX;
				posY = maxY - 32;
				break;
			default: // Left
				posX = 0;
				posY = random.nextDouble() * maxY;
				break;
		}

		// Create vampire with scaled stats based on wave
		int health = 3 + (currentWave / 5); // Increase health every 5 waves
		int damage = 1 + (currentWave / 10); // Increase damage every 10 waves
		double speed = 100 + (currentWave * 2); // Slightly increase speed

		return new Vampire(posX, posY, speed, maxX, maxY, 32, 32, 32*3, 36*3, health, damage, random.nextBoolean());
	}

	/**
	 * Continuously spawn enemies throughout the game
	 */
	public Vampire spawnContinuousEnemy() {
		return spawnEnemyAtEdge();
	}

	public int getCurrentWave() {
		return currentWave;
	}

	public double getTimeSinceLastWave() {
		return timeSinceLastWave;
	}

	public double getTimeToNextWave() {
		return waveInterval - timeSinceLastWave;
	}

	/**
	 * Reduce wave interval for more intense gameplay
	 */
	public void increaseSpawnRate(double factor) {
		waveInterval = Math.max(5.0, waveInterval * factor);
	}
}
