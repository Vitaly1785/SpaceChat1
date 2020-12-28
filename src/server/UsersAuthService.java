package server;

import connect.ConnectionService;
import connect.SpaceRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersAuthService implements AuthService {
    private class Users {
        String login;
        String password;
        String nickname;

        public Users(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }

    private List<Users> users;


    public UsersAuthService() {
        Connection connection = ConnectionService.connect();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");

            ResultSet rs = statement.executeQuery();
            users = new ArrayList<>();
            while (rs.next()) {
                users.add(
                        new Users(
                                rs.getString("login"),
                                rs.getString("password"),
                                rs.getString("nickname")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("SWW", e);
        } finally {
            ConnectionService.close(connection);
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return SpaceRepository.findByNickName(login, password);
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        for (Users user : users) {
            if (user.login.equals(login) || user.nickname.equals(nickname)) {
                return false;
            }
        }
        users.add(new Users(login, password, nickname));
        return true;
    }

    @Override
    public boolean changeNickname(String trueName, String newNick) {
        for (Users user : users) {
            if (user.nickname.equals(newNick)) {
                return false;
            }
        }
        return SpaceRepository.changeNickname(trueName, newNick);
    }
}
