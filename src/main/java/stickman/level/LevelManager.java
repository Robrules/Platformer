package stickman.level;

import stickman.entity.*;
import stickman.entity.moving.MovingEntity;
import stickman.entity.moving.other.Bullet;
import stickman.entity.moving.other.Projectile;
import stickman.entity.moving.player.Controllable;
import stickman.entity.moving.player.StickMan;
import stickman.entity.still.Lose;
import stickman.entity.still.Win;
import stickman.model.GameEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the Level interface. Manages the running of
 * the level and all the entities within it. Also acts as a Memento for the design pattern
 */
public class LevelManager implements Level {

    /**
     * The player character.
     */
    private Controllable hero;

    /**
     * A list of all the entities in the level.
     */
    private List<Entity> entities;

    /**
     * A list of all the moving entities in the level.
     */
    private List<MovingEntity> movingEntities;

    /**
     * A list of all the entities that can interact with the player.
     */
    private List<Interactable> interactables;

    /**
     * A list of all the projectiles (bullets) in the level.
     */
    private List<Projectile> projectiles;

    /**
     * The height of the level.
     */
    private double height;

    /**
     * The width of the level.
     */
    private double width;

    /**
     * The height of the floor in the level.
     */
    private double floorHeight;

    /**
     * Whether the entities should update, or the player has reached the flag.
     */
    private boolean active;

    /**
     * The name of the file the level is from.
     */
    private String filename;

    /**
     * The GameEngine the level is running inside of.
     */
    private GameEngine model;

    /**
     * Size of hero
     */
    private String heroSize;

    /**

    /**
     * Creates a new LevelManager object.
     * @param model The GameEngine the level is in
     * @param filename The file the level is based off of
     * @param height The height of the level
     * @param width The width of the level
     * @param floorHeight The height of the floor
     * @param heroX The starting x of the hero
     * @param heroSize The size of the hero
     * @param entities The list of entities in the level
     * @param movingEntities The list of moving entities in the level
     * @param interactables The list of entities that can interact with the hero in the level
     */
    public LevelManager(GameEngine model, String filename, double height, double width, double floorHeight, double heroX, int lives, String heroSize, List<Entity> entities, List<MovingEntity> movingEntities, List<Interactable> interactables) {
        this.model = model;
        this.filename = filename;
        this.height = height;
        this.width = width;
        this.floorHeight = floorHeight;
        this.entities = entities;
        this.movingEntities = movingEntities;
        this.interactables = interactables;
        this.heroSize = heroSize;

        this.projectiles = new ArrayList<>();

        // Create new hero
        this.hero = new StickMan(heroX, floorHeight, heroSize, this, lives);
        this.movingEntities.add(this.hero);

        // Ensure entities has all entities (including moving ones)
        this.entities.addAll(movingEntities);
        this.entities = new ArrayList<>(new HashSet<>(entities));

        this.active = true;

    }

    @Override
    public GameEngine getModel() {
        return this.model;
    }

    @Override
    public Level getState() {
        return this;
    }

    @Override
    public String getFileName() {
        return this.filename;
    }

    @Override
    public String getHeroSize() {
        return heroSize;
    }

    @Override
    public void setState(LevelManager level) {
        this.model = level.model;
        this.filename = level.filename;
        this.height = level.height;
        this.width = level.width;
        this.floorHeight = level.floorHeight;
        this.entities = level.entities;
        this.movingEntities = level.movingEntities;
        this.interactables = level.interactables;

        this.projectiles = new ArrayList<>();

        // Create new hero
        this.hero = new StickMan(level.getHeroX(), level.floorHeight, level.getHeroSize(), level, level.getHeroLives());
        this.movingEntities.add(this.hero);

        // Ensure entities has all entities (including moving ones)
        this.entities.clear();
        this.entities.addAll(level.movingEntities);
        this.entities = new ArrayList<>(new HashSet<>(entities));

        this.active = level.active;

    }

    @Override
    public List<Entity> getEntities() {
        return this.entities;
    }

    @Override
    public List<MovingEntity> getMovingEntities() {
        return this.movingEntities;
    }

    @Override
    public List<Interactable> getInteractables() {
        return this.interactables;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public void tick() {

        if (!active) {
            return;
        }


        for (MovingEntity entity : this.movingEntities) {
            entity.tick(this.entities, this.hero.getXPos(), this.floorHeight);
        }

        this.manageCollisions();

        // Remove inactive entities
        this.clearOutInactive();

    }

    /**
     * Removes inactive entities from all the lists.
     */
    private void clearOutInactive() {
        this.entities.removeIf(x -> !x.isActive());
        this.movingEntities.removeIf(x -> !this.entities.contains(x));
        this.interactables.removeIf(x -> !this.entities.contains(x));
        this.projectiles.removeIf(x -> !this.entities.contains(x));
    }

    /**
     * Calls interact methods on interactables and projectiles.
     */
    private void manageCollisions() {

        if (!entities.contains(this.hero)) {
            return;
        }

        // Collision between hero and other entity
        for (Interactable interactable : this.interactables) {
            if (interactable.checkCollide(hero)) {
                interactable.interact(hero);
            }
        }

        // Collision between bullet and moving entity (not hero)
        for (Projectile projectile : this.projectiles) {
            projectile.movingCollision(this.movingEntities.stream().filter(x -> x != hero).collect(Collectors.toList()));
        }

        // Collision between bullet and other entity
        for (Projectile projectile : this.projectiles) {
            projectile.staticCollision(this.entities.stream().filter(x -> x != hero).collect(Collectors.toList()));
        }
    }

    @Override
    public double getFloorHeight() {
        return this.floorHeight;
    }

    @Override
    public double getHeroX() {
        return this.hero.getXPos();
    }

    @Override
    public double getHeroY() {
        return this.hero.getYPos();
    }

    @Override
    public int getHeroLives() {
        return this.hero.getLives();
    }

    @Override
    public boolean jump() {
        if (!active) {
            return false;
        }
        return this.hero.jump();
    }

    @Override
    public boolean moveLeft() {
        if (!active) {
            return false;
        }
        return this.hero.moveLeft();
    }

    @Override
    public boolean moveRight() {
        if (!active) {
            return false;
        }
        return this.hero.moveRight();
    }

    @Override
    public boolean stopMoving() {
        if (!active) {
            return false;
        }
        return this.hero.stop();
    }

    @Override
    public void reset() {
        if (this.model != null) {
            this.model.reset();
        }
    }

    @Override
    public void shoot() {
        if (!this.hero.upgraded() || !active) {
            return;
        }

        double x = this.hero.getXPos() + this.hero.getWidth();

        if (this.hero.isLeftFacing()) {
            x = this.hero.getXPos();
        }

        Projectile bullet = new Bullet(x, this.hero.getYPos() + (2 * this.hero.getWidth() / 3), this.hero.isLeftFacing());

        this.entities.add(bullet);
        this.movingEntities.add(bullet);
        this.projectiles.add(bullet);
    }

    @Override
    public String getSource() {
        return this.filename;
    }

    @Override
    public void win() {
        this.active = false;

        this.entities.add(new Win(hero.getXPos() - 200, hero.getYPos() - 200));
        model.nextLevel();
    }

    @Override
    public void lose() {
        this.active = false;
        this.entities.add(new Lose(hero.getXPos() - 50, hero.getYPos() - 200));

    }
}
