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
     * @param userId - student_id value from students table
     * @return {@link Administrator} entity
     * Administrator contain administratorID, userID, login, email, name, lastName fields
     */
    Administrator findAdministrator(int userId);

    /**
     * Delete a row in user table
     *
     * @param userId - id of user for delete.
     */
    void deleteAccount(int userId);

    /**
     * @param userId    - id of user
     * @param newLogin  - new value of login
     * @throws AlreadyExistException - if there is user with same login
     */
    void updateLogin(int userId, String newLogin) throws AlreadyExistException;

    /**
     * @param userId    - id of user
     * @param newEmail  - new value of email
     */
    void updateEmail(int userId, String newEmail);

    /**
     * @param userId        - id of user
     * @param newPassword   - new value of email
     */
    void updatePassword(int userId, String newPassword);

    /**
     * @param userId    - id of user
     * @param newName   - new value of name
     */
    void updateName(int userId, String newName);

    /**
     * @param userId        - id of user
     * @param newLastName   - new value of last name
     */
    void updateLastName(int userId, String newLastName);
}
