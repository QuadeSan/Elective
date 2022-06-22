package application.dao;

import application.entity.Administrator;
import application.entity.User;

import java.util.List;

public interface AdministratorDAO extends AutoCloseable{

    void createAdministrator(String login, String password, String email) throws AlreadyExistException;

    Administrator findAdministrator(int adminId) throws NotExistException;

    Administrator findAdministrator(String login) throws NotExistException;

    Administrator findAdministrator(String login, String password) throws NotExistException;

    void deleteAccount(int userId);

    Iterable<Administrator> showAll();

    //////////////////////////////////////

    void changeLogin(User user, String newLogin);

    void changePassword(User user, String newPassword);

}
