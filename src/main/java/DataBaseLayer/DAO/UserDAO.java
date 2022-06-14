package DataBaseLayer.DAO;

import DataBaseLayer.entity.User;

import java.util.List;

public interface UserDAO extends AutoCloseable{
    void createUser(String login, String password);

    User findUser(int id);

    User findUser(String login);

    void deleteUser(int id);

    void changeLogin(User user, String newLogin);

    void changePassword(User user, String newPassword);

    List<User> showAllUsers();
}
