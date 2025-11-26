import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Upgrade selection menu (Vampire Survivors style)
 * Appears when player levels up
 */
public class UpgradeMenu extends VBox {
	private Pistoleros player;
	private Container container;
	private boolean isShowing;

	public UpgradeMenu(Pistoleros player, Container container) {
		this.player = player;
		this.container = container;
		this.isShowing = false;

		setAlignment(Pos.CENTER);
		setSpacing(20);
		setPadding(new Insets(30));
		setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); -fx-border-color: gold; -fx-border-width: 3;");
		setMaxWidth(600);
		setMaxHeight(400);
		setVisible(false);
	}

	/**
	 * Show upgrade choices
	 */
	public void show() {
		if (isShowing) return;

		getChildren().clear();
		isShowing = true;
		setVisible(true);

		// Title
		Text title = new Text("LEVEL UP!");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
		title.setFill(Color.GOLD);

		Text subtitle = new Text("Level " + player.getLevelSystem().getCurrentLevel());
		subtitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		subtitle.setFill(Color.WHITE);

		getChildren().addAll(title, subtitle);

		// Get 3 random upgrades
		List<Upgrade> upgrades = player.getUpgradeManager().getRandomUpgrades(3);

		for (Upgrade upgrade : upgrades) {
			VBox upgradeBox = createUpgradeButton(upgrade);
			getChildren().add(upgradeBox);
		}

		// Pause the game
		container.pause = true;
	}

	/**
	 * Create an upgrade button
	 */
	private VBox createUpgradeButton(Upgrade upgrade) {
		VBox box = new VBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		box.setPadding(new Insets(10));
		box.setStyle("-fx-background-color: rgba(50, 50, 50, 0.8); -fx-border-color: white; -fx-border-width: 2;");
		box.setMaxWidth(500);

		// Upgrade name and level
		HBox headerBox = new HBox(10);
		headerBox.setAlignment(Pos.CENTER_LEFT);

		Label nameLabel = new Label(upgrade.getName());
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		nameLabel.setTextFill(Color.CYAN);

		Label levelLabel = new Label("Level " + upgrade.getLevel() + "/" + upgrade.getMaxLevel());
		levelLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		levelLabel.setTextFill(Color.LIGHTGRAY);

		headerBox.getChildren().addAll(nameLabel, levelLabel);

		// Description
		Label descLabel = new Label(upgrade.getDescription());
		descLabel.setFont(Font.font("Arial", 14));
		descLabel.setTextFill(Color.WHITE);
		descLabel.setWrapText(true);

		box.getChildren().addAll(headerBox, descLabel);

		// Make clickable
		box.setOnMouseClicked(e -> selectUpgrade(upgrade));
		box.setOnMouseEntered(e -> box.setStyle("-fx-background-color: rgba(100, 100, 100, 0.9); -fx-border-color: gold; -fx-border-width: 2;"));
		box.setOnMouseExited(e -> box.setStyle("-fx-background-color: rgba(50, 50, 50, 0.8); -fx-border-color: white; -fx-border-width: 2;"));

		return box;
	}

	/**
	 * Player selected an upgrade
	 */
	private void selectUpgrade(Upgrade upgrade) {
		player.applyUpgrade(upgrade);
		player.getLevelSystem().consumeLevelUp();
		hide();
	}

	/**
	 * Hide the menu
	 */
	public void hide() {
		setVisible(false);
		isShowing = false;
		container.pause = false;
		container.pauseTime = 20;
	}

	public boolean isShowing() {
		return isShowing;
	}
}
