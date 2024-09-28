package PONG.model;
//--------------------------------------------------
//	IMPORTS
//--------------------------------------------------
import javafx.scene.paint.Color;
//--------------------------------------------------
//	CLASS Racket
//--------------------------------------------------
/**
 * This class models a racket in the Game class.
 */
public class Racket extends GameObject implements Resizable {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private double height;
    private double width;

    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * The constructor creates an Object of the Class Racket.
     * @param xDimension The x dimension of the racket.
     * @param yDimension The y dimension of the racket.
     * @param height The height of the racket.
     * @param width  The width of the racket.
     * @param color  The color of the racket.
     */
    public Racket(double xDimension, double yDimension, double height, double width, int speed, Color color) {
        super(xDimension, yDimension, speed, color);
        this.height = height;
        this.width = width;
    }

    //---------------------------------------
    //	GET METHODS
    //---------------------------------------

    /**
     * This function gets the height of the racket.
     * @return The height of the racket.
     */
    public double getHeight() {
        return height;
    }

    /**
     * This function gets the width of the racket.
     * @return The width of the racket.
     */
    public double getWidth() {
        return width;
    }

    //---------------------------------------
    //	SET METHODS
    //---------------------------------------

    /**
     * This function sets the height of the racket.
     * @param height The height to set the racket to.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * This function sets the width of the racket.
     * @param width The width to set the racket to.
     */
    public void setWidth(double width) { this.width = width; }

    //---------------------------------------
    //	EXTRA METHODS
    //---------------------------------------
    /**
     * Changes the x position, and the width of the racket depending on the width of the game.
     * @param factor The factor to multiply the position and width by.
     */
    public void resizeX(double factor) {
        xDimension = xDimension * factor;
        width = width * factor;
    }
    /**
     * Changes the y position, and the height of the racket depending on the height of the game.
     * @param factor The factor to multiply the position and height by.
     */
    public void resizeY(double factor) {
        yDimension = yDimension * factor;
        height = height * factor;
    }

    /**
     * Sets the y position of the Racket.
     * @param newYPos The y coordinate to set the object to.
     */
    @Override
    public void setyDimension(double newYPos){
        if (newYPos >= 0){
            this.yDimension = newYPos;
        } else {
            this.yDimension = 0;
        }
    }

    //	MOVEMENT METHODS
    //---------------------------------------
    /**
     * Moves the Racket up by the speed of the Racket.
     */
    public void moveUp(){
        setyDimension(getyDimension() - speed);
    }

    /**
     * Moves the Racket down by the speed of the Racket.
     * @param sceneHeight The height of the scene.
     */
    public void moveDown(double sceneHeight){
        if (yDimension + height + speed < sceneHeight){
            yDimension += speed;
        } else {
            yDimension = sceneHeight - height;
        }
    }
}
