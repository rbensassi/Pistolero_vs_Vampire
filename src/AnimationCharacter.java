import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class AnimationCharacter extends Animation {
	CharacterView charView;
	private int framesPerAnimation;
	private double animationSpeed;

	public AnimationCharacter(CharacterView charView) {
		this(charView, 3, 400);
	}

	public AnimationCharacter(CharacterView charView, int framesPerAnimation, double animationSpeedMs) {
		this.framesPerAnimation = framesPerAnimation;
		this.count = framesPerAnimation;
		this.lastIndex = 0;
		this.charView = charView;
		this.animationSpeed = animationSpeedMs;
		setCycleDuration(Duration.millis(animationSpeed));
		this.setCycleCount(INDEFINITE);
	}

	@Override
	protected void interpolate(double k) {
		Character character = charView.getCharact();

		// Update character's direction and state based on movement
		character.updateDirectionFromMovement();

		int index = Math.min((int) (k * count), count - 1);

		if (index != lastIndex || character.getAnimationState() == Character.AnimationState.WALKING) {
			double spriteWidth = character.width;
			double spriteHeight = character.height;
			double offsetX = character.offsetX;
			double offsetY = character.offsetY;

			double x, y;

			if (character.getAnimationState() == Character.AnimationState.WALKING) {
				// Calculate sprite position in the spritesheet
				// X: frame index * sprite width + initial offset
				x = index * spriteWidth + offsetX;
				// Y: direction row * sprite height + initial offset
				y = character.getDirection().getRow() * spriteHeight + offsetY;

				lastIndex = index;
			} else {
				// IDLE state: show first frame of current direction
				x = offsetX;
				y = character.getLastMovementDirection().getRow() * spriteHeight + offsetY;
			}

			charView.getView().setViewport(new Rectangle2D(x, y, spriteWidth, spriteHeight));
		}
	}

	// Allow changing animation speed dynamically
	public void setAnimationSpeed(double speedMs) {
		this.animationSpeed = speedMs;
		setCycleDuration(Duration.millis(speedMs));
	}

	// Allow changing number of frames
	public void setFramesPerAnimation(int frames) {
		this.framesPerAnimation = frames;
		this.count = frames;
	}
}
