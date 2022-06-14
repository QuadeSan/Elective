package Services;

import DataBaseLayer.entity.User;

import java.util.List;

public interface UserService {

    void createUser(String login, String password);

    void deleteUser(int id);

    User findUser(int id);

    User findUser(String login);

    void changeLogin(User user, String newLogin);

    void changePassword(User user, String newPassword);

    List<User> showAll();

}
