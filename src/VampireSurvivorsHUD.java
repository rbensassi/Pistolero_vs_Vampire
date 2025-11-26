import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * HUD for Vampire Survivors style information
 * Shows level, XP bar, stats, etc.
 */
public class VampireSurvivorsHUD extends VBox {
	private Pistoleros player;
	private Container container;

	private Label levelLabel;
	private Label timerLabel;
	private Label enemyCountLabel;
	private Label waveLabel;
	private ProgressBar xpBar;
	private ProgressBar healthBar;

	public VampireSurvivorsHUD(Pistoleros player, Container container) {
		this.player = player;
		this.container = container;

		setAlignment(Pos.TOP_LEFT);
		setSpacing(5);
		setPadding(new Insets(10));
		setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
		setMaxWidth(300);

		initializeComponents();
	}

	private void initializeComponents() {
		// Level display
		levelLabel = new Label("Level: 1");
		levelLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		levelLabel.setTextFill(Color.GOLD);

		// XP Bar
		VBox xpBox = new VBox(2);
		Label xpLabel = new Label("Experience");
		xpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		xpLabel.setTextFill(Color.CYAN);

		xpBar = new ProgressBar(0);
		xpBar.setPrefWidth(280);
		xpBar.setStyle("-fx-accent: cyan;");

		xpBox.getChildren().addAll(xpLabel, xpBar);

		// Health Bar
		VBox healthBox = new VBox(2);
		Label healthLabel = new Label("Health");
		healthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		healthLabel.setTextFill(Color.RED);

		healthBar = new ProgressBar(1.0);
		healthBar.setPrefWidth(280);
		healthBar.setStyle("-fx-accent: red;");

		healthBox.getChildren().addAll(healthLabel, healthBar);

		// Timer
		timerLabel = new Label("Time: 0:00");
		timerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		timerLabel.setTextFill(Color.WHITE);

		// Wave info
		waveLabel = new Label("Wave: 1");
		waveLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		waveLabel.setTextFill(Color.ORANGE);

		// Enemy count
		enemyCountLabel = new Label("Enemies: 0");
		enemyCountLabel.setFont(Font.font("Arial", 12));
		enemyCountLabel.setTextFill(Color.WHITE);

		// Stats box
		VBox statsBox = new VBox(3);
		statsBox.setPadding(new Insets(5, 0, 0, 0));

		Label statsTitle = new Label("Stats:");
		statsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		statsTitle.setTextFill(Color.YELLOW);

		Label damageLabel = new Label("Damage: x1.0");
		damageLabel.setFont(Font.font("Arial", 10));
		damageLabel.setTextFill(Color.WHITE);

		Label speedLabel = new Label("Speed: 100");
		speedLabel.setFont(Font.font("Arial", 10));
		speedLabel.setTextFill(Color.WHITE);

		statsBox.getChildren().addAll(statsTitle, damageLabel, speedLabel);

		// Add all components
		getChildren().addAll(
			levelLabel,
			xpBox,
			healthBox,
			timerLabel,
			waveLabel,
			enemyCountLabel
		);
	}

	/**
	 * Update HUD values
	 */
	public void update() {
		LevelSystem levelSys = player.getLevelSystem();

		// Update level
		levelLabel.setText("Level: " + levelSys.getCurrentLevel());

		// Update XP bar
		double xpProgress = levelSys.getLevelProgress() / 100.0;
		xpBar.setProgress(xpProgress);

		// Update health bar
		double healthProgress = player.life.getValue() / (double) player.getMaxLife();
		healthBar.setProgress(healthProgress);

		// Update timer
		int seconds = container.timer.getValue();
		int minutes = seconds / 60;
		int secs = seconds % 60;
		timerLabel.setText(String.format("Time: %d:%02d", minutes, secs));

		// Update wave
		waveLabel.setText("Wave: " + container.waveManager.getCurrentWave());

		// Update enemy count
		int aliveEnemies = 0;
		for (Vampire v : container.vampList) {
			if (v.isAlive()) aliveEnemies++;
		}
		enemyCountLabel.setText("Enemies: " + aliveEnemies);
	}
}
