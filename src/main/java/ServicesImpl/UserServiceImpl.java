package ServicesImpl;

import DataBaseLayer.entity.User;
import Services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public void createUser(String login, String password) {

    }

    @Override
    public void deleteUser(int id) {

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
    public List<User> showAll() {
        return null;
    }

    @Override
    public void changeLogin(User user, String newLogin) {

    }

    @Override
    public void changePassword(User user, String newPassword) {

    }
}
