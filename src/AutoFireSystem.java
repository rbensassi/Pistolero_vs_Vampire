import java.util.ArrayList;

/**
 * Automatic firing system (Vampire Survivors style)
 * Automatically targets and shoots at nearest enemies
 */
public class AutoFireSystem {
	private double fireRate; // Shots per second
	private double timeSinceLastShot;
	private int projectileCount;
	private boolean enabled;

	private static final double DEFAULT_FIRE_RATE = 2.0; // 2 shots per second

	public AutoFireSystem() {
		this.fireRate = DEFAULT_FIRE_RATE;
		this.timeSinceLastShot = 0;
		this.projectileCount = 1;
		this.enabled = true;
	}

	/**
	 * Update fire timer
	 * @param deltaTime Time since last update
	 * @return true if should fire
	 */
	public boolean update(double deltaTime) {
		if (!enabled) return false;

		timeSinceLastShot += deltaTime;
		double fireInterval = 1.0 / fireRate;

		if (timeSinceLastShot >= fireInterval) {
			timeSinceLastShot = 0;
			return true;
		}
		return false;
	}

	/**
	 * Find the nearest enemy to the player
	 */
	public Vampire findNearestEnemy(Pistoleros player, ArrayList<Vampire> enemies) {
		if (enemies.isEmpty()) return null;

		Vampire nearest = null;
		double minDistance = Double.MAX_VALUE;

		for (Vampire enemy : enemies) {
			if (!enemy.isAlive()) continue;

			double distance = player.distanceTo(enemy);
			if (distance < minDistance) {
				minDistance = distance;
				nearest = enemy;
			}
		}

		return nearest;
	}

	/**
	 * Calculate direction to shoot at target
	 * @return array [moveX, moveY]
	 */
	public double[] calculateDirection(Pistoleros player, Vampire target) {
		if (target == null) {
			// Default direction based on player facing
			return getDefaultDirection(player);
		}

		double dx = target.getCenterX() - player.getCenterX();
		double dy = target.getCenterY() - player.getCenterY();
		double magnitude = Math.sqrt(dx * dx + dy * dy);

		if (magnitude == 0) {
			return getDefaultDirection(player);
		}

		return new double[] { dx / magnitude, dy / magnitude };
	}

	/**
	 * Get default shooting direction based on player's last movement
	 */
	private double[] getDefaultDirection(Pistoleros player) {
		Character.Direction dir = player.getLastMovementDirection();

		switch (dir) {
			case UP:
				return new double[] { 0, -1 };
			case DOWN:
				return new double[] { 0, 1 };
			case LEFT:
				return new double[] { -1, 0 };
			case RIGHT:
				return new double[] { 1, 0 };
			default:
				return new double[] { 1, 0 };
		}
	}

	/**
	 * Create bullets for auto-fire
	 */
	public ArrayList<Bullet> createBullets(Pistoleros player, Vampire target, double bulletSpeed) {
		ArrayList<Bullet> bullets = new ArrayList<>();
		double[] direction = calculateDirection(player, target);

		// Create multiple projectiles based on upgrades
		for (int i = 0; i < projectileCount; i++) {
			AutoBullet bullet = new AutoBullet(player, bulletSpeed, 10, 10, 0, 0, direction[0], direction[1]);

			// Spread projectiles if multiple
			if (projectileCount > 1) {
				double spreadAngle = (i - projectileCount / 2.0) * 0.2; // 0.2 radians spread
				bullet.applySpread(spreadAngle);
			}

			bullets.add(bullet);
		}

		return bullets;
	}

	// Getters and setters
	public void setFireRate(double fireRate) {
		this.fireRate = fireRate;
	}

	public void increaseFireRate(double factor) {
		this.fireRate *= (1.0 + factor);
	}

	public void setProjectileCount(int count) {
		this.projectileCount = Math.max(1, count);
	}

	public void addProjectile(int count) {
		this.projectileCount += count;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public double getFireRate() {
		return fireRate;
	}

	public int getProjectileCount() {
		return projectileCount;
	}
}
