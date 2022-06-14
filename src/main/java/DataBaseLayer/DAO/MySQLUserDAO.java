package DataBaseLayer.DAO;

import DataBaseLayer.entity.User;

import java.util.List;

public class MySQLUserDAO implements UserDAO {
    @Override
    public void createUser(String login, String password) {

    }

    @Override
    public User findUser(int id) {
        return null;
    }

    @Override
    public User findUser(String login) {
        return null;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public void changeLogin(User user, String newLogin) {

    }

    @Override
    public void changePassword(User user, String newPassword) {

    }

    @Override
    public List<User> showAllUsers() {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
