package com.pistolero.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.pistolero.game.screens.GameScreen;

/**
 * Main game class for Pistolero vs Vampire - LibGDX Edition
 * With Vampire Survivors mechanics
 */
public class PistoleroGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // Use LibGDX's default Arial font initially

        // Start with the game screen
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render(); // Important! This calls the render method of the current screen
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
