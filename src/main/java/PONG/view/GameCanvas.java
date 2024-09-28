package PONG.view;
//--------------------------------------------------
//	IMPORTS
//--------------------------------------------------
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import PONG.model.Ball;
import PONG.model.Game;
import PONG.model.Player;
import PONG.model.Racket;
//--------------------------------------------------
//	CLASS GameCanvas
//--------------------------------------------------
/**
 * This class models a game canvas to display game objects on.
 */
public class GameCanvas extends Canvas {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private final GraphicsContext gc;
    private final int initialFontSize;
    private final double initialCanvasWidth;

    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Creates a Canvas object, and stores the initial width of the canvas <strong>and</strong>
     * the initial font size for resizing purposes.
     * @param width The width of the canvas.
     * @param height The height of the canvas.
     * @param initialFontSize The initial font size of the game canvas.
     */
    public GameCanvas(double width, double height, int initialFontSize) {
        super(width, height);
        setCursor(Cursor.CROSSHAIR);
        gc = this.getGraphicsContext2D();
        initialCanvasWidth = width;
        this.initialFontSize = initialFontSize;
    }
    //---------------------------------------
    //	GET METHODS
    //---------------------------------------
    /**
     * Gets the initial width of the canvas.
     * @return The width that was set when the object was first constructed.
     */
    public double getInitialCanvasWidth() {
        return initialCanvasWidth;
    }
    /**
     * Gets the initial font size of text-components of the canvas.
     * @return The width that was set when the object was first constructed.
     */
    public int getInitialFontSize() {
        return initialFontSize;
    }

    //---------------------------------------
    //	EXTRA METHODS
    //---------------------------------------
    //	Draw Methods
    //---------------------------------------
    /**
     * Draws the contents of the game (rackets, player details (score, name), ball, and background).
     * @param game The game containing the objects to be drawn.
     */
    public void drawGame(Game game){
        // INIT PLAYERS
        //---------------------------------------
        Player player1 = game.getPlayer1(), player2 = game.getPlayer2();
        // INIT POS VALUES
        //---------------------------------------
        double width, p1XPos, p2XPos, pNameYPos, pScoreYPos;
        // RESIZING BEFORE DECLARING POS VALUES
        //---------------------------------------
        resetSize(game);
        // DECLARING POS VALUES
        //---------------------------------------
        width = game.getGc().getWidth();
        p1XPos = width / 3;
        p2XPos = (width / 3) * 2;
        pNameYPos = 90;
        pScoreYPos = 130;
        // DRAWING THE CONTENTS OF THE GAME (background, ball, player details (name, score), and player rackets)
        //---------------------------------------
        drawBackground();
        drawBall(game.getBall());
        drawPlayerDetails(player1, p1XPos, pNameYPos, pScoreYPos);
        drawPlayerDetails(player2, p2XPos, pNameYPos, pScoreYPos);
    }

    /**
     * Draws the details of the player (including their associated racket).
     * @param player The player whose details <strong>and</strong> racket is to be drawn.
     * @param xPos The x coordinates to put the player name and score.
     * @param nameYPos The y coordinate to put the player's name.
     * @param scoreYPos The y coordinate to put the player's score.
     */
    private void drawPlayerDetails(Player player, double xPos, double nameYPos, double scoreYPos){
        drawText(player.getName(), xPos, nameYPos);
        drawText(String.valueOf(player.getScore()), xPos, scoreYPos);
        drawRacket(player.getRacket());
    }

    /**
     * Draws a solid black non-gradient background the width and height of the canvas.
     */
    private void drawBackground(){
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    /**
     * Draws bold Times New Roman white text at given coordinates.
     * @param str The text to be drawn.
     * @param x The x coordinated to draw the text.
     * @param y The y coordinates to draw the text
     */
    public void drawText(String str, double x, double y){
        double fontMultiplier, newFontSize;
        fontMultiplier = this.getWidth() / this.getInitialCanvasWidth();
        newFontSize = this.getInitialFontSize() * fontMultiplier;
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Times New Roman", FontWeight.BOLD, newFontSize));

        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(str, x, y);
    }

    /**
     * Draws a <strong>Ball</strong> object at x and y coordinates given by the Ball object.
     * @param ball The Ball object to be drawn.
     */
    private void drawBall(Ball ball){
        gc.setFill(ball.getColor());
        gc.fillOval(ball.getxDimension(), ball.getyDimension(), ball.getRadius(), ball.getRadius());
    }

    /**
     * Draws a <strong>Racket</strong> object at x and y coordinates given by the Ball object.
     * @param racket The Racket object to be drawn.
     */
    private void drawRacket(Racket racket){
        gc.setFill(racket.getColor());
        gc.fillRect(racket.getxDimension(), racket.getyDimension(), racket.getWidth(), racket.getHeight());
    }
    //	Reset Methods
    //---------------------------------------

    /**
     * Resets the position of the rackets and ball of a game.
     * @param r1 Player 1's Racket object.
     * @param r2 Player 2's Racket object.
     * @param ball The Ball of the Game.
     * @param game The Game which the rackets and ball belong to.
     */
    public void resetGameComponents(Racket r1, Racket r2, Ball ball, Game game){
        resetRacket(r1);
        resetRacket(r2);
        resetBall(ball, game);
    }

    /**
     * Resets the y position of the Racket.
     * @param r The racket whose position is to be reset.
     */
    private void resetRacket(Racket r) {
        r.setyDimension(this.getHeight()/2);
    }

    /**
     * Resets the x and y position of the Ball, the speed of the Ball, and the TimeLine that the
     * ball belongs to.
     * @param ball The Ball to be reset.
     * @param game The Game where the Ball and TimeLine is located.
     */
    private void resetBall(Ball ball, Game game) {
        // resets position of the ball
        ball.setxDimension(this.getWidth() / 2);
        ball.setyDimension(this.getHeight() / 2);
        // resets the speed
        ball.setSpeed(1);
        // resets the speed increase frequency
        game.getTimeline().playFromStart();
    }

    /**
     * Sets the width and height of the canvas according to the dimensions of the game.
     * @param game The game that the width and height of the canvas is set to.
     */
    private void resetSize(Game game){
        this.setWidth(game.getxDimension());
        this.setHeight(game.getyDimension());
    }
}
