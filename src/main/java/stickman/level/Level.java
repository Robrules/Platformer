package stickman.level;

import stickman.entity.Entity;
import stickman.entity.Interactable;
import stickman.entity.moving.MovingEntity;
import stickman.model.GameEngine;

import java.util.List;

/**
 * The interface describing the behaviours of a Level.
 */
public interface Level {

    /**
     * Gets all the entities within the Level.
     * @return All the entities within the level
     */
    List<Entity> getEntities();

    /**
     * Gets all the  interactable entities within the Level.
     * @return All the interactable entities within the level
     */
    List<Interactable> getInteractables();

    /**
     * Gets all the moving entities within the Level.
     * @return All the moving entities within the level
     */
    List<MovingEntity> getMovingEntities();

    /**
     * Gets the height of the level.
     * @return The height of the level
     */
    double getHeight();

    /**
     * Gets the width of the level.
     * @return The width of the level
     */
    double getWidth();

    /**
     * Updates the level every frame.
     */
    void tick();

    /**
     * Gets the height of the floor.
     * @return The height of the floor
     */
    double getFloorHeight();

    /**
     * Gets the x-coordinate of the player character.
     * @return The x-coordinate of the player
     */
    double getHeroX();

    /**
     * Gets the y-coordinate of the player character.
     * @return The y-coordinate of the player
     */
    double getHeroY();

    /**
     * Gets the amount of lives left of a stickman
     * @return amount of lives left
     */
    int getHeroLives();

    /**
     * Makes the player jump.
     * @return Whether the player jumped
     */
    boolean jump();

    /**
     * Makes the player move left.
     * @return Whether the player moved left
     */
    boolean moveLeft();

    /**
     * Makes the player move right.
     * @return Whether the player moved right
     */
    boolean moveRight();

    /**
     * Stops all horizontal movement of the player.
     * @return Whether the player stopped moving
     */
    boolean stopMoving();

    /**
     * Resets the level.
     */
    void reset();

    /**
     * Makes the player shoot.
     */
    void shoot();

    /**
     * Returns the source file of the level.
     * @return The file the level is based off of
     */
    String getSource();

    /**
     * Stops level and shows victory message.
     */
    void win();

    /**
     * Stops level and shows game over message.
     */
    void lose();

    /**
     * Get level state
     */
    Level getState();

    /**
     * Get level state
     */
    void setState(LevelManager level);

    /**
     * Get level state
     */
    String getHeroSize();

    /**
     * Get fileName
     */
    String getFileName();

    /**
     * Get model
     */
    GameEngine getModel();
}
