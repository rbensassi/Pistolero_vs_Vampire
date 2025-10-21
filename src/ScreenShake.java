import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ScreenShake {
    private Pane pane;
    private double originalX;
    private double originalY;
    private Timeline shakeTimeline;

    public ScreenShake(Pane pane) {
        this.pane = pane;
        this.originalX = 0;
        this.originalY = 0;
    }

    /**
     * Shake the screen with given intensity and duration
     * @param intensity The maximum offset in pixels (default: 8)
     * @param duration Duration in milliseconds (default: 150)
     */
    public void shake(double intensity, int duration) {
        if (shakeTimeline != null && shakeTimeline.getStatus() == Timeline.Status.RUNNING) {
            shakeTimeline.stop();
        }

        shakeTimeline = new Timeline();
        int frames = duration / 16; // ~60 fps

        for (int i = 0; i < frames; i++) {
            double progress = (double) i / frames;
            double currentIntensity = intensity * (1 - progress); // Decay over time

            double offsetX = (Math.random() - 0.5) * 2 * currentIntensity;
            double offsetY = (Math.random() - 0.5) * 2 * currentIntensity;

            KeyFrame kf = new KeyFrame(
                Duration.millis(i * 16),
                e -> {
                    pane.setTranslateX(offsetX);
                    pane.setTranslateY(offsetY);
                }
            );
            shakeTimeline.getKeyFrames().add(kf);
        }

        // Return to original position at the end
        KeyFrame finalFrame = new KeyFrame(
            Duration.millis(duration),
            e -> {
                pane.setTranslateX(originalX);
                pane.setTranslateY(originalY);
            }
        );
        shakeTimeline.getKeyFrames().add(finalFrame);

        shakeTimeline.play();
    }

    // Quick shake for shooting
    public void lightShake() {
        shake(3, 100);
    }

    // Medium shake for hits
    public void mediumShake() {
        shake(6, 150);
    }

    // Heavy shake for deaths
    public void heavyShake() {
        shake(12, 250);
    }
}
