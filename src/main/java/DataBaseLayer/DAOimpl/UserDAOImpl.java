package DataBaseLayer.DAOimpl;

import DataBaseLayer.DAO.UserDAO;
import DataBaseLayer.TempDB.UserInMemory;
import DataBaseLayer.entity.User;

import java.util.List;

abstract public class UserDAOImpl implements UserDAO {

    @Override
    public void createUser(String login, String password) {
        User newUser = new User(login, password);
        UserInMemory users = UserInMemory.getInstance();
        users.addUser(newUser);
    }

    @Override
    public User findUser(int id) {
        UserInMemory users = UserInMemory.getInstance();
        return users.findUser(id);
    }

    @Override
    public User findUser(String login) {
        UserInMemory users = UserInMemory.getInstance();
        return users.findUser(login);
    }

    @Override
    public void deleteUser(int id) {
        UserInMemory users = UserInMemory.getInstance();
        User currentUser = users.findUser(id);
        if (currentUser != null) {
            users.deleteUser(currentUser);
        }
    }

    @Override
    public void changeLogin(User user, String newLogin) {
        user.setLogin(newLogin);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }

    @Override
    public List<User> showAllUsers() {
        UserInMemory users = UserInMemory.getInstance();
        return users.getUserTable();
    }

    @Override
    public void close() throws Exception {

    }
}
