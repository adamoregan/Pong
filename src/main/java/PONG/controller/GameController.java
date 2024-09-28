package PONG.controller;
//---------------------------------------
//	IMPORTS
//---------------------------------------
import PONG.model.Game;
//--------------------------------------------------
//	CLASS GameController
//--------------------------------------------------
/**
 * This class models a GameController for the Game.
 */
public class GameController {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private final Game game;
    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Creates an instance of the GameController class.
     * @param game The Game which the GameController controls.
     */
    public GameController(Game game){ this.game = game;}
    //---------------------------------------
    //	GET METHODS
    //---------------------------------------
    /**
     * Gets the Game which the class controls.
     * @return The Game which the GameController controls.
     */
    public Game getGame(){ return game; }
    //---------------------------------------
    //	SET METHODS
    //---------------------------------------
    /**
     * Changes the settings of the existing Game to a new Game.
     * @param newGame The Game that holds the details to change the existing Game to.
     */
    public void setGame(Game newGame) {
        Game oldGame = getGame();
        oldGame.setName(newGame.getName());
        oldGame.setPlayer1(newGame.getPlayer1());
        oldGame.setPlayer2(newGame.getPlayer2());
        oldGame.setMaxScore(newGame.getMaxScore());
    }
}
