#StickMan

##Run
Run the code with `gradle run`

##Features
* Level transitions: When flag of last level is reached, 'You win' will display
* Configuration level files altered to include stickman lives and target time
* Players initial score and time elapsed (not fully functionable) is displayed on screen
* Amount of lives a stickman has is displayed and updates according to the level and lives remaining
* If all lives lost, game finishes and 'Game Over' is displayed
* User can save state of game (not fully functional)
* User can load previous save (loads to beginning of current level)


##Design Patterns
* Memento Design Pattern: LevelManager, LevelOriginator, LevelCareTaker
* Facade Design Pattern: GameEngine, GameManager, GameManagerHelper

##Controls for load/save
* Save: press 's'
* Load: press 'q'

# Other Info
* The stickman lives displays correctly on screen, however the text draws on top of each other which makes it
* a little difficult to see