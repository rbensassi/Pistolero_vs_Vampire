package com.pistolero.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * XP Gem that drops from defeated enemies (Vampire Survivors style)
 * Gems are attracted to the player when they get close
 */
public class XPGem extends Entity {
    private int xpValue;
    private boolean collected;
    private float magnetRange;
    private float magnetStrength;
    private Player player;

    private static final float DEFAULT_MAGNET_RANGE = 100.0f;
    private static final float DEFAULT_MAGNET_STRENGTH = 200.0f;

    public XPGem(float x, float y, int xpValue, Player player) {
        super(x, y, 8, 8, 0);
        this.xpValue = xpValue;
        this.collected = false;
        this.magnetRange = DEFAULT_MAGNET_RANGE;
        this.magnetStrength = DEFAULT_MAGNET_STRENGTH;
        this.player = player;
    }

    @Override
    public void update(float delta) {
        if (collected || player == null) return;

        float distance = distanceTo(player);

        // Magnet effect when player is close
        if (distance < magnetRange && distance > 0) {
            Vector2 direction = new Vector2(
                player.getCenterX() - getCenterX(),
                player.getCenterY() - getCenterY()
            );
            direction.nor();
            velocity.set(direction).scl(magnetStrength);
            position.add(velocity.x * delta, velocity.y * delta);
            updateBounds();
        }

        // Collect if touching player
        if (collidesWith(player)) {
            collected = true;
            player.addXP(xpValue);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO: Draw gem sprite with color based on value
    }

    public boolean isCollected() {
        return collected;
    }

    public void setMagnetRange(float range) {
        this.magnetRange = range;
    }
}
