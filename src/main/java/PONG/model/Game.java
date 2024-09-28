package PONG.model;
//---------------------------------------
//	IMPORTS
//---------------------------------------
import PONG.controller.*;
import PONG.dao.DatabaseManager;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import PONG.view.GameCanvas;
import PONG.view.GameMenu;
import java.io.Serializable;
//--------------------------------------------------
//	CLASS Game
//--------------------------------------------------

/**
 * This class models a Game in the PONG application. This game has 2 players (with 2 rackets), a ball,
 * a max score, pause functionality, a width and a height.
 */
public class Game extends Application implements Resizable, Serializable {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    //	Game Elements
    //---------------------------------------
    private String name;
    private Player player1;
    private Player player2;
    private transient final Ball ball;
    private int maxScore;
    private double xDimension;
    private double yDimension;
    private boolean isPaused = false;
    //  View Elements
    //---------------------------------------
    private transient final GameCanvas gc;
    private transient final GameMenu gameMenu;
    //  Controller Elements
    //---------------------------------------
    private transient BallManager ballManager;
    private transient final GameController gameController;
    private transient final CollisionController collisionController;
    private transient final DatabaseManager databaseManager;
    //  Timeline
    //---------------------------------------
    private transient final Timeline timeline;
    //  KeyBoardListener
    //---------------------------------------
    private transient KeyboardListener keyboardListener;
    //---------------------------------------
    //	Constructor
    //---------------------------------------

    /**
     * Creates an instance of the Game class, setting the width/height, and setting the variables for the rackets, players, ball.<br>
     * The controllers, menu, and canvas are also initialised.
     */
    public Game(){
        name = "My Game";
        double racketWidth, racketHeight, racketOffset, p1XPos, p2XPos, racketYPos, ballXPos, ballYPos;
        //  Speed + Score Vars.
        //---------------------------------------
        int racketSpeed = 15, ballSpeed = 1, numColTracker;
        maxScore = 10;
        //  Col Vars.
        //---------------------------------------
        numColTracker = 10;
        //  Canvas Vars.
        //---------------------------------------
        xDimension = 400;
        yDimension = 400;
        //  Racket Vars.
        //---------------------------------------
        racketWidth = 30;
        racketHeight = 100;
        racketOffset = 30;
        //  Pos Vars.
        //---------------------------------------
        p1XPos = racketOffset;
        p2XPos =  xDimension - racketWidth - racketOffset;
        racketYPos = yDimension / 2;
        ballXPos = xDimension / 2;
        ballYPos = yDimension / 2;
        //	Init. Game Elems
        //---------------------------------------
        // BALL
        ball = new Ball(ballXPos, ballYPos, 10, ballSpeed, 10, Color.PURPLE);
        // PLAYER 1
        Racket p1Racket = new Racket(p1XPos, racketYPos, racketHeight, racketWidth, racketSpeed, Color.BLUE);
        player1 = new Player("Player 1", 0, p1Racket);
        // PLAYER 2
        Racket p2Racket = new Racket(p2XPos, racketYPos, racketHeight, racketWidth, racketSpeed, Color.RED);
        player2 = new Player("Player 2", 0, p2Racket);
        //  Init. Controller Elems
        //---------------------------------------
        gameController = new GameController(this);
        collisionController = new CollisionController(gameController.getGame(), numColTracker);
        MenuListener menuListener = new MenuListener(gameController.getGame());
        databaseManager = new DatabaseManager();
        //  Init. View Elems
        //---------------------------------------
        gc = new GameCanvas(xDimension, yDimension, 12);
        gameMenu = new GameMenu(menuListener);
        //  Init. Timeline
        //---------------------------------------
        timeline = new Timeline();
        // adding ball speed increase keyframe
        timeline.getKeyFrames().add(ball.getSpeedIncreaseFrequency()); // by default increases speed every 10 seconds
        // repeats indefinitely
        timeline.setCycleCount(Timeline.INDEFINITE);
        // starts the timeline
        timeline.play();
    }
    /**
     * Loads an icon image for the stage.
     * @param stage    The stage the image is to be displayed on.
     * @param filename The name of the file to be displayed.
     */
    public void loadIcon(Stage stage, String filename) {
        try {
            Image icon = new Image(filename);
            stage.getIcons().add(icon);
        } catch (IllegalArgumentException e) {
            System.out.println(filename + " could not be found.");
        }
    }
    /**
     * Starts the Game class, displaying the canvas on the stage.
     * @param stage The Stage where the canvas and game elements are placed on.
     */
    @Override
    public void start(Stage stage) {
        //  Resizing
        //---------------------------------------
        // WIDTH
        stage.widthProperty().addListener(o -> {
            double factor = stage.getWidth()/gameController.getGame().getxDimension();
            System.out.println("Width changed: " + stage.getWidth() + " " + factor);
            gameController.getGame().setxDimension(stage.getWidth());
            gameController.getGame().resizeX(factor);
            gc.drawGame(gameController.getGame());

        });
        // HEIGHT
        stage.heightProperty().addListener(o -> {
            double factor= stage.getHeight()/gameController.getGame().getyDimension();
            System.out.println("Height changed " + stage.getHeight()+" "+factor);
            gameController.getGame().setyDimension(stage.getHeight());
            gameController.getGame().resizeY(factor);
            gc.drawGame(gameController.getGame());
        });
        //  Layout
        //---------------------------------------
        // ROOT
        BorderPane root = new BorderPane();
        // GAME MENU - (setting as top)
        root.setTop(gameMenu.getMb());
        // CANVAS - (set as center)
        root.setCenter(gc);
        // SCENE
        Scene scene = new Scene(root);
        //  Stage Settings
        //---------------------------------------
        stage.setScene(scene);
        stage.setMinHeight(xDimension);
        stage.setMinWidth(yDimension);
        // ICON
        loadIcon(stage, "icon.png");
        // TITLE
        stage.setTitle("PONG");
        stage.show();
        //  View
        //---------------------------------------
        gc.drawGame(gameController.getGame());
        //  Controller
        //---------------------------------------
        keyboardListener = new KeyboardListener(gameController.getGame(), gc);
        gc.setOnKeyPressed(keyboardListener);
        gc.setFocusTraversable(true);

        ballManager = new BallManager(gameController.getGame(), gc);
        //  Threading
        //---------------------------------------
        Thread thread = new Thread(ballManager);
        thread.start();
        Thread.yield();
    }

