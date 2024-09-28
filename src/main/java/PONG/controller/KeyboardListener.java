package PONG.controller;
//---------------------------------------
//	IMPORTS
//---------------------------------------
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import PONG.model.Game;
import PONG.model.Racket;
import PONG.view.GameCanvas;
//--------------------------------------------------
//	CLASS KeyboardListener
//--------------------------------------------------

/**
 * This class models a KeyboardListener which listens to user keyboard inputs
 * to move each Player's Racket up and down depending on what key was pressed.<br>
 * It also handles the pausing/unpausing of the game if a key was pressed.
 */
public class KeyboardListener implements EventHandler<KeyEvent> {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private final Game game;
    private final GameCanvas canvas;
    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Creates an instance of the KeyboardListener class.
     * @param game The Game that the KeyboardListener is a part of.
     * @param canvas The GameCanvas where the Rackets are drawn after movement.
     */
    public KeyboardListener(Game game, GameCanvas canvas) {
        this.game = game;
        this.canvas = canvas;
    }

    //---------------------------------------
    //	HANDLE METHOD
    //---------------------------------------
    /**
     * Handles user input key presses.
     * @param keyEvent The KeyEvent representing the user's input.
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        // INIT VARS --------------
        KeyCode key = keyEvent.getCode();
        System.out.println("Key pressed: " + key);
        // ------------------------
        // HANDLING PAUSE INPUT ---
        handlePauseInput(key);
        // ------------------------
        // HANDLING RACKET INPUT --
        // If the keyboard input is not paused then keyboard input will be detected
        if (!game.isPaused()) {
            handleRacketInput(key);
        }
        // ------------------------
    }

    /**
     * Changes the pause state of the game if the SPACE key was pressed.
     * @param key The key that was pressed
     */
    private void handlePauseInput(KeyCode key) {
        if (KeyCode.SPACE == key) {
            game.setPaused(!game.isPaused());
        }
    }
    /**
     * Moves each Player's Rackets according to what key was pressed.
     * @param key The key that was pressed.
     */
    private void handleRacketInput(KeyCode key) {
        // init player rackets
        Racket p1Racket = game.getPlayer1().getRacket();
        Racket p2Racket = game.getPlayer2().getRacket();
        //	Player 1 keyboard detection/movement
        //---------------------------------------
        if (KeyCode.W == key) {
            p1Racket.moveUp();
        }
        if (KeyCode.S == key) {
            p1Racket.moveDown(canvas.getScene().getHeight());
        }
        //	Player 2 keyboard detection/movement
        //---------------------------------------
        if (KeyCode.UP == key) {
            p2Racket.moveUp();
        }
        if (KeyCode.DOWN == key) {
            p2Racket.moveDown(canvas.getScene().getHeight());
        }
        canvas.drawGame(game);
    }
}