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
//	CLASS BallManager
//--------------------------------------------------
/**
 * This class models a ball manager in the PONG game.<br>
 * It controls directional movement of the Ball and what occurs when the Ball interacts
 * with other game components (such as Racket(s), and the scene border).
 */
public class BallManager implements Runnable {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private final Game game;
    private final GameCanvas canvas;

    //---------------------------------------
    //	Constructor
    //---------------------------------------

    /**
     * Creates an instance of the BallManager class
     * @param game The Game class that the BallManger belongs to (Game contains - Players, Rackets, a Ball,
     *             and a CollisionController).
     * @param canvas The GameCanvas class where the PONG game is drawn. <br>
     *               It is needed for rendering the Canvas again after every Ball movement, and resetting the
     *               game components when there is a score/win.
     */
    public BallManager(Game game, GameCanvas canvas) {
        this.game = game;
        this.canvas = canvas;
    }

    //---------------------------------------
    //	RUN METHOD
    //---------------------------------------
    @Override
    public void run() {
        int direction;
        // randomises the initial direction of the ball
        // Gets us either 1, 2, 3, or 4 - to account for 4 possible directions
        direction  = (int) (Math.random() * 4) + 1;
        Ball ball = game.getBall();
        Player p1 = game.getPlayer1();
        Racket p1R = p1.getRacket();
        Player p2 = game.getPlayer2();
        Racket p2R = p2.getRacket();
        CollisionController colController = game.getCollisionController();

        while(true)
        {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!game.isPaused()) {
                game.getTimeline().play();      // plays the game's timeline in the case it was paused
                moveBall(ball, direction);
                //---------------------------------------
                //	Player Scoring
                //---------------------------------------
                //	Player 1 Scored
                //---------------------------------------
                if (colController.isRightSceneCol(ball, canvas)) {
                    colController.manageScore(p1, p2);
                    canvas.resetGameComponents(p1R, p2R, ball, game);
                    direction = (int) (Math.random() * 4) + 1;
                }
                //	Player 2 Scored
                //---------------------------------------
                if (colController.isLeftSceneCol(ball)) {
                    colController.manageScore(p2, p1);
                    canvas.resetGameComponents(p1R, p2R, ball, game);
                    direction = (int) (Math.random() * 4) + 1;
                }
                //---------------------------------------
                //	COLLISION HANDLING
                //---------------------------------------
                //	Player 1 racket collision
                //---------------------------------------
                if (colController.isRacket1Col(ball, p1R)) {
                    direction = (int) Math.round(Math.random() + 1);
                    System.out.println(p1.getName() + " hits!");
                }
                //	Player 2 racket collision
                //---------------------------------------
                if (colController.isRacket2Col(ball, p2R)) {
                    direction = (int) Math.round(Math.random() + 3);
                    System.out.println(p2.getName() + " hits!");
                }
                //	Top of game-area collision
                //---------------------------------------
                if (colController.isTopSceneCol(ball)) {
                    if (direction == 4) {
                        direction = 3;
                    } else if (direction == 1) {
                        direction = 2;
                    }
                }
                //	Bottom of game-area collision
                //---------------------------------------
                if (colController.isBottomSceneCol(ball, canvas)) {
                    if (direction == 2) {
                        direction = 1;
                    } else if (direction == 3) {
                        direction = 4;
                    }
                }
                canvas.drawGame(game);
            } else {
                game.getTimeline().stop();
                // drawn multiple times to account for resizing
                canvas.drawText("Paused", game.getxDimension()/2, game.getyDimension()/2);
            }
        }
    }
    //---------------------------------------
    //	EXTRA/HELPER METHODS
    //---------------------------------------
    /**
     * Moves the Ball class in a diagonal direction, based on the Ball's current position and the Ball's speed.<br>
     * @param b The Ball class to be moved.
     * @param direction The direction to move the ball. Each number correlates to a diagonal direction.<br>
     *                  (1 - top-right, 2 - bottom-right, 3 - bottom-left, 4 - top-left)
     */
    private void moveBall(Ball b, int direction){
        double x, y, speed;
        x = b.getxDimension();
        y = b.getyDimension();
        speed = b.getSpeed();

        switch (direction){
            // top-right direction
            case 1:
                b.setyDimension(y - speed);
                b.setxDimension(x + speed);
                break;
            // bottom-right direction
            case 2:
                b.setyDimension(y + speed);
                b.setxDimension(x + speed);
                break;
            // bottom-left direction
            case 3:
                b.setyDimension(y + speed);
                b.setxDimension(x - speed);
                break;
            // top-left direction
            case 4:
                b.setyDimension(y - speed);
                b.setxDimension(x - speed);
                break;
        }
    }
    /**
     * Pauses the Game class and sleeps the thread of this.
     * @param delay How long to delay the thread + game (in milliseconds).
     */
    public void delay(int delay){
        try {
            game.setPaused(true);
            // sleeps the thread for 2 seconds to display the text
            Thread.sleep(delay);
            game.setPaused(false);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