    /**
     * The entrypoint for the Application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    //---------------------------------------
    //	GET METHODS
    //---------------------------------------

    /**
     * Gets the name of the Game.
     * @return The name of the Game.
     */
    public String getName() { return name; }
    /**
     * Gets Player 1.
     * @return Player 1.
     */
    public Player getPlayer1() { return player1; }
    /**
     * Gets Player 2.
     * @return Player 2.
     */
    public Player getPlayer2() { return player2; }
    /**
     * Gets the Ball for the Game.
     * @return The Ball.
     */
    public Ball getBall() { return ball; }
    /**
     * Gets the max score of the Game.
     * @return The max score.
     */
    public int getMaxScore(){ return maxScore; }

    /**
     * Gets the width of the Game.
     * @return The width of the Game.
     */
    public double getxDimension() { return xDimension; }

    /**
     * Gets the height of the Game.
     * @return The height of the Game.
     */
    public double getyDimension()  { return yDimension; }

    /**
     * Gets if the Game is paused or not.
     * @return If the Game is paused.
     */
    public boolean isPaused() {
        return isPaused;
    }
    /**
     * Gets the GameCanvas of the Game.
     * @return The GameCanvas.
     */
    public GameCanvas getGc() { return gc; }

    /**
     * Gets the Timeline of the Game.
     * @return The Timeline of the Game.
     */
    public Timeline getTimeline() {
        return timeline;
    }

    /**
     * Gets the CollisionController of the Game.
     * @return The CollisionController of the Game.
     */
    public CollisionController getCollisionController() { return collisionController; }

    /**
     * Gets the BallManager of the Game which controls the movement of the Game's Ball.
     * @return The BallManager of the Game.
     */
    public BallManager getBallManager() {
        return ballManager;
    }

    /**
     * Gets the GameController of the Game which controls the changing of the Game.
     * @return The GameController of the Game.
     */
    public GameController getGameController() {
        return gameController;
    }
    /**
     * Gets the DatabaseManager of the Game which controls the loading and saving of the details of the Game
     * to the database.
     * @return The DatabaseManager of the Game.
     */
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    //---------------------------------------
    //	SET METHODS
    //---------------------------------------
    /**
     * Sets the name of the Game.
     * @param name The name to set the name of the Game to.
     */
    public void setName(String name){ this.name = name; }

    /**
     * Sets player 1 of the Game.
     * @param p1 The player to set to player 1.
     */
    public void setPlayer1(Player p1){
        player1.setName(p1.getName());
        player1.setScore(p1.getScore());
    }
    /**
     * Sets player 2 of the Game.
     * @param p2 The player to set to player 2.
     */
    public void setPlayer2(Player p2){
        player2.setName(p2.getName());
        player2.setScore(p2.getScore());
    }
    /**
     * Sets the max score of the Game.
     * @param score The score to set the max score to.
     */
    public void setMaxScore(int score){ maxScore = score; }

    /**
     * Sets the width of the Game.
     * @param x The width to set the Game to.
     */
    public void setxDimension(double x){ xDimension = x; }

    /**
     * Sets the height of the Game.
     * @param y The height to set the Game to.
     */
    public void setyDimension(double y){ yDimension = y; }

    /**
     * Sets if the Game is paused.
     * @param isPaused If the Game is paused or not.
     */
    public void setPaused(boolean isPaused){ this.isPaused = isPaused; }

    //---------------------------------------
    //	EXTRA METHODS
    //---------------------------------------
    //  Resizing
    //---------------------------------------
    /**
     * Resizes the Game's Rackets and Ball depending on the width.
     * @param factor The factor to multiply the position and width by.
     */
    public void resizeX(double factor) {
        player1.getRacket().resizeX(factor);
        player2.getRacket().resizeX(factor);
        ball.resizeX(factor);
    }

    /**
     * Resizes the Game's Rackets and Ball depending on the height.
     * @param factor The factor to multiply the position and height by.
     */
    public void resizeY(double factor) {
        player1.getRacket().resizeY(factor);
        player2.getRacket().resizeY(factor);
        ball.resizeY(factor);
    }
}
