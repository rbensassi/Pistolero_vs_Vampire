import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.MotionBlur;
import javafx.util.Duration;

public class AnimationDeath extends Animation{
	boolean isFinished;
	MotionBlur blur;
	VampireView v;
	int id;
	int MAX_BLUR = 50;
	int CURRENT_BLUR = 10;
	double MAX_ANGLE = 50.0f;
	double CURRENT_ANGLE = 10.0f;
	double initialRotation;
	double rotationDirection;

	public AnimationDeath(VampireView _v){
		isFinished = false;
		v = _v;
		blur = new MotionBlur();
		blur.setRadius(CURRENT_BLUR);
        blur.setAngle(45.0f);
		v.imageView.setEffect(blur);
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setContrast(0.5);
		colorAdjust.setHue(-1);
		colorAdjust.setBrightness(0.7);
		colorAdjust.setSaturation(1);
		v.imageView.setEffect(colorAdjust);

		// Store initial rotation and randomize direction
		initialRotation = v.imageView.getRotate();
		rotationDirection = Math.random() > 0.5 ? 1 : -1;

		this.setCycleDuration(Duration.millis(400)); // Increased duration for better effect
		this.setOnFinished(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent actionEvent) {
	        	isFinished = true;
	        }
	    });
	}

	@Override
	protected void interpolate(double k) {
		int index = Math.min((int) (k * MAX_BLUR), MAX_BLUR - 1);
		if (index > CURRENT_BLUR) {
			CURRENT_BLUR = index;
			blur.setRadius(CURRENT_BLUR);
		}
		double angle = Math.min((k * MAX_ANGLE), MAX_ANGLE - 1);
		if (angle > CURRENT_ANGLE) {
			CURRENT_ANGLE = angle;
	        blur.setAngle(CURRENT_ANGLE);
		}
		v.imageView.setEffect(blur);

		// Add rotation and scaling during death
		double rotation = initialRotation + (rotationDirection * 360 * k);
		v.imageView.setRotate(rotation);

		// Scale down and fade out
		double scale = 1.0 - (k * 0.7); // Shrink to 30% of original size
		v.imageView.setScaleX(scale);
		v.imageView.setScaleY(scale);
		v.imageView.setOpacity(1.0 - k);
	}

}
