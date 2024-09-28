package PONG.io;
//---------------------------------------
//	IMPORTS
//---------------------------------------
import PONG.model.Game;
import java.io.*;
//--------------------------------------------------
//	CLASS FileManager
//--------------------------------------------------
/**
 * This class models a file manager for the game.<br>
 * It can serialize and deserialize game-data to a .ser file.
 */
public class FileManager {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private static FileManager instance;
    private final Game game;
    private final String FILENAME = "GameData.ser";
    //---------------------------------------
    //	Constructor
    //---------------------------------------
    /**
     * Creates an instance of the FileManager class.
     * @param game The Game that's data is to be serialized/deserialized.
     */
    private FileManager(Game game) {
        this.game = game;
    }
    //---------------------------------------
    //	GET METHODS
    //---------------------------------------
    /**
     * Gets the singleton instance of the FileManager class.<br>
     * If there is no instance, an instance is created.
     * @param game The Game that's data is to be serialized/deserialized.
     * @return The singleton instance of the FileManager class.
     */
    public static FileManager getInstance(Game game) {
        if (instance == null) {
            instance = new FileManager(game);
        }
        return instance;
    }
    //---------------------------------------
    //	FILE METHODS
    //---------------------------------------
    /**
     * Deserializes the Game from the .ser file.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws ClassNotFoundException If the Game class can not be found.
     */
    public void deserialize() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(FILENAME);
        ObjectInputStream in = new ObjectInputStream(file);
        game.getGameController().setGame((Game)in.readObject());
        in.close();
        file.close();
    }
    /**
     * Serializes the Game to the .ser file.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void serialize() throws IOException {
        FileOutputStream file = new FileOutputStream(FILENAME);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(game);
        out.close();
        file.close();
    }
}
