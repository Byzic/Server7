package server.utility;

import common.User;
import common.data.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Hashtable;

public class DatabaseCollectionManager {
    private final String SELECT_ALL_FLAT = "SELECT * FROM " + DatabaseManager.FLAT_TABLE;
    private final String SELECT_ALL_HOUSE = "SELECT * FROM " + DatabaseManager.HOUSE_TABLE;
    private final String SELECT_HOUSE_BY_ID = SELECT_ALL_HOUSE + " WHERE " + DatabaseManager.HOUSE_TABLE_ID_COLUMN + " =?";


    private DatabaseManager databaseManager;
    private DatabaseUserManager databaseUserManager;
    public DatabaseCollectionManager(DatabaseManager dm,DatabaseUserManager dum){
        this.databaseManager=dm;
        this.databaseUserManager=dum;
    }
    private Flat returnFlat(ResultSet resultSet, int id) throws SQLException{
        String name = resultSet.getString(DatabaseManager.FLAT_TABLE_NAME_COLUMN);
        Coordinates coordinates = getCoordinates(resultSet.getString(DatabaseManager.FLAT_TABLE_COORDINATES_COLUMN));
        LocalDateTime creationDate = resultSet.getTimestamp(DatabaseManager.FLAT_TABLE_CREATION_DATE_COLUMN).toLocalDateTime();
        Float area=resultSet.getFloat(DatabaseManager.FLAT_TABLE_AREA_COLUMN);
        Integer numberOfRooms=resultSet.getInt(DatabaseManager.FLAT_TABLE_NUMBER_OF_ROOMS_COLUMN);
        Furnish furnish=Furnish.valueOf(DatabaseManager.FLAT_TABLE_FURNISH_COLUMN);
        common.data.View view= View.valueOf(DatabaseManager.FLAT_TABLE_VIEW_COLUMN);
        Transport transport=Transport.valueOf(DatabaseManager.FLAT_TABLE_TRANSPORT_COLUMN);
        House house =getHouseById(resultSet.getInt(DatabaseManager.FLAT_TABLE_HOUSE_ID_COLUMN));
        //Integer houseId=resultSet.getInt(DatabaseManager.FLAT_TABLE_HOUSE_ID_COLUMN);
        User owner = databaseUserManager.getUserById(resultSet.getInt(DatabaseManager.FLAT_TABLE_USER_ID_COLUMN));
        return new Flat(id,name,coordinates,creationDate,area,numberOfRooms,furnish,view,transport,house,owner);

        

    }
    private House getHouseById(int id)throws SQLException{
        House house;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.getPreparedStatement(SELECT_HOUSE_BY_ID, false);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                house = new House(
                        resultSet.getString(DatabaseManager.HOUSE_TABLE_NAME_COLUMN),
                        resultSet.getLong(DatabaseManager.HOUSE_TABLE_YEAR_COLUMN),
                        resultSet.getLong(DatabaseManager.HOUSE_TABLE_NUMBER_OF_FLOORS_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_CHAPTER_BY_ID!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return house;
    }


    private Coordinates getCoordinates(String str) throws SQLException {
        String[] xy={"",""};
        xy=str.split(" ",2);
        Coordinates coordinates=new Coordinates( Float.parseFloat(xy[0]), Float.parseFloat(xy[1]));
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
