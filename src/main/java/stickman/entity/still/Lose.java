package stickman.entity.still;

import stickman.entity.GameObject;

/**
 * The gameOver message displayed after losing all lives
 */
public class Lose extends GameObject {

    /**
     * Constructs a new Lose object.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public Lose(double x, double y) {
        super("gameOver.png", x, y, 400, 400, Layer.EFFECT);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}