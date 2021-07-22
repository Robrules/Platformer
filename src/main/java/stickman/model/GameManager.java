package stickman.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import stickman.level.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of GameEngine. Manages the running of the game.
 */
public class GameManager implements GameEngine {

    /**
     * The current level
     */
    private Level level;

    /**
     * The level careTaker obect
     */
    private LevelCareTaker careTaker;

    /**
     * The level originator object
     */
    private LevelOriginator originator;

    /**
     * List of all level files
     */
    private List<String> levelFileNames;

    /**
     * keeps track of current level in list of filenames
     */
    private Integer count;

    /**
     * Creates a GameManager object.
     * @param levels The config file containing the names of all the levels
     */

    public GameManager(String levels) {
        this.count = 0;
        this.levelFileNames = this.readConfigFile(levels);

        this.level = LevelBuilderImpl.generateFromFile(levelFileNames.get(count), this);

        this.careTaker = new LevelCareTaker();
        this.originator = new LevelOriginator();
    }
    @Override
    public LevelOriginator getOriginator() {
        return this.originator;
    }

    @Override
    public LevelCareTaker getCareTaker() {
        return this.careTaker;
    }

    @Override
    public  void setCurrentLevel(Level level) {
        this.level = level;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getLevelFileNames() {
        return this.levelFileNames;
    }

    @Override
    public void nextLevel() {
        GameManagerHelper gmHelper = new GameManagerHelper(this);
        gmHelper.nextLevel();

    }

    @Override
    public Level getCurrentLevel() {
        return this.level;
    }

    @Override
    public boolean jump() {
        return this.level.jump();
    }

    @Override
    public boolean moveLeft() {
        return this.level.moveLeft();
    }

    @Override
    public boolean moveRight() {
        return this.level.moveRight();
    }

    @Override
    public boolean stopMoving() {
        return this.level.stopMoving();
    }

    @Override
    public void tick() {
        this.level.tick();
    }

    @Override
    public void shoot() {
        this.level.shoot();
    }

    @Override
    public void reset() {
        this.level = LevelBuilderImpl.generateFromFile(this.level.getSource(), this);
    }

    /**
     * Retrieves the list of level filenames from a config file
     * @param config The config file
     * @return The list of level names
     */
    @SuppressWarnings("unchecked")
    private List<String> readConfigFile(String config) {

        List<String> res = new ArrayList<String>();

        JSONParser parser = new JSONParser();

        try {

            Reader reader = new FileReader(config);

            JSONObject object = (JSONObject) parser.parse(reader);

            JSONArray levelFiles = (JSONArray) object.get("levelFiles");

            Iterator<String> iterator = (Iterator<String>) levelFiles.iterator();

            // Get level file names
            while (iterator.hasNext()) {
                String file = iterator.next();
                res.add("levels/" + file);
            }

        } catch (IOException e) {
            System.exit(10);
            return null;
        } catch (ParseException e) {
            return null;
        }

        return res;
    }

}