import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class CameraZoom {
    private Pane pane;
    private Timeline zoomTimeline;
    private double baseScale = 1.0;

    public CameraZoom(Pane pane) {
        this.pane = pane;
    }

    /**
     * Zoom in briefly then back out for punch effect
     * @param intensity How much to zoom (1.0 = no zoom, 1.1 = 10% zoom in)
     * @param duration Total duration in milliseconds
     */
    public void zoomPunch(double intensity, int duration) {
        if (zoomTimeline != null && zoomTimeline.getStatus() == Timeline.Status.RUNNING) {
            zoomTimeline.stop();
        }

        zoomTimeline = new Timeline();
        int frames = duration / 16; // ~60 fps

        for (int i = 0; i <= frames; i++) {
            double progress = (double) i / frames;

            // Create a punch curve: quick zoom in, slower zoom out
            double scale;
            if (progress < 0.3) {
                // Zoom in phase (30% of duration)
                scale = baseScale + (intensity - baseScale) * (progress / 0.3);
            } else {
                // Zoom out phase (70% of duration)
                double outProgress = (progress - 0.3) / 0.7;
                scale = intensity - (intensity - baseScale) * outProgress;
            }

            final double currentScale = scale;

            KeyFrame kf = new KeyFrame(
                Duration.millis(i * 16),
                e -> {
                    pane.setScaleX(currentScale);
                    pane.setScaleY(currentScale);
                }
            );
            zoomTimeline.getKeyFrames().add(kf);
        }

        // Ensure we end at base scale
        KeyFrame finalFrame = new KeyFrame(
            Duration.millis(duration),
            e -> {
                pane.setScaleX(baseScale);
                pane.setScaleY(baseScale);
            }
        );
        zoomTimeline.getKeyFrames().add(finalFrame);

        zoomTimeline.play();
    }

    /**
     * Small zoom for hits
     */
    public void smallZoom() {
        zoomPunch(1.03, 200);
    }

    /**
     * Medium zoom for kills
     */
    public void mediumZoom() {
        zoomPunch(1.06, 300);
    }

    /**
     * Large zoom for special events
     */
    public void largeZoom() {
        zoomPunch(1.1, 400);
    }
}
