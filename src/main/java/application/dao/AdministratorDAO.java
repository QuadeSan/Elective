package application.dao;

import application.entity.Administrator;

/**
 * DAO interfaces uses connection to database
 * Extending AutoCloseable helps person, who will use current DAO to understand
 * that here are might be some recourses he needs to close
 * to have no resources leak
 */
public interface AdministratorDAO extends AutoCloseable {

    /**
     * Inserting new record to users table and new record to administrators table
     *
     * @param login    - login of new administrator.
     * @param password - password of new administrator.
     * @param email    - email of new administrator.
     * @throws AlreadyExistException - if there is user with same login
     *                               when exception is thrown rollback users table insert
     */
    void createAdministrator(String login, String password, String email,String name,String lastName) throws AlreadyExistException;

    /**
     * @param login    - login value from users table
     * Administrator contain administratorID, userID, name,
     * lastName, login, password, email fields
     * @return {@link Administrator} entity for authorization
     * @throws NotExistException - if there are no users with current
     *                           combination of login and password.
     */
    Administrator findAdministrator(String login) throws NotExistException;

    /**
     * Delete a row in user table
     *
     * @param userId - id of user for delete.
     */
    void deleteAccount(int userId);

//    void changeLogin(int userId, String newLogin);
//
//    void changePassword(int userId, String newPassword);
}
