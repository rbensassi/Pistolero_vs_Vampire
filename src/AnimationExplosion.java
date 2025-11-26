import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AnimationExplosion extends Animation{
	ImageView explose;
	Pane p;
	int count;
	boolean isFinished;
	private static final int SPRITE_SIZE = 25;
	private static final int FRAME_COUNT = 12;
	private static final double ANIMATION_DURATION = 200;

	public AnimationExplosion(double x, double y, Pane p){
		this(x, y, p, SPRITE_SIZE, FRAME_COUNT, ANIMATION_DURATION);
	}

	/**
	 * Create an explosion animation with customizable parameters
	 * @param x X position of explosion center
	 * @param y Y position of explosion center
	 * @param p Pane to add explosion to
	 * @param spriteSize Size of each frame in pixels
	 * @param frameCount Number of frames in the animation
	 * @param durationMs Duration of animation in milliseconds
	 */
	public AnimationExplosion(double x, double y, Pane p, int spriteSize, int frameCount, double durationMs){
		explose = new ImageView(new Image("file:res/explos1.png"));
		explose.setViewport(new Rectangle2D(0, 0, spriteSize, spriteSize));

		// Center the explosion on the given coordinates
		explose.relocate(x - (spriteSize / 2.0), y - (spriteSize / 2.0));
		isFinished = false;
		this.setCycleDuration(Duration.millis(durationMs));
		this.p = p;
		this.setOnFinished(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent actionEvent) {
	        	p.getChildren().remove(explose);
	        	isFinished = true;
	        }
	    });
        this.count = frameCount;
        p.getChildren().add(explose);
	}

	@Override
	protected void interpolate(double k) {
		int index = Math.min((int) (k * count), count - 1);
		if (index != lastIndex) {
			// Calculate X position in sprite sheet (frames are arranged horizontally)
			int x = index * SPRITE_SIZE;
			int y = 0;

			lastIndex = index;
			explose.setViewport(new Rectangle2D(x, y, SPRITE_SIZE, SPRITE_SIZE));
		}
	}

	public boolean isFinished() {
		return isFinished;
	}
}
