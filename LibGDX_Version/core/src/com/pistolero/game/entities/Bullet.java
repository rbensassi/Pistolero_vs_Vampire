package com.pistolero.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Bullet/Projectile entity
 */
public class Bullet extends Entity {
    private int damage;
    private float lifetime;
    private float maxLifetime;

    public Bullet(float x, float y, Vector2 direction, float speed, int damage) {
        super(x, y, 10, 10, speed);
        this.damage = damage;
        this.velocity.set(direction).nor().scl(speed);
        this.maxLifetime = 3.0f; // 3 seconds max lifetime
        this.lifetime = 0;
    }

    @Override
    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);
        updateBounds();

        // Update lifetime
        lifetime += delta;
        if (lifetime >= maxLifetime) {
            alive = false;
        }

        // Check screen bounds
        if (position.x < 0 || position.x > 1280 || position.y < 0 || position.y > 720) {
            alive = false;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO: Draw bullet sprite
    }

    public int getDamage() {
        return damage;
    }
}
