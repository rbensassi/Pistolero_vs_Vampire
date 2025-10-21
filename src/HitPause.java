import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class HitPause {
    private PauseTransition pauseTransition;
    private Container container;
    private boolean isPaused;

    public HitPause(Container container) {
        this.container = container;
        this.isPaused = false;
    }

    /**
     * Create a brief pause effect (freeze frame) for impact
     * @param durationMs Duration in milliseconds (recommended: 50-150ms)
     */
    public void pause(int durationMs) {
        if (isPaused) return;

        isPaused = true;

        // Store original game state
        double originalTimerI = container.timerI;

        // Temporarily stop the game
        boolean wasntPaused = !container.pause;
        if (wasntPaused) {
            container.pause = true;
        }

        // Resume after duration
        pauseTransition = new PauseTransition(Duration.millis(durationMs));
        pauseTransition.setOnFinished(e -> {
            if (wasntPaused) {
                container.pause = false;
            }
            // Restore timer to prevent time advancement during pause
            container.timerI = originalTimerI;
            isPaused = false;
        });
        pauseTransition.play();
    }

    /**
     * Light hit pause for regular hits
     */
    public void lightPause() {
        pause(40);
    }

    /**
     * Heavy hit pause for kills
     */
    public void heavyPause() {
        pause(80);
    }
}
