import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Visual representation of an XP Gem
 */
public class XPGemView {
	private XPGem gem;
	private Circle circle;
	private Pane pane;

	public XPGemView(XPGem gem, Pane pane) {
		this.gem = gem;
		this.pane = pane;

		// Create a glowing circle for the XP gem
		this.circle = new Circle(gem.width / 2.0);

		// Color based on XP value
		if (gem.getXpValue() >= 10) {
			circle.setFill(Color.GOLD);
		} else if (gem.getXpValue() >= 5) {
			circle.setFill(Color.CYAN);
		} else {
			circle.setFill(Color.LIGHTGREEN);
		}

		circle.setStroke(Color.WHITE);
		circle.setStrokeWidth(1);

		updatePosition();
	}

	public void updatePosition() {
		circle.setCenterX(gem.getPosX() + gem.width / 2.0);
		circle.setCenterY(gem.getPosY() + gem.height / 2.0);
	}

	public void add() {
		pane.getChildren().add(circle);
	}

	public void remove() {
		pane.getChildren().remove(circle);
	}

	public XPGem getGem() {
		return gem;
	}
}
