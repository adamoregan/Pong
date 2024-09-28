package PONG.controller;
//--------------------------------------------------
//	IMPORTS
//--------------------------------------------------
import PONG.model.Ball;
import PONG.model.Racket;
import PONG.view.GameCanvas;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//--------------------------------------------------
//	CLASS CollisionControllerTest
//--------------------------------------------------
/**
 * This class models a test class for the CollisionController class.
 */
class CollisionControllerTest {
    /**
     * Tests the collision detection between Player 1's racket and the ball.
     */
    @Test
    void isRacket1Col() {
        Racket racket = new Racket(30,200,100,30,15, Color.WHITE);
        Ball ball = new Ball(200, 200, 10, 1, 10, Color.WHITE);

        assertFalse(ball.getxDimension() <= racket.getxDimension() + racket.getWidth() &&
                ball.getyDimension() + ball.getRadius() >= racket.getyDimension() &&
                ball.getyDimension() <= racket.getyDimension() + racket.getHeight());

        ball.setyDimension(250);
        ball.setxDimension(30);
        assertTrue(ball.getxDimension() <= racket.getxDimension() + racket.getWidth() &&
                ball.getyDimension() + ball.getRadius() >= racket.getyDimension() &&
                ball.getyDimension() <= racket.getyDimension() + racket.getHeight());

        ball.setyDimension(400);

        assertFalse(ball.getxDimension() <= racket.getxDimension() + racket.getWidth() &&
                ball.getyDimension() + ball.getRadius() >= racket.getyDimension() &&
                ball.getyDimension() <= racket.getyDimension() + racket.getHeight());

    }

    /**
     * Tests the collision detection between Player 2's racket and the ball.
     */
    @Test
    void isRacket2Col() {
        Racket racket = new Racket(340,200,100,30,15,Color.WHITE);
        Ball ball = new Ball(200, 200, 10, 1, 10, Color.WHITE);

        assertFalse(ball.getxDimension() + ball.getRadius() >= racket.getxDimension() &&
                ball.getyDimension() + ball.getRadius() >= racket.getyDimension() &&
                ball.getyDimension() <= racket.getyDimension() + racket.getHeight());

        ball.setxDimension(330);

        assertTrue(ball.getxDimension() + ball.getRadius() >= racket.getxDimension() &&
                ball.getyDimension() + ball.getRadius() >= racket.getyDimension() &&
                ball.getyDimension() <= racket.getyDimension() + racket.getHeight());

        ball.setyDimension(400);

        assertFalse(ball.getxDimension() + ball.getRadius() >= racket.getxDimension() &&
                ball.getyDimension() + ball.getRadius() >= racket.getyDimension() &&
                ball.getyDimension() <= racket.getyDimension() + racket.getHeight());

    }

    /**
     * Tests the collision detection between the ball and the top of the scene/canvas.
     */
    @Test
    public void isTopSceneCol() {
        Ball ball = new Ball(200, 200, 10, 1, 10, Color.WHITE);
        ball.setyDimension(0);
        assertTrue(ball.getyDimension() <= 0);
        ball.setyDimension(100);
        assertFalse(ball.getyDimension() <= 0);
    }

    /**
     * Tests the collision detection between the ball and the bottom of the scene/canvas.
     */
    @Test
    void isBottomSceneCol() {
        GameCanvas gameCanvas = new GameCanvas(400, 400, 12);
        Ball ball = new Ball(200, 200, 10, 1, 10, Color.WHITE);
        assertFalse(ball.getyDimension() + ball.getRadius() >= gameCanvas.getHeight() - 25);
        ball.setyDimension(390);
        assertTrue(ball.getyDimension() + ball.getRadius() >= gameCanvas.getHeight() - 25);
    }

    /**
     * Tests the collision detection between the ball and the left of the scene/canvas.
     */
    @Test
    void isLeftSceneCol() {
        Ball ball = new Ball(200, 200, 10, 1, 10, Color.WHITE);
        ball.setxDimension(0);
        assertTrue(ball.getxDimension() <= 0);
        ball.setxDimension(100);
        assertFalse(ball.getxDimension() <= 0);
    }
    /**
     * Tests the collision detection between the ball and the right of the canvas.
     */
    @Test
    void isRightSceneCol() {
        GameCanvas gameCanvas = new GameCanvas(400, 400, 12);
        Ball ball = new Ball(200, 200, 10, 1, 10, Color.WHITE);
        assertFalse(ball.getxDimension() + ball.getRadius() >= gameCanvas.getWidth());
        ball.setxDimension(400 - ball.getRadius());
        assertTrue(ball.getxDimension() + ball.getRadius() >= gameCanvas.getWidth());
    }
}