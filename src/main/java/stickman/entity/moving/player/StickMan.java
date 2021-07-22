package stickman.entity.moving.player;

import stickman.entity.Entity;
import stickman.entity.moving.MovingObject;
import stickman.level.Level;

import java.util.List;

/**
 * The player controlled character.
 */
public class StickMan extends MovingObject implements Controllable {

    /**
     * The maximum speed the player can move horizontally.
     */
    public static final double HORIZONTAL_SPEED = 1;

    /**
     * The height of the player when normal sized.
     */
    public static final double NORMAL_HEIGHT = 40;

    /**
     * The width of the player when normal sized.
     */
    public static final double NORMAL_WIDTH = 24;

    /**
     * The height of the player when large.
     */
    public static final double LARGE_HEIGHT = 60;

    /**
     * The width of the player when large.
     */
    public static final double LARGE_WIDTH = 36;

    /**
     * The possible sizes the player can be.
     */
    private enum Size {
        NORMAL,
        LARGE
    }

    /**
     * The current size of the player.
     */
    private Size size;

    /**
     * The level the player exists within.
     */
    private Level level;

    /**
     * Whether the player is pressing left.
     */
    private boolean left;

    /**
     * Whether the player is pressing right.
     */
    private boolean right;

    /**
     * Whether the hero has upgraded.
     */
    private boolean upgraded;

    /**
     * Whether the sprite is facing left.
     */
    private boolean leftFacing;

    /**
     * Number of lives the stickman has
     */
    private int lives;

    /**
     * Creates a new StickMan object.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param size The size of the player.
     * @param level The level the player exists within.
     * @param lives the lives that a stickman starts with for this level
     */
    public StickMan(double x, double y, String size, Level level, int lives) {
        super("ch_stand1.png", x, y, 0, 0, Layer.FOREGROUND);

        this.level = level;
        this.upgraded = false;
        this.leftFacing = false;
        this.lives = lives;

        if (size.equals("normal")) {
            this.size = Size.NORMAL;
            this.width = NORMAL_WIDTH;
            this.height = NORMAL_HEIGHT;
        } else if (size.equals("large")) {
            this.size = Size.LARGE;
            this.width = LARGE_WIDTH;
            this.height = LARGE_HEIGHT;
        }

        this.yPos -= this.height;
    }

    @Override
    public void tick(List<Entity> entities, double heroX, double floorHeight) {

        this.gravity(entities, floorHeight);
        this.yPos += this.yVelocity;

        this.horizontalMove(entities);
        this.xPos += this.xVelocity;

    }

    /**
     * Updates x-velocity based on the proximity of other entities.
     * @param entities The other entities in the scene
     */
    private void horizontalMove(List<Entity> entities) {
        if (left) {
            this.xVelocity = Math.max(-HORIZONTAL_SPEED, -horizontalRaycast(true, entities, 0));
        } else if (right) {
            this.xVelocity = Math.min(HORIZONTAL_SPEED, horizontalRaycast(false, entities, level.getWidth()));
        } else {
            this.xVelocity = 0;
        }
    }

    @Override
    public boolean moveLeft() {
        this.left = true;
        this.right = false;
        faceLeft();
        return horizontalRaycast(true, this.level.getEntities(), 0) > 0;
    }

    @Override
    public boolean moveRight() {
        this.right = true;
        this.left = false;
        faceRight();
        return horizontalRaycast(false, this.level.getEntities(), 0) > 0;
    }

    @Override
    public boolean jump() {
        if (canJump()) {
            this.yVelocity = -4;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the player is able to jump.
     * @return Whether the player can jump
     */
    private boolean canJump() {
        return verticalRaycast(false, this.level.getEntities(), this.level.getFloorHeight()) < 0.05;
    }

    @Override
    public boolean stop() {
        this.xVelocity = 0;
        this.left = false;
        this.right = false;
        return true;
    }

    @Override
    public void upgrade() {
        this.upgraded = true;
    }

    @Override
    public int getLives() {
        return this.lives;
    }

    @Override
    public void reduceLife() {

        this.lives -=1;
    }

    @Override
    public void die() {
        reduceLife();
        if (getLives() == 0) {
            lose();
        } else {
            this.active = false;

            if (this.level != null) {
                this.level.reset();
            }
        }



    }

    @Override
    public boolean isLeftFacing() {
        return this.leftFacing;
    }

    @Override
    public boolean upgraded() {
        return this.upgraded;
    }

    @Override
    public void win() {
        this.level.win();
    }

    @Override
    public void lose() {
        this.level.lose();
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    /**
     * Turns the player left and updates the sprite.
     */
    private void faceLeft() {
        this.leftFacing = true;
        this.imagePath = "ch_stand4.png";
    }

    /**
     * Turns the player right and updates the sprite.
     */
    private void faceRight() {
        this.leftFacing = false;
        this.imagePath = "ch_stand1.png";
    }

}
