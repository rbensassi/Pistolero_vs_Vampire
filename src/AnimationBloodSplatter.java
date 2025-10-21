import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.ArrayList;

public class AnimationBloodSplatter {
    private ArrayList<Circle> particles;
    private Pane pane;
    private Timeline particleTimeline;
    public boolean isFinished;

    public AnimationBloodSplatter(double x, double y, int hitDirection, Pane pane) {
        this.pane = pane;
        this.particles = new ArrayList<>();
        this.isFinished = false;

        // Create 8-15 blood particles
        int particleCount = 8 + (int)(Math.random() * 7);

        for (int i = 0; i < particleCount; i++) {
            Circle particle = new Circle();
            particle.setRadius(2 + Math.random() * 3);

            // Dark red blood color with variation
            int red = 180 + (int)(Math.random() * 75);
            particle.setFill(Color.rgb(red, 0, 0, 0.8));

            particle.setCenterX(x);
            particle.setCenterY(y);

            particles.add(particle);
            pane.getChildren().add(particle);
        }

        // Animate particles
        animateParticles(x, y, hitDirection);
    }

    private void animateParticles(double startX, double startY, int hitDirection) {
        // Calculate spread direction based on hit direction
        double baseAngle = 0;
        switch(hitDirection) {
            case 0: baseAngle = Math.PI / 2; break;  // Up -> spray down
            case 1: baseAngle = Math.PI; break;       // Right -> spray left
            case 2: baseAngle = -Math.PI / 2; break;  // Down -> spray up
            case 3: baseAngle = 0; break;             // Left -> spray right
        }

        particleTimeline = new Timeline();
        int duration = 400; // milliseconds
        int frames = 25;

        for (int frame = 0; frame < frames; frame++) {
            final int currentFrame = frame;
            double progress = (double) frame / frames;

            KeyFrame kf = new KeyFrame(
                Duration.millis(frame * (duration / frames)),
                e -> {
                    for (int i = 0; i < particles.size(); i++) {
                        Circle particle = particles.get(i);

                        // Random angle with bias towards hit direction
                        double angle = baseAngle + (Math.random() - 0.5) * Math.PI;
                        double speed = 50 + Math.random() * 100;

                        // Calculate velocity with gravity
                        double vx = Math.cos(angle) * speed * progress;
                        double vy = Math.sin(angle) * speed * progress + (progress * progress * 50); // Gravity

                        particle.setCenterX(startX + vx);
                        particle.setCenterY(startY + vy);

                        // Fade out
                        particle.setOpacity(1 - progress);

                        // Shrink
                        particle.setRadius((2 + Math.random() * 3) * (1 - progress * 0.5));
                    }
                }
            );
            particleTimeline.getKeyFrames().add(kf);
        }

        // Cleanup
        KeyFrame finalFrame = new KeyFrame(
            Duration.millis(duration),
            e -> {
                for (Circle particle : particles) {
                    pane.getChildren().remove(particle);
                }
                isFinished = true;
            }
        );
        particleTimeline.getKeyFrames().add(finalFrame);
    }

    public void play() {
        if (particleTimeline != null) {
            particleTimeline.play();
        }
    }
}
