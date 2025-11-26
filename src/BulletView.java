import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;

public class BulletView extends SpriteView{
	public static final String image = "file:res/bulletf.png";
	Bullet bullet;

	public BulletView(Bullet bullet, Pane p) {
		super(image, p);
		this.bullet = bullet;
		imageView.setViewport(new Rectangle2D(bullet.offsetX, bullet.offsetY, bullet.width, bullet.height));

		// Calculate bullet rotation based on movement direction
		setBulletRotation();
	}

	/**
	 * Calculate and set bullet rotation based on movement vector
	 * Uses Math.atan2 for accurate angle calculation in all directions
	 */
	private void setBulletRotation() {
		if (bullet.moveX != 0 || bullet.moveY != 0) {
			// Calculate angle in degrees from movement vector
			double angle = Math.toDegrees(Math.atan2(bullet.moveY, bullet.moveX));
			imageView.setRotate(angle);
		} else {
			// Default rotation (facing right)
			imageView.setRotate(0);
		}
	}

	public void update() {
		imageView.relocate(bullet.getPosX(), bullet.getPosY());
		if (bullet.explose)
			remove();
	}
}
