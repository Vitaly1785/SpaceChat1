package connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
    private ConnectionService() {}

    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/SpaceChat?serverTimezone=Europe/Moscow", "root", "jpwv8zcm17VNP");
        } catch (SQLException throwables) {
            throw new RuntimeException("SWW", throwables);
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


   /* Connection connection;

    public Connection getConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + name + "?" + "serverTimezone=Europe/Moscow";
        connection = DriverManager.getConnection(connectionString, user, password);

        return connection;*/
    }


