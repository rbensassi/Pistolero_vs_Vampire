package com.pistolero.game.systems;

/**
 * Manages enemy waves (Vampire Survivors style)
 * Spawns enemies progressively harder over time
 */
public class WaveManager {
    private int currentWave;
    private double timeSinceLastWave;
    private double waveInterval;
    private int baseEnemyCount;
    private double enemyScaling;
    private double maxX;
    private double maxY;

    private static final double INITIAL_WAVE_INTERVAL = 10.0; // seconds
    private static final int INITIAL_ENEMY_COUNT = 5;
    private static final double ENEMY_SCALING_FACTOR = 1.2;

    public WaveManager(double maxX, double maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.currentWave = 0;
        this.timeSinceLastWave = 0;
        this.waveInterval = INITIAL_WAVE_INTERVAL;
        this.baseEnemyCount = INITIAL_ENEMY_COUNT;
        this.enemyScaling = ENEMY_SCALING_FACTOR;
    }

    /**
     * Update wave timer
     * @param deltaTime Time since last update
     * @return true if a new wave should spawn
     */
    public boolean update(double deltaTime) {
        timeSinceLastWave += deltaTime;

        if (timeSinceLastWave >= waveInterval) {
            timeSinceLastWave = 0;
            currentWave++;
            return true;
        }
        return false;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public double getTimeSinceLastWave() {
        return timeSinceLastWave;
    }

    public double getTimeToNextWave() {
        return waveInterval - timeSinceLastWave;
    }
}
