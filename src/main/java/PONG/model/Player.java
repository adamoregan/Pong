package PONG.model;
//--------------------------------------------------
//	CLASS Player
//--------------------------------------------------

import java.io.Serializable;

/**
 * This class models a player in the Pong game.
 */
public class Player implements Serializable {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private String name;
    private int score;
    private transient Racket racket;
    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Creates an instance of the Player class.
     */
    public Player(){}
    /**
     * Creates an instance of the Player class.
     * @param name The name of the Player.
     * @param score The score of the Player.
     * @param racket The Player's Racket.
     */
    public Player(String name, int score, Racket racket){
        this.score = score;
        this.name = name;
        this.racket = racket;
    }

    //---------------------------------------
    //	GET METHODS
    //---------------------------------------

    /**
     * Gets the score of the Player.
     * @return The score of the Player.
     */
    public int getScore() { return score; }

    /**
     * Gets the name of the Player.
     * @return The name of the Player.
     */
    public String getName() { return name; }

    /**
     * Gets the Player's Racket.
     * @return The Player's Racket.
     */
    public Racket getRacket() { return racket; }

    //---------------------------------------
    //	SET METHODS
    //---------------------------------------
    /**
     * Sets the score of the Player.
     * @param score The number to set the score to.
     */
    public void setScore(int score) { this.score = score; }

    /**
     * Sets the name of the Player.
     * @param name The name to set to.
     */
    public void setName(String name) { this.name = name; }
    //---------------------------------------
    //	EXTRA METHODS
    //---------------------------------------
    /**
     * Increments the Player's score by 1.
     */
    public void score() { this.score++; }
}
