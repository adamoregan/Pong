package PONG.controller;
//---------------------------------------
//	IMPORTS
//---------------------------------------
import PONG.model.Ball;
import PONG.model.Game;
import PONG.model.Player;
import PONG.model.Racket;
import PONG.view.GameCanvas;
//--------------------------------------------------
//	CLASS CollisionController
//--------------------------------------------------
/**
 * This class models a CollisionController in the PONG game.<br>
 * It detects collisions between the game components (ball, racket, scene borders).
 */
public class CollisionController {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private final Game game;
    private int totalCollisions = 0;
    private int numColTracker;
    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Creates an instance of the CollisionController Class.
     * @param game The Game class the CollisionController detects the collisions of.
     * @param numColTracker The amount of collisions to be tracked (for example, after x collisions, something happens).
     */
    public CollisionController(Game game, int numColTracker) {
        this.game = game;
        this.numColTracker = numColTracker;
    }
    //---------------------------------------
    //	GET METHODS
    //---------------------------------------
    /**
     * Gets the total number of collisions which have occured.
     * @return The total number of collisions.
     */
    public int getTotalCollisions() { return totalCollisions; }

    /**
     * Gets the collision tracker.
     * @return The collision tracker.
     */
    public int getNumColTracker() { return numColTracker; }
    //---------------------------------------
    //	SET METHODS
    //---------------------------------------

    /**
     * Sets the total amount of collisions.
     * @param collisions The amount of collisions.
     */
    private void setTotalCollisions(int collisions) { totalCollisions = collisions; }

    /**
     * Sets the amount of collisions to track.
     * @param numColTracker The amount of collisions to track.
     */
    public void setNumColTracker(int numColTracker) { this.numColTracker = numColTracker; }

    /**
     * Increments the number of total collisions by 1.
     */
    private void incrementTotalCollisions() { setTotalCollisions(getTotalCollisions()+1); }

    //---------------------------------------
    //	EXTRA METHODS
    //---------------------------------------

    /**
     * Increments the total collisions, and checks if the number of collisions is equals to the
     * number collision tracker (numColTracker).
     * @param ball The Ball whose collisions are being tracked.
     */
    private void handleCollision(Ball ball) {
        incrementTotalCollisions();
        handleNumBounces(ball);
    }

    /**
     * Checks if the number of collisions for the collision tracker has been met.<br>
     * If so, then increments the speed of the ball by 1.
     * @param ball The Ball class whose speed is increased.
     */
    private void handleNumBounces(Ball ball){
        if (this.getTotalCollisions() % this.getNumColTracker() == 0) {
            System.out.println("!Bounce - Speed Increased!");
            ball.incrementSpeed();
        }
    }

    /**
     * Checks if there has been a collision between the passed Ball object and the passed Racket object
     * by comparing their x and y coordinates.
     * @param ball The Ball which contains x and y coordinates
     * @param racket The Racket that collides with the Ball.
     * @return If there has been a collision.
     */
    public boolean isRacket1Col(Ball ball, Racket racket){
        if (ball.getxDimension() <= racket.getxDimension() + racket.getWidth() &&
                ball.getyDimension() + ball.getRadius() >= racket.getyDimension() &&
                ball.getyDimension() <= racket.getyDimension() + racket.getHeight()){
            handleCollision(ball);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if there has been a collision between the passed Ball object and the passed Racket object
     * by comparing their x and y coordinates.
     * @param ball The Ball which contains x and y coordinates
     * @param racket The Racket that collides with the Ball.
     * @return If there has been a collision.
     */
    public boolean isRacket2Col(Ball ball, Racket racket) {
        if (ball.getxDimension() + ball.getRadius() >= racket.getxDimension() &&
                ball.getyDimension() + ball.getRadius() >= racket.getyDimension() &&
                ball.getyDimension() <= racket.getyDimension() + racket.getHeight()){
            handleCollision(ball);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if there has been a collision between the top of the scene and the passed Ball object, ball.
     * @param ball The Ball object whose y coordinates are compared with the top of the scene (0).
     * @return If there has been a collision.
     */
    public boolean isTopSceneCol(Ball ball) {
        if (ball.getyDimension() <= 0) {
            handleCollision(ball);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Checks if there has been a collision between the bottom of the scene and the passed Ball object, ball.
     * @param ball The Ball object whose y coordinates are compared with the height of the canvas.
     * @param canvas The canvas where ball is drawn on.
     * @return If there has been a collision.
     */
    public boolean isBottomSceneCol(Ball ball, GameCanvas canvas) {
        if (ball.getyDimension() + ball.getRadius() >= canvas.getScene().getHeight() - 25){
            handleCollision(ball);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if there has been a collision with the left-border of the scene and a Ball.
     * @param ball The Ball whose collision is checked with the left of the scene.
     * @return If there has been a collision.
     */
    public boolean isLeftSceneCol(Ball ball){
        return (ball.getxDimension() <= 0);
    }

    /**
     * Checks if there has been a collision with the right-border of the scene and a Ball.
     * @param ball The Ball whose collision is checked with the right of the scene.
     * @param canvas The GameCanvas whose collision is checked with the Ball.
     * @return If there has been a collision.
     */
    public boolean isRightSceneCol(Ball ball, GameCanvas canvas){
        return (ball.getxDimension() + ball.getRadius() >= canvas.getScene().getWidth());
    }

    /**
     * Increment's the Player's score by 1, checks if a win has occured,
     * and delays displays text to the canvas showing that a score/win has occured,
     * @param scoringPlayer The Player who has scored.
     * @param otherPlayer The other Player who has <strong>NOT</strong> scored.
     */
    public void manageScore(Player scoringPlayer, Player otherPlayer){
        // the player's score is incremented by 1
        scoringPlayer.score();
        GameCanvas gc = game.getGc();
        if (hasPlayerWon(scoringPlayer)){
            manageWin(scoringPlayer, otherPlayer, gc);
            // the text is displayed for 5 seconds
            game.getBallManager().delay(5000);
        } else {
            System.out.println(scoringPlayer.getName() + " scored!");
            // shows a score message in the middle of the screen
            gc.drawText(scoringPlayer.getName() + " scored!", gc.getWidth() / 2, gc.getHeight() / 2 );
            // the text is displayed for 2 seconds
            game.getBallManager().delay(2000);
        }
    }

    /**
     * Checks if a Player's score is greater than or equals to the max score of the Game.
     * @param player The Player whose score is checked.
     * @return If the Player's score is greater than or equals to the max score of the Game.
     */
    public boolean hasPlayerWon(Player player) {
        return (player.getScore() >= game.getMaxScore());
    }

    /**
     * Prints text to display that a Player won, and resets the score of each Player.
     * @param winningPlayer The Player who has won.
     * @param losingPlayer The Player who has not won.
     * @param gc The GameCanvas where the winning text is displayed.
     */
    private void manageWin(Player winningPlayer, Player losingPlayer, GameCanvas gc){
        System.out.println(winningPlayer.getName() + " wins!");
        gc.drawText("! " + winningPlayer.getName() + " wins!", gc.getWidth() / 2, gc.getHeight() / 2 );
        winningPlayer.setScore(0);
        losingPlayer.setScore(0);
    }
}
