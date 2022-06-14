package DataBaseLayer.DAOimpl;

import DataBaseLayer.DAO.AdministratorDAO;
import DataBaseLayer.TempDB.UserInMemory;
import DataBaseLayer.entity.Administrator;
import DataBaseLayer.entity.User;

import java.util.List;

public class AdministratorDAOImlp implements AdministratorDAO {


//    @Override
//    public void addAdministrator(Administrator administrator) {
//
//    }

    @Override
    public void createAdministrator(String login, String password, String email) {

    }

    @Override
    public Administrator findAdministrator(int id) {
        return null;
    }

    @Override
    public Administrator findAdministrator(String login) {
        return null;
    }

    @Override
    public Administrator findAdministrator(String login, String password) {
        return null;
    }

    @Override
    public void editAdministrator(int id, String... params) {

    }

    @Override
    public void deleteAccount(int user_id) {

    }


    @Override
    public List<Administrator> showAll() {
        return null;
    }

    ////////////////////////////



//    @Override
//    public void createUser(String login, String password) {
//        User newUser = new User(login, password);
//        UserInMemory users = UserInMemory.getInstance();
//        users.addUser(newUser);
//    }
//
//    @Override
//    public User findUser(int id) {
//        UserInMemory users = UserInMemory.getInstance();
//        return users.findUser(id);
//    }
//
//    @Override
//    public User findUser(String login) {
//        UserInMemory users = UserInMemory.getInstance();
//        return users.findUser(login);
//    }
//
//    @Override
//    public void deleteUser(int id) {
//        UserInMemory users = UserInMemory.getInstance();
//        User currentUser = users.findUser(id);
//        if (currentUser != null) {
//            users.deleteUser(currentUser);
//        }
//    }

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
