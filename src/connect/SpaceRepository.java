package connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SpaceRepository {
    public int createUsers(String login, String password, String nickname){
        Connection connection = ConnectionService.connect();
        try{
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (login, password, nickname) VALUES (?,?,?)");
            statement.setString(1,login);
            statement.setString(2,password);
            statement.setString(3,nickname);

           int row =  statement.executeUpdate();
           connection.commit();

        return row;
        } catch (SQLException e) {
            ConnectionService.rollback(connection);
            throw new RuntimeException("SWW", e);
        } finally {
            ConnectionService.close(connection);
        }
    }


    public static String findByNickName(String login, String password) {
        Connection connection = ConnectionService.connect();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ? and password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("nickname");
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("SWW", e);
        } finally {
            ConnectionService.close(connection);
        }
    }

    public static boolean changeNickname(String trueNickname, String newNickname) {
        Connection connection = ConnectionService.connect();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET nickname = ? WHERE nickname = ?;");
            statement.setString(1, newNickname);
            statement.setString(2, trueNickname);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
