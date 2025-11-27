package com.pistolero.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Base class for all game entities
 */
public abstract class Entity {
    protected Vector2 position;
    protected Vector2 velocity;
    protected float speed;
    protected float width;
    protected float height;
    protected Rectangle bounds;
    protected boolean alive;

    public Entity(float x, float y, float width, float height, float speed) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.bounds = new Rectangle(x, y, width, height);
        this.alive = true;
    }

    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

    public void updateBounds() {
        bounds.set(position.x, position.y, width, height);
    }

    public boolean collidesWith(Entity other) {
        return bounds.overlaps(other.bounds);
    }

    public float distanceTo(Entity other) {
        return position.dst(other.position);
    }

    // Getters and setters
    public Vector2 getPosition() { return position; }
    public Vector2 getVelocity() { return velocity; }
    public float getX() { return position.x; }
    public float getY() { return position.y; }
    public float getCenterX() { return position.x + width / 2; }
    public float getCenterY() { return position.y + height / 2; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public Rectangle getBounds() { return bounds; }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
}
