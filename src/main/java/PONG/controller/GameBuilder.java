package PONG.controller;
//---------------------------------------
//	IMPORTS
//---------------------------------------
import PONG.model.Game;
import PONG.model.Player;
//--------------------------------------------------
//	CLASS GameBuilder
//--------------------------------------------------
/**
 * This class models a builder for creating instances of the Game class for the PONG game.
 */
public class GameBuilder {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private String player1Name;
    private String player2Name;
    private int player1Score;
    private int player2Score;
    private String name;
    private int maxScore;
    //---------------------------------------
    //	BUILDER METHODS
    //---------------------------------------
    /**
     * Creates a Game object with the desired attributes.
     * @return The created Game object.
     */
    public Game build() {
        Game game = new Game();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setName(player1Name);
        player2.setName(player2Name);
        player1.setScore(player1Score);
        player2.setScore(player2Score);
        game.setMaxScore(maxScore);
        game.setName(name);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        return game;
    }

    /**
     * Sets the name of the Game.
     * @param name The name of the Game.
     * @return The GameBuilder object to chain method calls.
     */
    public GameBuilder withName(String name) {
        this.name=name;
        return this;
    }
    /**
     * Sets the name of player 1 of the Game.
     * @param p1Name The name of player 1.
     * @return The GameBuilder object to chain method calls.
     */
    public GameBuilder withPlayer1Name(String p1Name) {
        this.player1Name=p1Name;
        return this;
    }
    /**
     * Sets the name of player 2 of the Game.
     * @param p2Name The name of player 2.
     * @return The GameBuilder object to chain method calls.
     */
    public GameBuilder withPlayer2Name(String p2Name) {
        this.player2Name=p2Name;
        return this;
    }
    /**
     * Sets the score of player 1 of the Game.
     * @param score The score of player 1.
     * @return The GameBuilder object to chain method calls.
     */
    public GameBuilder withPlayer1Score(int score) {
        this.player1Score=score;
        return this;
    }
    /**
     * Sets the score of player 2 of the Game.
     * @param score The score of player 2.
     * @return The GameBuilder object to chain method calls.
     */
    public GameBuilder withPlayer2Score(int score) {
        this.player2Score=score;
        return this;
    }
    /**
     * Sets the max score of the Game.
     * @param score The max score of the Game.
     * @return The GameBuilder object to chain method calls.
     */
    public GameBuilder withMaxScore(int score) {
        maxScore = score;
        return this;
    }
}
