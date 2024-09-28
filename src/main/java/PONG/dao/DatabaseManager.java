package PONG.dao;
//---------------------------------------
//	IMPORTS
//---------------------------------------
import PONG.controller.GameBuilder;
import PONG.model.Game;
import java.sql.*;
//--------------------------------------------------
//	CLASS DatabaseManager
//--------------------------------------------------
/**
 * This class models a database manager in the PONG game.<br>
 * It loads saved games, saves games, and updates games in the SQL database.
 */
public class DatabaseManager {
    //---------------------------------------
    //	GET METHODS
    //---------------------------------------
    /**
     * Accesses the database to load a saved Game.
     * @param name The name of the Game to load.
     * @return The saved Game in the database.
     */
    public Game getGame(String name) throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseConnector.getConnection();
        ResultSet rs = searchByName(connection, name);
        if(rs.next())
        {
            System.out.println(rs.getString("player1Name"));
            System.out.println(rs.getString("player2Name"));
            GameBuilder builder = new GameBuilder().
                    withName(rs.getString("name")).
                    withPlayer1Name(rs.getString("player1Name")).
                    withPlayer2Name(rs.getString("player2Name")).
                    withPlayer1Score(rs.getInt("player1Score")).
                    withPlayer2Score(rs.getInt("player2Score")).
                    withMaxScore(rs.getInt("target"));
            return builder.build();
        }
        return null;
    }
    //---------------------------------------
    //	EXTRA METHODS
    //---------------------------------------
    /**
     * Saves the current Game to the database.
     * @param currentGame The current Game to be saved to the database.
     * @throws ClassNotFoundException If the driver class to connect to the database can not be found.
     * @throws SQLException If the SQL command is incorrect, or there is something wrong
     * with the connection to the database.
     */
    public void saveGame(Game currentGame) throws ClassNotFoundException, SQLException
    {
        // Getting the info of the current game
        String p1Name, p2Name, gameName;
        int p1Score, p2Score, maxScore;
        p1Name = currentGame.getPlayer1().getName();
        p2Name =  currentGame.getPlayer2().getName();
        p1Score = currentGame.getPlayer1().getScore();
        p2Score = currentGame.getPlayer2().getScore();
        gameName = currentGame.getName();
        maxScore = currentGame.getMaxScore();
        // ------------------------------------
        String sql;
        Connection connection = DatabaseConnector.getConnection();
        ResultSet rs = searchByName(connection, currentGame.getName());

        if (!rs.next()) {
            // If there is no previous save then we insert it into the database
            sql = String.format("INSERT INTO assignment.game (player1Name, player2Name, player1Score, " +
                    "player2Score, target, name) VALUES (\"%s\", \"%s\", %d, %d, %d, \"%s\")", p1Name, p2Name, p1Score, p2Score,
                    maxScore, gameName);
        } else {
            // If a save game with that name exists, then we update the data it holds
            sql = String.format("UPDATE assignment.game " +
                    "SET player1Name = \"%s\", player2Name = \"%s\", player1Score = %d, player2Score = %d, target = %d " +
                    "WHERE name = \"%s\"", p1Name, p2Name, p1Score, p2Score, maxScore, gameName);
        }

        System.out.println("SQL Statement: " + sql);
        // Connecting to the database and inserting/updating the game save
        try(connection) {
            PreparedStatement statement = connection.prepareStatement(sql);
            int rowsChanged = statement.executeUpdate();
            if (rowsChanged > 0) {
                System.out.println("Game saved to database successfully!");
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL query!");
        } finally {
            connection.close();
        }
        // ------------------------------------
    }

    /**
     * Searches through the SQL database to see if there exists a save Game with the entered name.
     * @param connection The connection to the database
     * @param name The name of the Game to search for.
     * @return The ResultSet of the query.
     * @throws SQLException If the SQL command is incorrect, or there is something wrong
     * with the connection to the database.
     */
    private ResultSet searchByName(Connection connection, String name) throws  SQLException {
        Statement statement = connection.createStatement();
        String sql = "select * from assignment.game WHERE name = \""+name+"\" ORDER BY id DESC LIMIT 1;";
        System.out.println(sql);
        return statement.executeQuery(sql);
    }
}
