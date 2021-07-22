package stickman.level;


/**
 * Responsible for creating and storing level states
 */
public class LevelOriginator {
    /**
     * The level state
     */
    private LevelManager levelManager;

    /**
     * Sets the state of the level to the new state
     */
    public void setState(LevelManager level) {
        levelManager.setState(level);

    }
    /**
     * Returns the stored level state
     * @return levelManager: the level state
     */
    public LevelManager getState() {
        return levelManager;
    }

    /**
     * Retrieve the current level state from the memento
     */
    public void getStateFromMemento(LevelManager lm) {
        levelManager = (LevelManager) lm.getState();
    }

    /**
     * Updates the level state to the saved state
     * @return the new state
     */
    public LevelManager saveStateToMemento() {
        return new LevelManager(levelManager.getModel(),levelManager.getFileName(),
                levelManager.getHeight(),levelManager.getWidth()
        ,levelManager.getFloorHeight(),levelManager.getHeroX(),levelManager.getHeroLives(),
                levelManager.getHeroSize(),levelManager.getEntities(),
                levelManager.getMovingEntities(),levelManager.getInteractables());
    }
}
