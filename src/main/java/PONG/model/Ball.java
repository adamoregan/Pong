package PONG.model;
//---------------------------------------
//	IMPORTS
//---------------------------------------
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.util.Duration;

//--------------------------------------------------
//	CLASS Ball
//--------------------------------------------------

/**
 * This class models a Ball class which has a speed, location (x and y coordinates), color, and a KeyFrame which increases the Ball's speed after
 * a certain amount of time.
 */
public class Ball extends GameObject implements Resizable {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private double radius;
    private KeyFrame speedIncreaseFrequency;
    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Creates an instance of the Ball class.
     * @param xDimension The x coordinate of the Ball.
     * @param yDimension The y coordinate of the Ball.
     * @param radius The radius of the Ball.
     * @param speed The speed of the Ball
     * @param sIncreaseFrequency The KeyFrame of the Ball which handles how many seconds it takes for the speed of the
     *                           ball to increase.
     * @param color The (Color) color of the Ball.
     */
    public Ball(double xDimension, double yDimension, double radius, int speed, int sIncreaseFrequency, Color color){
        super(xDimension, yDimension, speed, color);
        this.radius = radius;
        this.speed = speed;
        speedIncreaseFrequency = new KeyFrame(Duration.seconds(sIncreaseFrequency), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // speed++
                incrementSpeed();
            }
        });
    }
    //---------------------------------------
    //	GET METHODS
    //---------------------------------------
    /**
     * Gets the radius of the Ball.
     * @return The radius of the Ball.
     */
    public double getRadius() { return radius; }
    /**
     * Gets the speed of the Ball.
     * @return The speed of the Ball.
     */
    public int getSpeed() { return speed; }
    /**
     * Gets the KeyFrame of the Ball.
     * @return The KeyFrame of the Ball.
     */
    public KeyFrame getSpeedIncreaseFrequency() { return speedIncreaseFrequency; }
    //---------------------------------------
    //	SET METHODS
    //---------------------------------------
    /**
     * Sets the radius of the Ball.
     * @param radius The radius of the Ball.
     */
    public void setRadius(double radius) { this.radius = radius; }
    /**
     * Sets the speed of the Ball.
     * @param speed The speed of the Ball.
     */
    public void setSpeed(int speed) { this.speed = speed; }

    /**
     * Sets how often the speed of the ball should increase by setting a new KeyFrame.
     * @param sIncreaseFrequency How long (in milliseconds) it takes to increase the speed of the Ball.
     */
    public void setSpeedIncreaseFrequency(int sIncreaseFrequency) {
        speedIncreaseFrequency = new KeyFrame(Duration.seconds(sIncreaseFrequency), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // speed++
                incrementSpeed();
                System.out.println("Set Ball speed: " +getSpeed());
            }
        });
    }

    //---------------------------------------
    //	EXTRA METHODS
    //---------------------------------------

    /**
     * Changes the x position, and the radius depending on the width of the game.
     * @param factor The factor to multiply the position and width by.
     */
    public void resizeX(double factor){
        xDimension = xDimension*factor;
        setRadius(radius*factor);
    }

    /**
     * Changes the y position, and the radius depending on the height of the game.
     * @param factor The factor to multiply the position and height by.
     */
    public void resizeY(double factor){
        yDimension = yDimension*factor;
        setRadius(radius*factor);
    }

    /**
     * Increments the speed by 1.
     */
    public void incrementSpeed(){ this.setSpeed(this.getSpeed()+1);}

}
