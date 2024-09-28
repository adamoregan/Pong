package PONG.view;
//--------------------------------------------------
//	IMPORTS
//--------------------------------------------------
import PONG.controller.MenuListener;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.CheckMenuItem;          // used to easily have a pause button
//--------------------------------------------------
//	CLASS GameMenu
//--------------------------------------------------
/**
 * This class models a menu for a Game.
 */
public class GameMenu {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private final MenuBar menuBar;
    private final MenuListener menuListener;
    private final Menu playerMenu;
    private final MenuItem player1Name;
    private final MenuItem player2Name;
    private final Menu ballMenu;
    private final MenuItem ballSpeed;
    private final MenuItem ballSpeedConfig;
    private final MenuItem ballBounceConfig;
    private final Menu racketMenu;
    private final MenuItem racketHeight;
    private final MenuItem racketWidth;
    private final Menu gameSettings;
    private final MenuItem changeGameName;
    private final MenuItem maxScore;
    private final MenuItem exit;
    private final CheckMenuItem pause;
    private final MenuItem restart;
    private final Menu gameLoadingAndSaving;
    private final MenuItem databaseLoading;
    private final MenuItem databaseSaving;
    private final MenuItem serialize;
    private final MenuItem deserialize;
    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Creates an instance of the GameMenu class with a MenuListener.
     * @param menuListener The MenuListener that listens to each MenuItem in the Menu.
     */
    public GameMenu(MenuListener menuListener){
        this.menuListener = menuListener;
        menuBar = new MenuBar();
        playerMenu = new Menu("Player");
        player1Name = new MenuItem("Player1 Name");
        player2Name = new MenuItem("Player2 Name");
        playerMenu.getItems().addAll(player1Name, player2Name);

        ballMenu = new Menu("Ball");
        ballSpeed = new MenuItem("Set Speed");
        ballSpeedConfig = new MenuItem("Speed Increase Frequency");
        ballBounceConfig = new MenuItem("Bounce Speed Increase");
        ballMenu.getItems().addAll(ballSpeed, ballSpeedConfig, ballBounceConfig);

        racketMenu = new Menu("Racket");
        racketHeight = new MenuItem("Height");
        racketWidth = new MenuItem("Width");
        racketMenu.getItems().addAll(racketHeight, racketWidth);


        gameSettings = new Menu("Game");
        changeGameName = new MenuItem("Name");
        maxScore = new MenuItem("Max Score");
        restart = new MenuItem("Restart");
        pause = new CheckMenuItem("Pause");
        exit = new MenuItem("Exit");

        gameSettings.getItems().addAll(changeGameName, maxScore, new SeparatorMenuItem(), restart, pause, exit);

        gameLoadingAndSaving = new Menu("Saving/Loading");
        deserialize = new MenuItem("Load from File");
        serialize = new MenuItem("Save to File");
        databaseLoading = new MenuItem("Load from Database");
        databaseSaving = new MenuItem("Save to Database");

        gameLoadingAndSaving.getItems().addAll(serialize, deserialize, new SeparatorMenuItem(), databaseSaving, databaseLoading);

        // Adding all menus to the menu-bar
        menuBar.getMenus().addAll(playerMenu, ballMenu, racketMenu, gameSettings, gameLoadingAndSaving);
        handleClicking();
    }
    //---------------------------------------
    //	GET METHODS
    //---------------------------------------
    /**
     * Gets the MenuBar of the menu.
     * @return The MenuBar of the menu.
     */
    public MenuBar getMb(){ return menuBar; }

    //---------------------------------------
    //  EXTRA METHODS
    //---------------------------------------
    /**
     * Handles what happens when a MenuItem is clicked in the Menu.
     */
    private void handleClicking(){
        // PLAYER Listeners
        player1Name.setOnAction(e -> menuListener.setPlayer1Name());
        player2Name.setOnAction(e -> menuListener.setPlayer2Name());
        // BALL Listeners
        ballSpeed.setOnAction(e -> menuListener.setBallSpeed());
        ballSpeedConfig.setOnAction(e -> menuListener.setBallSpeedConfig());
        ballBounceConfig.setOnAction(e -> menuListener.setBallBounceConfig());
        // RACKET Listeners
        racketHeight.setOnAction(e -> menuListener.setRacketHeight());
        racketWidth.setOnAction(e -> menuListener.setRacketWidth());
        // ADDITIONAL Listeners
        changeGameName.setOnAction(e -> menuListener.setGameName());
        maxScore.setOnAction(e -> menuListener.setMaxScore());
        restart.setOnAction(e -> menuListener.setRestart());
        pause.setOnAction(e -> menuListener.setPaused(pause));
        exit.setOnAction(e -> menuListener.setExit());
        // LOADING & SAVING Listeners
        deserialize.setOnAction(e -> menuListener.deserialize());
        serialize.setOnAction(e -> menuListener.serialize());
        databaseLoading.setOnAction(e -> menuListener.setDatabaseLoad());
        databaseSaving.setOnAction(e -> menuListener.setDatabaseSave());
    }
}
