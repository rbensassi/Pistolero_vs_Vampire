import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LowHealthVignette {
    private Rectangle vignette;
    private Pane pane;
    private Timeline pulseTimeline;
    private boolean isActive;
    private double currentIntensity;

    public LowHealthVignette(Pane pane, double width, double height) {
        this.pane = pane;
        this.isActive = false;
        this.currentIntensity = 0;

        // Create vignette overlay
        vignette = new Rectangle(width, height);
        vignette.setMouseTransparent(true);
        vignette.setOpacity(0);

        updateVignette(0);
    }

    public void update(int currentHealth, int maxHealth) {
        double healthPercent = (double) currentHealth / maxHealth;

        if (healthPercent <= 0.33) { // Low health (1 heart out of 3)
            if (!isActive) {
                activate();
            }
            // Increase intensity as health decreases
            double targetIntensity = 1.0 - (healthPercent / 0.33);
            updateIntensity(targetIntensity);
        } else {
            if (isActive) {
                deactivate();
            }
        }
    }

    private void activate() {
        isActive = true;
        if (!pane.getChildren().contains(vignette)) {
            pane.getChildren().add(vignette);
            vignette.toBack(); // Put behind game elements but in front of background
        }

        // Create pulsing effect
        pulseTimeline = new Timeline(
            new KeyFrame(Duration.millis(0), e -> vignette.setOpacity(currentIntensity * 0.3)),
            new KeyFrame(Duration.millis(800), e -> vignette.setOpacity(currentIntensity * 0.5)),
            new KeyFrame(Duration.millis(1600), e -> vignette.setOpacity(currentIntensity * 0.3))
        );
        pulseTimeline.setCycleCount(Timeline.INDEFINITE);
        pulseTimeline.play();
    }

    private void deactivate() {
        isActive = false;
        if (pulseTimeline != null) {
            pulseTimeline.stop();
        }
        vignette.setOpacity(0);
        currentIntensity = 0;
    }

    private void updateIntensity(double intensity) {
        currentIntensity = intensity;
        updateVignette(intensity);
    }

    private void updateVignette(double intensity) {
        // Red vignette that gets stronger towards edges
        double redIntensity = Math.min(255, 200 + (intensity * 55));

        RadialGradient gradient = new RadialGradient(
            0, 0, 0.5, 0.5, 0.7,
            true,
            CycleMethod.NO_CYCLE,
            new Stop(0, Color.TRANSPARENT),
            new Stop(0.6, Color.rgb((int)redIntensity, 0, 0, 0.0)),
            new Stop(1.0, Color.rgb((int)redIntensity, 0, 0, 0.6 * intensity))
        );

        vignette.setFill(gradient);
    }

    public void remove() {
        if (pulseTimeline != null) {
            pulseTimeline.stop();
        }
        pane.getChildren().remove(vignette);
    }
}
