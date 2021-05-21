package server.utility;

import java.io.Console;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//Класс для составления sql запросов и для коннекта с бд
public class DatabaseManager {
    // Table names
    public static final String FLAT_TABLE = "flat";
    public static final String USER_TABLE = "users";
    public static final String HOUSE_TABLE = "house";
    // FLAT_TABLE column names
    public static final String FLAT_TABLE_ID_COLUMN = "id";
    public static final String FLAT_TABLE_KEY_COLUMN = "key";
    public static final String FLAT_TABLE_NAME_COLUMN = "name";
    public static final String FLAT_TABLE_COORDINATES_COLUMN = "coordinates";
    public static final String FLAT_TABLE_CREATION_DATE_COLUMN = "creation_date";
    public static final String FLAT_TABLE_AREA_COLUMN = "area";
    public static final String FLAT_TABLE_NUMBER_OF_ROOMS_COLUMN = "number_of_rooms";
    public static final String FLAT_TABLE_FURNISH_COLUMN = "furnish";
    public static final String FLAT_TABLE_VIEW_COLUMN = "view";
    public static final String FLAT_TABLE_TRANSPORT_COLUMN="transport";
    public static final String FLAT_TABLE_HOUSE_ID_COLUMN = "house_id";
    public static final String FLAT_TABLE_USER_ID_COLUMN = "user_id";
    // USER_TABLE column names
    public static final String USER_TABLE_ID_COLUMN = "id";
    public static final String USER_TABLE_USERNAME_COLUMN = "username";
    public static final String USER_TABLE_PASSWORD_COLUMN = "password";
    // HOUSE_TABLE column names
    public static final String HOUSE_TABLE_ID_COLUMN = "id";
    public static final String HOUSE_TABLE_NAME_COLUMN = "name";
    public static final String HOUSE_TABLE_YEAR_COLUMN = "year";
    public static final String HOUSE_TABLE_NUMBER_OF_FLOORS_COLUMN = "number_of_floors";




    private final String JDBC_DRIVER = "org.postgresql.Driver";
    private final String url = "jdbc:postgresql://localhost:2888/studs";
    private String user;
    private String password;
    private Connection connection;
    public DatabaseManager(){
        connectionToDatabase();
    }
    private void connectionToDatabase(){
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        System.out.println("Подключение к базе данных...");
        while (true) {
            System.out.println("Введите логин:");
            this.user = scanner.nextLine();
            System.out.println("Введите пароль:");
            this.password = "ruz426";//String.valueOf(console.readPassword());
            try{
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Соединение с базой данных установлено!");
                break;
            } catch (SQLException e) {
                System.out.println("Произошла ошибка при подключении к базе данных!");
                System.out.println("Проверьте правильность ввода логина и пароля!");
            } catch (ClassNotFoundException e) {
                System.out.println("Драйвер управления базой данных не найден!");
                System.exit(0);
            }
        }
    }


    public PreparedStatement getPreparedStatement(String sqlStatement, boolean generateKeys) throws SQLException {
        PreparedStatement preparedStatement=null;
        try {
            if (connection == null) throw new SQLException();
            int autoGeneratedKeys = generateKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
            preparedStatement = connection.prepareStatement(sqlStatement, autoGeneratedKeys);
            //App.logger.info("Подготовлен SQL запрос '" + sqlStatement + "'.");
            return preparedStatement;
        } catch (SQLException exception) {
            //App.logger.error("Произошла ошибка при подготовке SQL запроса '" + sqlStatement + "'.");
            if (connection == null)
            throw new SQLException(exception);
        }
        return preparedStatement; //!
    }
    public void setAutoCommit() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при установлении 'auto_commit'!");
        }
    }
    public void setCommit() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при установлении 'commit'!");
        }
    }
    public void commit() {
        try {
            if (connection == null) throw new SQLException();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при подтверждении нового состояния базы данных!");
        }
    }
    public void rollback() {
        try {
            if (connection == null) throw new SQLException();
            connection.rollback();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при возврате исходного состояния базы данных!");
        }
    }
     public void setSavepoint() {
        try {
            if (connection == null) throw new SQLException();
            connection.setSavepoint();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при сохранении состояния базы данных!");
        }
    }

    public void closePreparedStatement(PreparedStatement sqlStatement) {
        if (sqlStatement == null) return;
        try {
            sqlStatement.close();

        } catch (SQLException exception) {

        }
    }


    public void closeConnection(){
        if (connection == null) return;
        try {
            connection.close();
            System.out.println("Соединение с базой данных разорвано!");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при разрыве соединения с базой данных!");
        }
    }
}
