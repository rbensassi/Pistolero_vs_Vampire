/**
 * Auto-firing bullet that can be aimed in any direction
 */
public class AutoBullet extends Bullet {
	private double directionX;
	private double directionY;

	public AutoBullet(Pistoleros pist, double speed, double width, double height,
	                  double offsetX, double offsetY, double dirX, double dirY) {
		super(pist, speed, width, height, offsetX, offsetY);

		// Override the direction-based movement from parent
		this.directionX = dirX;
		this.directionY = dirY;

		// Set position at player center
		this.posX = pist.getCenterX() - width / 2.0;
		this.posY = pist.getCenterY() - height / 2.0;

		// Set movement based on direction
		this.moveX = directionX * speed;
		this.moveY = directionY * speed;

		this.explose = false;
	}

	/**
	 * Apply spread to the bullet's direction
	 */
	public void applySpread(double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		double newDirX = directionX * cos - directionY * sin;
		double newDirY = directionX * sin + directionY * cos;

		directionX = newDirX;
		directionY = newDirY;

		// Update movement
		double speed = Math.sqrt(moveX * moveX + moveY * moveY);
		moveX = directionX * speed;
		moveY = directionY * speed;
	}

	public double getDirectionX() {
		return directionX;
	}

	public double getDirectionY() {
		return directionY;
	}
}
