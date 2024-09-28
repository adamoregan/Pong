package PONG.model;
//--------------------------------------------------
//	INTERFACE Resizable
//--------------------------------------------------
/**
 * This interface models a resizable Class.
 */
public interface Resizable {
    /**
     * Resizes the component's x-related attributes (x-position, width)
     * according to the size of the scene.
     * @param factor The factor to multiply the position and width by.
     */
    void resizeX(double factor);

    /**
     * Resizes the component's y-related attributes (y-position, height)
     * according to the size of the scene.
     * @param factor The factor to multiply the position and height by.
     */
    void resizeY(double factor);
}
