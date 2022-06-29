package application.dao;

import application.entity.Teacher;

/**
 * DAO interfaces uses connection to database
 * Extending AutoCloseable helps person, who will use current DAO to understand
 * that here are might be some recourses he needs to close
 * to have no resources leak
 */
public interface TeacherDAO extends AutoCloseable {

    /**
     * @param login    - login of new teacher.
     * @param password - password of new teacher.
     * @param email    - email of new teacher.
     * @param name     - name of new teacher.
     * @param lastName - last name of new teacher.
     * @throws AlreadyExistException if there is user with same login
     *                               when exception is thrown rollback users table insert
     */
    void createTeacher(String login, String password, String email, String name, String lastName) throws AlreadyExistException;

    /**
     * @param userId - user_id value from users table
     * @return {@link Teacher} entity
     * Teacher contain teacherID, userID, name, lastName fields
     * @throws NotExistException - if there are no user with current id
     */
    Teacher findTeacher(int userId) throws NotExistException;

    /**
     * @param login - login value from users table
     * @return {@link Teacher} entity
     * Teacher contain teacherID, userID, name, lastName fields
     * @throws NotExistException - if there are no user with current login
     */
    Teacher findTeacher(String login) throws NotExistException;

    /**
     * @param login    - login value from users table
     * @param password - password value from users table.
     * @return {@link Teacher} entity for authorization
     * Teacher contain teacherID, userID, name,
     * lastName, status, login, password, email fields
     * @throws NotExistException - if there are no users with current
     *                           combination of login and password.
     */
    Teacher findTeacher(String login, String password) throws NotExistException;

    /**
     * Delete a row in user table
     * @param userId - id of user for delete.
     */
    void deleteAccount(int userId);

    /**
     * @return Iterable of {@link Teacher} entities
     * Each Teacher contain studentID, userID, name, lastName fields
     */
    Iterable<Teacher> showAllTeachers();
}
