package dataBaseLayer.dao;

import dataBaseLayer.entity.Administrator;
import dataBaseLayer.entity.User;

import java.util.List;

public interface AdministratorDAO extends AutoCloseable{

    void createAdministrator(String login, String password, String email);

    Administrator findAdministrator(int admin_id);

    Administrator findAdministrator(String login);

    Administrator findAdministrator(String login, String password);

    void editAdministrator(int id, String... params);

    void deleteAccount(int user_id);

    List<Administrator> showAll();

    //////////////////////////////////////

    void changeLogin(User user, String newLogin);

    void changePassword(User user, String newPassword);

    List<User> showAllUsers();

}
