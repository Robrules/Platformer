package stickman.level;
import java.util.ArrayList;
import java.util.List;

/**
 * LevelCareTaker class: Responsible for restoring a saved state into the memento
 */
public class LevelCareTaker {

    /**
     * List of saved states
     */
    private List<LevelManager> mementoList = new ArrayList<LevelManager>();

    /**
     * Add a saved state to mementoList
     * @param level the saved state
     */
    public void add(LevelManager level) {
        mementoList.add(level);
    }

    /**
     * Retrieve a saved state from the mementoList
     * @param index the saved state number to retrieve
     */
    public LevelManager get(int index) {
        return mementoList.get(index);
    }


}
