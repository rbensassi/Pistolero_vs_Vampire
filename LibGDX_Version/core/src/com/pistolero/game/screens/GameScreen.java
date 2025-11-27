package com.pistolero.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pistolero.game.PistoleroGame;
import com.pistolero.game.entities.*;
import com.pistolero.game.systems.WaveManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Main game screen with Vampire Survivors mechanics
 */
public class GameScreen implements Screen {
    private final PistoleroGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    // Game entities
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<XPGem> xpGems;

    // Game systems
    private WaveManager waveManager;

    // Constants
    private static final int XP_PER_KILL = 5;
    private static final float CONTINUOUS_SPAWN_INTERVAL = 2.0f;
    private float continuousSpawnTimer = 0;

    public GameScreen(PistoleroGame game) {
        this.game = game;

        // Setup camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(PistoleroGame.WIDTH, PistoleroGame.HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Initialize shape renderer for debugging
        shapeRenderer = new ShapeRenderer();

        // Initialize game entities
        player = new Player(PistoleroGame.WIDTH / 2f, PistoleroGame.HEIGHT / 2f);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        xpGems = new ArrayList<>();

        // Initialize wave manager
        waveManager = new WaveManager(PistoleroGame.WIDTH, PistoleroGame.HEIGHT);

        // Spawn initial enemies
        for (int i = 0; i < 10; i++) {
            spawnEnemyAtEdge();
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Update game logic
        update(delta);

        // Render game
        renderGame();
        renderUI();
    }

    private void update(float delta) {
        // Update player
        player.update(delta);

        // Auto-fire system
        if (player.canShoot() && !enemies.isEmpty()) {
            Enemy nearest = findNearestEnemy();
            if (nearest != null) {
                fireAtTarget(nearest);
            }
        }

        // Update bullets
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.update(delta);

            if (!bullet.isAlive()) {
                bulletIter.remove();
                continue;
            }

            // Check bullet collisions with enemies
            for (Enemy enemy : enemies) {
                if (enemy.isAlive() && bullet.collidesWith(enemy)) {
                    enemy.takeDamage((int) (bullet.getDamage() * player.getDamageMultiplier()));
                    bullet.setAlive(false);

                    // Drop XP gem if enemy dies
                    if (!enemy.isAlive()) {
                        xpGems.add(new XPGem(enemy.getCenterX(), enemy.getCenterY(), XP_PER_KILL, player));
                        player.addScore(10);
                    }
                    break;
                }
            }
        }

        // Update enemies
        Iterator<Enemy> enemyIter = enemies.iterator();
        while (enemyIter.hasNext()) {
            Enemy enemy = enemyIter.next();
            enemy.update(delta);

            if (!enemy.isAlive()) {
                enemyIter.remove();
                continue;
            }

            // Check collision with player
            if (enemy.collidesWith(player)) {
                player.takeDamage(enemy.getDamage());
            }
        }

        // Update XP gems
        Iterator<XPGem> gemIter = xpGems.iterator();
        while (gemIter.hasNext()) {
            XPGem gem = gemIter.next();
            gem.setMagnetRange(player.getPickupRange());
            gem.update(delta);

            if (gem.isCollected()) {
                gemIter.remove();
            }
        }

        // Wave system - continuous spawning
        continuousSpawnTimer += delta;
        if (continuousSpawnTimer >= CONTINUOUS_SPAWN_INTERVAL) {
            continuousSpawnTimer = 0;
            int spawnCount = 1 + (int) (Math.random() * 3);
            for (int i = 0; i < spawnCount; i++) {
                spawnEnemyAtEdge();
            }
        }

        // Check if player leveled up
        if (player.getLevelSystem().isLevelUpReady()) {
            // TODO: Show upgrade menu
            player.getLevelSystem().consumeLevelUp();
        }

        // Check game over
        if (!player.isAlive()) {
            // TODO: Show game over screen
            System.out.println("Game Over! Score: " + player.getScore());
        }
    }

