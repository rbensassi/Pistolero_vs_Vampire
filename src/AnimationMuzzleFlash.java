import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class AnimationMuzzleFlash {
    private Circle flash;
    private Pane pane;
    private Timeline flashTimeline;
    public boolean isFinished;

    public AnimationMuzzleFlash(double x, double y, int direction, Pane pane) {
        this.pane = pane;
        this.isFinished = false;

        // Create flash circle
        flash = new Circle(8);
        flash.setFill(Color.rgb(255, 200, 100, 0.9));

        // Position based on direction
        double offsetX = 0;
        double offsetY = 0;

        switch(direction) {
            case 0: // Up
                offsetY = -10;
                break;
            case 1: // Right
                offsetX = 15;
                break;
            case 2: // Down
                offsetY = 15;
                break;
            case 3: // Left
                offsetX = -10;
                break;
        }

        flash.setCenterX(x + offsetX);
        flash.setCenterY(y + offsetY);

        pane.getChildren().add(flash);

        // Create animation
        flashTimeline = new Timeline(
            new KeyFrame(Duration.millis(0), e -> {
                flash.setRadius(8);
                flash.setOpacity(0.9);
            }),
            new KeyFrame(Duration.millis(30), e -> {
                flash.setRadius(12);
                flash.setOpacity(0.7);
            }),
            new KeyFrame(Duration.millis(60), e -> {
                flash.setRadius(6);
                flash.setOpacity(0.3);
            }),
            new KeyFrame(Duration.millis(80), e -> {
                pane.getChildren().remove(flash);
                isFinished = true;
            })
        );
    }

    public void play() {
        if (flashTimeline != null) {
            flashTimeline.play();
        }
    }
}
