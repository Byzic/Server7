package server.utility;

import common.data.Coordinates;
import common.data.Flat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Hashtable;

public class DatabaseCollectionManager {
    private final String SELECT_ALL_FLAT = "SELECT * FROM " + DatabaseManager.FLAT_TABLE;
    private final String SELECT_ALL_COORDINATES = "SELECT * FROM " + DatabaseManager.COORDINATES_TABLE;
    private final String SELECT_COORDINATES_BY_ID = SELECT_ALL_COORDINATES + " WHERE " + DatabaseManager.COORDINATES_TABLE_ID_COLUMN + " =?";


    private DatabaseManager databaseManager;
    private DatabaseUserManager databaseUserManager;
    public DatabaseCollectionManager(DatabaseManager dm,DatabaseUserManager dum){
        this.databaseManager=dm;
        this.databaseUserManager=dum;
    }
    private Flat returnFlat(ResultSet resultSet, int id) throws SQLException{
        String name = resultSet.getString(DatabaseManager.FLAT_TABLE_NAME_COLUMN);
        Coordinates coordinates = getCoordinatesByID(resultSet.getInt(DatabaseManager.FLAT_TABLE_COORDINATES_ID_COLUMN));
        LocalDateTime creationDate = resultSet.getTimestamp(DatabaseManager.FLAT_TABLE_CREATION_DATE_COLUMN).toLocalDateTime();
        

    }

    private Coordinates getCoordinatesByID(int id) throws SQLException {
        Coordinates coordinates;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.getPreparedStatement(SELECT_COORDINATES_BY_ID, false);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                coordinates = new Coordinates(
                        resultSet.getFloat(DatabaseManager.COORDINATES_TABLE_X_COLUMN),
                        resultSet.getFloat(DatabaseManager.COORDINATES_TABLE_Y_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_COORDINATES_BY_ID!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return coordinates;
    }


    public Hashtable<Integer, Flat> getCollection() {
        Hashtable<Integer, Flat> hashtable = new Hashtable<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.getPreparedStatement(SELECT_ALL_FLAT, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(DatabaseManager.FLAT_TABLE_ID_COLUMN);
                int key = resultSet.getInt(DatabaseManager.FLAT_TABLE_KEY_COLUMN);
                hashtable.put(key, returnFlat(resultSet, id));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return hashtable;
    }
}
