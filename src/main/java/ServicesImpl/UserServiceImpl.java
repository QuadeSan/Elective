package ServicesImpl;

import DataBaseLayer.DAO.UserDAO;
import DataBaseLayer.DAOimpl.UserDAOImpl;
import Services.UserService;
import DataBaseLayer.entity.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;

    private UserServiceImpl() {

    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public void createUser(String login, String password) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        userDAO.createUser(login, password);
    }

    @Override
    public void deleteUser(int id) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        userDAO.deleteUser(id);
    }

    @Override
    public User findUser(int id) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        return userDAO.findUser(id);
    }

    @Override
    public User findUser(String login) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        return userDAO.findUser(login);
    }

    @Override
    public List<User> showAll() {
        UserDAO userDAO = UserDAOImpl.getInstance();
        return userDAO.showAllUsers();
    }

    @Override
    public void changeLogin(User user, String newLogin) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        userDAO.changeLogin(user, newLogin);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        userDAO.changePassword(user, newPassword);
    }
}
