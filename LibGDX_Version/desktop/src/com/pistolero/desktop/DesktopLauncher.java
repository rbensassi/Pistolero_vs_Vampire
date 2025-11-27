package com.pistolero.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.pistolero.game.PistoleroGame;

/**
 * Desktop launcher for Pistolero vs Vampire - LibGDX Edition
 */
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        // Window configuration
        config.setTitle("Pistolero vs Vampire - Vampire Survivors Edition");
        config.setWindowedMode(PistoleroGame.WIDTH, PistoleroGame.HEIGHT);
        config.setResizable(true);

        // FPS configuration
        config.setForegroundFPS(60);
        config.useVsync(true);

        // Window icon (optional)
        // config.setWindowIcon("icon.png");

        new Lwjgl3Application(new PistoleroGame(), config);
    }
}
