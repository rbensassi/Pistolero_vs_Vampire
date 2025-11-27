package com.pistolero.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.pistolero.game.systems.LevelSystem;
import com.pistolero.game.systems.UpgradeManager;
import com.pistolero.game.systems.Upgrade;

/**
 * Player character (Pistolero) with Vampire Survivors systems
 */
public class Player extends Entity {
    private int health;
    private int maxHealth;
    private float damageMultiplier;
    private float armorReduction;
    private float pickupRange;
    private int score;

    // Vampire Survivors systems
    private LevelSystem levelSystem;
    private UpgradeManager upgradeManager;

    // Auto-fire system
    private float fireRate;
    private float timeSinceLastShot;
    private int projectileCount;

    // Rendering
    private Texture texture;
    private TextureRegion currentFrame;

    // Constants
    private static final float BASE_SPEED = 150f;
    private static final float BASE_FIRE_RATE = 2.0f;

    public Player(float x, float y) {
        super(x, y, 32, 32, BASE_SPEED);

        this.maxHealth = 3;
        this.health = maxHealth;
        this.damageMultiplier = 1.0f;
        this.armorReduction = 0.0f;
        this.pickupRange = 100f;
        this.score = 0;

        // Initialize Vampire Survivors systems
        this.levelSystem = new LevelSystem();
        this.upgradeManager = new UpgradeManager();

        // Initialize auto-fire
        this.fireRate = BASE_FIRE_RATE;
        this.timeSinceLastShot = 0;
        this.projectileCount = 1;

        // Load texture (you'll need to add the actual texture file)
        // For now, we'll use a placeholder
        // texture = new Texture("player.png");
        // currentFrame = new TextureRegion(texture, 0, 0, 32, 32);
    }

    @Override
    public void update(float delta) {
        // Handle input
        velocity.set(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocity.y = speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.y = -speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = speed;
        }

        // Normalize diagonal movement
        if (velocity.len() > 0) {
            velocity.nor().scl(speed);
        }

        // Update position
        position.add(velocity.x * delta, velocity.y * delta);

        // Clamp to screen bounds
        position.x = Math.max(0, Math.min(position.x, Gdx.graphics.getWidth() - width));
        position.y = Math.max(0, Math.min(position.y, Gdx.graphics.getHeight() - height));

        updateBounds();

        // Update auto-fire timer
        timeSinceLastShot += delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        // For now, just draw a colored rectangle
        // In a real implementation, you'd draw the texture
        // batch.draw(currentFrame, position.x, position.y, width, height);
    }

    /**
     * Check if player can shoot
     */
    public boolean canShoot() {
        float fireInterval = 1.0f / fireRate;
        if (timeSinceLastShot >= fireInterval) {
            timeSinceLastShot = 0;
            return true;
        }
        return false;
    }

    /**
     * Add XP to player
     */
    public boolean addXP(int xp) {
        return levelSystem.addXP(xp);
    }

    /**
     * Apply upgrade to player
     */
    public void applyUpgrade(Upgrade upgrade) {
        upgradeManager.applyUpgrade(upgrade);

        switch (upgrade.getType()) {
            case WEAPON_DAMAGE:
                damageMultiplier += upgrade.getValue();
                break;
            case WEAPON_SPEED:
                fireRate *= (1.0f + upgrade.getValue());
                break;
            case WEAPON_PROJECTILE_COUNT:
                projectileCount += (int) upgrade.getValue();
                break;
            case MOVE_SPEED:
                speed = BASE_SPEED * (1.0f + upgrade.getCurrentValue());
                break;
            case MAX_HEALTH:
                maxHealth += (int) upgrade.getValue();
                health = Math.min(health + (int) upgrade.getValue(), maxHealth);
                break;
            case PICKUP_RANGE:
                pickupRange = 100.0f * (1.0f + upgrade.getCurrentValue());
                break;
            case ARMOR:
                armorReduction = Math.min(0.75f, armorReduction + upgrade.getValue());
                break;
            default:
                break;
        }
    }

    /**
     * Take damage
     */
    public void takeDamage(int damage) {
        int actualDamage = (int) Math.max(1, damage * (1.0 - armorReduction));
        health -= actualDamage;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }

    /**
     * Heal player
     */
    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
    }

    // Getters
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public float getDamageMultiplier() { return damageMultiplier; }
    public float getPickupRange() { return pickupRange; }
    public int getScore() { return score; }
    public void addScore(int points) { score += points; }
    public LevelSystem getLevelSystem() { return levelSystem; }
    public UpgradeManager getUpgradeManager() { return upgradeManager; }
    public int getProjectileCount() { return projectileCount; }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
