package stickman.model;

import stickman.level.LevelBuilderImpl;
import stickman.level.Level;

/**
 * Assists GameManager with functions, to reduce clutter in GameManager
 */

public class GameManagerHelper {
    /**
     * Game Manager object
     */
    private GameManager gameManager;


    /**
     * Creates a GameManagerHelper object.
     * @param gameManager The gameManager object
     */
    public GameManagerHelper(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Assists in progressing gameManager to next level
     */
    public void nextLevel() {
        int count = this.gameManager.getCount();
        count++;

        int fileSize = gameManager.getLevelFileNames().size();
        fileSize-=1;

        if (!(gameManager.getCount() >= fileSize)) {
            Level currentLevel = LevelBuilderImpl.generateFromFile(this.gameManager.getLevelFileNames().get(count), this.gameManager);
            this.gameManager.setCurrentLevel(currentLevel);
            this.gameManager.setCount(count);
        }
    }

}
