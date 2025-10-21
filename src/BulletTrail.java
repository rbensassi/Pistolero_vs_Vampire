import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.ArrayList;

public class BulletTrail {
    private ArrayList<Circle> trailParticles;
    private Pane pane;
    private int maxParticles = 5;
    private Bullet bullet;

    public BulletTrail(Bullet bullet, Pane pane) {
        this.bullet = bullet;
        this.pane = pane;
        this.trailParticles = new ArrayList<>();
    }

    public void update() {
        // Add new trail particle at bullet position
        Circle particle = new Circle(3);
        particle.setFill(Color.rgb(255, 220, 100, 0.6));
        particle.setCenterX(bullet.posX + bullet.width / 2);
        particle.setCenterY(bullet.posY + bullet.height / 2);

        trailParticles.add(particle);
        pane.getChildren().add(particle);

        // Fade out old particles
        for (int i = trailParticles.size() - 1; i >= 0; i--) {
            Circle p = trailParticles.get(i);
            double opacity = p.getOpacity() - 0.15;

            if (opacity <= 0) {
                pane.getChildren().remove(p);
                trailParticles.remove(i);
            } else {
                p.setOpacity(opacity);
                p.setRadius(p.getRadius() * 0.9); // Shrink
            }
        }

        // Limit number of particles
        while (trailParticles.size() > maxParticles) {
            Circle p = trailParticles.remove(0);
            pane.getChildren().remove(p);
        }
    }

    public void clear() {
        for (Circle p : trailParticles) {
            pane.getChildren().remove(p);
        }
        trailParticles.clear();
    }
}
