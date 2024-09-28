package PONG.model;

import javafx.scene.paint.Color;
//--------------------------------------------------
//	CLASS GameObject
//--------------------------------------------------
/**
 * This class models a Game Object.
 */
public class GameObject {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    protected Color color;
    protected double xDimension;
    protected double yDimension;
    protected int speed;

    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Creates an instance of a GameObject.
     * @param x The x coordinate of the GameObject.
     * @param y The y coordinate of the GameObject.
     * @param speed The speed of the GameObject.
     * @param color The color of the GameObject.
     */
    public GameObject(double x, double y, int speed, Color color){
        xDimension = x;
        yDimension = y;
        this.speed = speed;
        this.color = color;
    }

    //---------------------------------------
    //	GET METHODS
    //---------------------------------------

    /**
     * This function gets the x dimension of the object.
     * @return The xDimension of the object.
     */
    public double getxDimension() {
        return xDimension;
    }

    /**
     * This function gets the y dimension of the object.
     * @return The yDimension of the object.
     */
    public double getyDimension() {
        return yDimension;
    }

    /**
     * This function gets the speed of the object.
     * @return The speed of the object.
     */
    public int getSpeed() { return speed; }
    /**
     * This function gets the color of the racket.
     * @return The color of the racket.
     */
    public Color getColor() {
        return color;
    }

    //---------------------------------------
    //	SET METHODS
    //---------------------------------------
    /**
     * Sets the x position.
     * @param x The x coordinate to set the object to.
     */
    public void setxDimension(double x) {
        xDimension = x;
    }
    /**
     * Sets the y position.
     * @param y The y coordinate to set the object to.
     */
    public void setyDimension(double y) {
        yDimension = y;
    }

    /**
     * Sets the speed of the object.
     * @param speed The speed to set the
     */
    public void setSpeed(int speed) { this.speed = speed; }

    /**
     * This function sets the color of the object.
     * @param color The color to set the object to.
     */
    public void setColor(Color color) {
        this.color = color;
    }

}
