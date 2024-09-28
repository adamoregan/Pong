package PONG.controller;
//---------------------------------------
//	IMPORTS
//---------------------------------------
import PONG.io.FileManager;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.*;
import PONG.model.Game;
import PONG.model.Racket;
import java.io.*;
import java.util.Optional;
//--------------------------------------------------
//	CLASS MenuListener
//--------------------------------------------------
/**
 * This class models a MenuListener which handles when menu items in the GameMenu is placed.
 */
public class MenuListener {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private final Game game;
    private final String filename = "GameData.ser";
    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Creates an instance of the MenuListener class.
     * @param game The Game that the MenuListener is part of.
     */
    public MenuListener(Game game){
        this.game = game;
    }
    //---------------------------------------
    //	EXTRA METHODS
    //---------------------------------------
    /**
     * Shows a TextInputDialog box to allow the user to set the name of the Game.
     */
    public void setGameName() {
        TextInputDialog dialog = new TextInputDialog(game.getName());
        setDialogContent(dialog, "Change Game Name", "Change the name of the game", "Enter the name");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(string -> {
            String input = result.get();
            System.out.println("- Name entered: " + input);
            game.setName(input);
        });
    }
    /**
     * Shows an alert window which prompts the user to exit the application.
     */
    public void setExit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && (option.get() == ButtonType.OK)) {
            Platform.exit();
        }
    }
    /**
     * Pauses/unpauses the game, based on if the menu item is checked.
     * @param pauseButton The button that is either selected or not.
     */
    public void setPaused(CheckMenuItem pauseButton) {
        // if the pause button is selected
        // we pause the game -> which pauses the ball manager and keyboardlistener
        game.setPaused(pauseButton.isSelected());
    }

    /**
     * Sets the title, header text, and content text of a TextInputDialog box.
     * @param dialog The TextInputDialog class whose content is set.
     * @param title The string to set the title as.
     * @param header The string to set the header text as.
     * @param content The string to set the content of the dialog to.
     */
    private void setDialogContent(TextInputDialog dialog, String title, String header, String content){
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
    }

    /**
     * Shows a TextInputDialog box to allow the user to set the max score of a Game class (minimum = 1).
     */
    public void setMaxScore(){
        TextInputDialog dialog = new TextInputDialog();
        setDialogContent(dialog, "Change Max Score", "Change the max score (min. 1)", "Enter a whole number");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(string -> {
            try {
                int input = Integer.parseInt(result.get());
                // the minimum max score is 1
                if (input > 0){
                    game.setMaxScore(Integer.parseInt(result.get()));
                }
            } catch (NumberFormatException e){
               System.out.println("Invalid entry! The max score has not been changed.");
            }
        });
        System.out.println(game.getMaxScore());
    }

    /**
     * Shows a TextInputDialog box to allow the user to set the name of Player 1.
     */
    public void setPlayer1Name(){
        TextInputDialog dialog = new TextInputDialog(game.getPlayer1().getName());
        setDialogContent(dialog, "Change Player1 Name", "Change the name of player1.",
                "Enter a name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(string -> {
            try {
                String input = result.get();
                System.out.println("- Name entered: " + input);
                game.getPlayer1().setName(input);
                game.getGc().drawGame(game);
            } catch (Exception e){
                System.out.println("Error! Could not get player name.");
            }
        });
    }

    /**
     * Shows a TextInputDialog box to allow the user to set the name of Player 1.
     */
    public void setPlayer2Name(){
        TextInputDialog dialog = new TextInputDialog(game.getPlayer2().getName());
        setDialogContent(dialog, "Change Player2 Name", "Change the name of player2.",
                "Enter a name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(string -> {
            try {
                String input = result.get();
                System.out.println("- Name entered: " + input);
                game.getPlayer2().setName(input);
                game.getGc().drawGame(game);
            } catch (Exception e){
                System.out.println("Error! Could not get player name.");
            }
        });
    }
    /**
     * Shows a TextInputDialog box to allow the user to set the height of both Rackets (minimum = 100).
     */
    public void setRacketHeight(){
        TextInputDialog dialog = new TextInputDialog(String.valueOf(game.getPlayer1().getRacket().getHeight()));
        setDialogContent(dialog, "Change Racket Height", "Change the height of the Racket (min.1)",
                "Enter a whole number:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(string -> {
            try {
                double input = Double.parseDouble(result.get());
                System.out.println("- Input: " + input);
                // the minimum max height is 100
                if (input >= 100 && input <= game.getyDimension()){
                    game.getPlayer1().getRacket().setHeight(input);
                    game.getPlayer2().getRacket().setHeight(input);
                    game.getGc().drawGame(game);
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid entry! The height of the racket has not been changed.");
            }
        });
    }
    /**
     * Shows a TextInputDialog box to allow the user to set the name of Player 1.
     */
    public void setRacketWidth(){
        TextInputDialog dialog = new TextInputDialog(String.valueOf(game.getPlayer1().getRacket().getHeight()));
        setDialogContent(dialog, "Change Racket Width", "Change the width of the Racket (min.100)",
                "Enter a whole number:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(string -> {
            try {
                double input = Double.parseDouble(result.get());
                System.out.println("- Input: " + input);
                // the maximum width is less than 3 times the game width
                if (input >= 100 && input <= game.getxDimension() / 3){
                    Racket racket = game.getPlayer1().getRacket();
                    racket.setWidth(input);
                    racket = game.getPlayer2().getRacket();
                    racket.setxDimension(game.getGc().getWidth() - input);
                    racket.setWidth(input);
                    game.getGc().drawGame(game);
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid entry! The width of the racket has not been changed.");
            }
        });
    }
    /**
     * Shows a TextInputDialog box to allow the user to set the speed of the Ball (minimum = 1).
     */
    public void setBallSpeed() {
        TextInputDialog dialog = new TextInputDialog(String.valueOf(game.getBall().getSpeed()));
        setDialogContent(dialog, "Change Ball Speed", "Change the speed of the Ball (min.1)",
                "Enter a whole number:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(string -> {
            try {
                int input = Integer.parseInt(result.get());
                System.out.println("- Input: " + input);
                // the speed must be greater than 0
                if (input > 0) {
                    game.getBall().setSpeed(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry!");
            }
        });
    }
    /**
     * Shows a TextInputDialog box to allow the user to set how often the speed of the Ball should increase
     * every x seconds.
     */
    public void setBallSpeedConfig() {
        TextInputDialog dialog = new TextInputDialog();
        setDialogContent(dialog, "Ball Speed Increase Frequency ",
                "Change how often the ball show increase speed (in seconds).",
                "Enter a whole number:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(string -> {
            try {
                int input = Integer.parseInt(result.get());
                // gets the game's timeline
                Timeline timeline = game.getTimeline();
                System.out.println("- Input: " + input);
                // the amount of time must be greater than 0
                if (input >= 1) {
                    timeline.stop();
                    // removes the previous value
                    timeline.getKeyFrames().remove(game.getBall().getSpeedIncreaseFrequency());
                    // sets a new value
                    game.getBall().setSpeedIncreaseFrequency(input);
                    // adds the NEW keyframe to the timeline
                    timeline.getKeyFrames().add(game.getBall().getSpeedIncreaseFrequency());
                    timeline.play();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry!");
            }
        });
    }
    /**
     * Shows a TextInputDialog box to allow the user to set how many bounces it takes to increase the Ball's
     * speed.
     */
    public void setBallBounceConfig() {
        TextInputDialog dialog = new TextInputDialog(String.valueOf(game.getCollisionController().getNumColTracker()));
        setDialogContent(dialog, "Ball Bounce Speed Increase Frequency",
                "Change how many bounces it takes to increase the speed of the ball.",
                "Enter a whole number:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(string -> {
            try {
                int input = Integer.parseInt(result.get());
                System.out.println("- Input: " + input);
                // the amount of bounces must be greater than 0
                if (input > 0) {
                    game.getCollisionController().setNumColTracker(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry!");
            }
        });
    }
    /**
     * Shows an Alert to allow the user to restart the game.<br>
     * Setting the scores to 0, setting the speed to 1, restarting the Game timeline,
     * and resetting the positions of the Game components.
     */
    public void setRestart() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Restart");
        alert.setHeaderText("Confirm Restart");
        alert.setContentText("Are you sure you want to restart the game?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && (option.get() == ButtonType.OK)) {
            game.getPlayer1().setScore(0);
            game.getPlayer2().setScore(0);
            game.getBall().setSpeed(1);
            game.getTimeline().playFromStart();
            game.getGc().resetGameComponents(game.getPlayer1().getRacket(), game.getPlayer2().getRacket(),
                    game.getBall(), game);
        }
    }

    /**
     * Shows an Alert to the user to allow them to deserialize the saved Game from the .ser file.
     */
    public void deserialize() {
        game.setPaused(true);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Load Game from File");
        alert.setHeaderText("Confirm loading the the saved game from the " + filename + " file.");
        alert.setContentText("Are you sure you want to load the game from the file?\n" +
                "(The current game will be lost if it is not saved)");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && (option.get() == ButtonType.OK)) {
            try {
                FileManager.getInstance(game).deserialize();
                // RESET COMPONENTS
                game.getGc().resetGameComponents(game.getPlayer1().getRacket(), game.getPlayer2().getRacket(),
                        game.getBall(), game);
            } catch (IOException e) {
                System.out.println("Error! There was an error loading the data from the file.");
            } catch (ClassNotFoundException e) {
                System.out.println("Error! Game class could not be found");
            }
        }
        game.setPaused(false);
    }
    /**
     * Shows an Alert to the user to allow them to serialize the Game to the .ser file.
     */
    public void serialize() {
        game.setPaused(true);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Game to File");
        alert.setHeaderText("Confirm saving the current game to the "+filename+" file.");
        alert.setContentText("Are you sure you want to save the game to the file?\n" +
                "(The previous save will be overwritten)");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && (option.get() == ButtonType.OK)) {
            try {
                FileManager.getInstance(game).serialize();
                // Resetting the game components
                game.getGc().resetGameComponents(game.getPlayer1().getRacket(), game.getPlayer2().getRacket(),
                        game.getBall(), game);
                showSavePopUp();
            } catch (IOException e) {
                System.out.println("Error! There was an error saving the data to the file.");
            }
        }
        game.setPaused(false);
    }
    /**
     * Shows a TextInputDialog box to allow the user to load a previously saved Game from the database.
     */
    public void setDatabaseLoad() {
        game.setPaused(true);
        TextInputDialog dialog = new TextInputDialog();
        setDialogContent(dialog, "Load Game from Database",
                "Load a previously saved game from the database.",
                "Enter the name of the save Game (DEFAULT NAME: My Game):");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(string -> {
            try {
                String input = result.get();
                System.out.println("- Input: " + input);
                // Getting the game
                Game loadedGame = game.getDatabaseManager().getGame(input);
                if (loadedGame != null) {
                    // Loading the game
                    game.getGameController().setGame(loadedGame);
                    // Resetting the game components
                    game.getGc().resetGameComponents(game.getPlayer1().getRacket(), game.getPlayer2().getRacket(),
                            game.getBall(), game);
                }
                else {
                    System.out.println("Error! Game could not be found!");
                }
            } catch (Exception e) {
                System.out.println("Error! Game could not be found!");
            }
        });
        game.setPaused(false);
    }
    /**
     *  Shows an Alert to allow the user to save the current Game to the database.
     */
    public void setDatabaseSave() {
        game.setPaused(true);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Game to Database");
        alert.setHeaderText("Confirm saving the current game to the database.");
        alert.setContentText("Are you sure you want to save the game to the database?\n" +
                "(Saved games with the same name will be overwritten DEFAULT NAME: My Game)");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && (option.get() == ButtonType.OK)) {
            try {
                game.getDatabaseManager().saveGame(game);
                showSavePopUp();
            } catch (Exception e) {
                System.out.println("Error! Game could not be saved.");
            }
        }
        game.setPaused(false);
    }
    /**
     * Shows an Alert to the user that their game has been saved successfully.
     */
    private void showSavePopUp() {
        Alert popUp = new Alert(Alert.AlertType.INFORMATION);
        popUp.setHeaderText("Save Successful");
        popUp.setContentText("Your game has been successfully saved.");
        popUp.showAndWait();
    }
}
