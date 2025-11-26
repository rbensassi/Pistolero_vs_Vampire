/**
 * XP Gem that drops from defeated enemies (Vampire Survivors style)
 * Gems are attracted to the player when they get close
 */
public class XPGem extends Sprite {
	private int xpValue;
	private boolean isCollected;
	private double magnetRange;
	private double magnetStrength;
	private static final double DEFAULT_MAGNET_RANGE = 100.0;
	private static final double DEFAULT_MAGNET_STRENGTH = 200.0;

	public XPGem(double posX, double posY, int xpValue, double maxX, double maxY) {
		super(posX, posY, 0, maxX, maxY, 8, 8, 0, 0);
		this.xpValue = xpValue;
		this.isCollected = false;
		this.magnetRange = DEFAULT_MAGNET_RANGE;
		this.magnetStrength = DEFAULT_MAGNET_STRENGTH;
	}

	/**
	 * Update gem position - attracted to player if within magnet range
	 */
	public void update(double time, Pistoleros player) {
		if (isCollected) return;

		double distance = distanceTo(player);

		// Magnet effect when player is close
		if (distance < magnetRange && distance > 0) {
			double dx = player.getCenterX() - getCenterX();
			double dy = player.getCenterY() - getCenterY();

			// Normalize and apply magnet strength
			double magnitude = Math.sqrt(dx * dx + dy * dy);
			moveX = (dx / magnitude) * magnetStrength;
			moveY = (dy / magnitude) * magnetStrength;

			move(time);
		}

		// Collect if touching player
		if (collides(player)) {
			isCollected = true;
		}
	}

	public int getXpValue() {
		return xpValue;
	}

	public boolean isCollected() {
		return isCollected;
	}

	public void setMagnetRange(double range) {
		this.magnetRange = range;
	}

	public void setMagnetStrength(double strength) {
		this.magnetStrength = strength;
	}
}
