package com.pistolero.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Enemy (Vampire) entity
 */
public class Enemy extends Entity {
    private int health;
    private int maxHealth;
    private int damage;
    private Player target;

    public Enemy(float x, float y, Player target, int health, int damage, float speed) {
        super(x, y, 32, 32, speed);
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.target = target;
    }

    @Override
    public void update(float delta) {
        if (!alive || target == null) return;

        // Move towards player
        Vector2 direction = new Vector2(
            target.getCenterX() - getCenterX(),
            target.getCenterY() - getCenterY()
        );

        if (direction.len() > 0) {
            direction.nor();
            velocity.set(direction).scl(speed);
            position.add(velocity.x * delta, velocity.y * delta);
            updateBounds();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO: Draw enemy sprite
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