    private void renderGame() {
        game.batch.begin();

        // Render player
        player.render(game.batch);

        // Render enemies
        for (Enemy enemy : enemies) {
            enemy.render(game.batch);
        }

        // Render bullets
        for (Bullet bullet : bullets) {
            bullet.render(game.batch);
        }

        // Render XP gems
        for (XPGem gem : xpGems) {
            gem.render(game.batch);
        }

        game.batch.end();

        // Debug rendering
        renderDebug();
    }

    private void renderDebug() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Player
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());

        // Enemies
        shapeRenderer.setColor(1, 0, 0, 1);
        for (Enemy enemy : enemies) {
            shapeRenderer.rect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        }

        // Bullets
        shapeRenderer.setColor(1, 1, 0, 1);
        for (Bullet bullet : bullets) {
            shapeRenderer.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }

        // XP Gems
        shapeRenderer.setColor(0, 1, 1, 1);
        for (XPGem gem : xpGems) {
            shapeRenderer.rect(gem.getX(), gem.getY(), gem.getWidth(), gem.getHeight());
        }

        shapeRenderer.end();
    }

    private void renderUI() {
        game.batch.begin();

        // Render HUD
        game.font.draw(game.batch, "Level: " + player.getLevelSystem().getCurrentLevel(), 10, PistoleroGame.HEIGHT - 10);
        game.font.draw(game.batch, "HP: " + player.getHealth() + "/" + player.getMaxHealth(), 10, PistoleroGame.HEIGHT - 30);
        game.font.draw(game.batch, "XP: " + player.getLevelSystem().getCurrentXP() + "/" + player.getLevelSystem().getXPToNextLevel(), 10, PistoleroGame.HEIGHT - 50);
        game.font.draw(game.batch, "Score: " + player.getScore(), 10, PistoleroGame.HEIGHT - 70);
        game.font.draw(game.batch, "Enemies: " + enemies.size(), 10, PistoleroGame.HEIGHT - 90);

        game.batch.end();
    }

    private Enemy findNearestEnemy() {
        Enemy nearest = null;
        float minDistance = Float.MAX_VALUE;

        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) continue;

            float distance = player.distanceTo(enemy);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = enemy;
            }
        }

        return nearest;
    }

    private void fireAtTarget(Enemy target) {
        Vector2 direction = new Vector2(
            target.getCenterX() - player.getCenterX(),
            target.getCenterY() - player.getCenterY()
        );

        // Fire multiple projectiles if upgraded
        int projectileCount = player.getProjectileCount();
        for (int i = 0; i < projectileCount; i++) {
            Bullet bullet = new Bullet(
                player.getCenterX(),
                player.getCenterY(),
                direction,
                400f,
                50
            );

            // Add spread for multiple projectiles
            if (projectileCount > 1) {
                float spreadAngle = (i - projectileCount / 2.0f) * 0.2f;
                Vector2 vel = bullet.getVelocity();
                float cos = (float) Math.cos(spreadAngle);
                float sin = (float) Math.sin(spreadAngle);
                float newX = vel.x * cos - vel.y * sin;
                float newY = vel.x * sin + vel.y * cos;
                vel.set(newX, newY);
            }

            bullets.add(bullet);
        }
    }

    private void spawnEnemyAtEdge() {
        float x, y;
        int edge = (int) (Math.random() * 4);

        switch (edge) {
            case 0: // Top
                x = (float) (Math.random() * PistoleroGame.WIDTH);
                y = PistoleroGame.HEIGHT;
                break;
            case 1: // Right
                x = PistoleroGame.WIDTH;
                y = (float) (Math.random() * PistoleroGame.HEIGHT);
                break;
            case 2: // Bottom
                x = (float) (Math.random() * PistoleroGame.WIDTH);
                y = 0;
                break;
            default: // Left
                x = 0;
                y = (float) (Math.random() * PistoleroGame.HEIGHT);
                break;
        }

        enemies.add(new Enemy(x, y, player, 3, 1, 80f));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        player.dispose();
    }
}
