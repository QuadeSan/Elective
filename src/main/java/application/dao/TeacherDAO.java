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
     * @param login    - login value from users table
     * @return {@link Teacher} entity for authorization
     * Teacher contain teacherID, userID, name,
     * lastName, status, login, password, email fields
     * @throws NotExistException - if there are no users with current
     *                           combination of login and password.
     */
    Teacher findTeacher(String login) throws NotExistException;

    /**
     * @param userId - student_id value from students table
     * @return {@link Teacher} entity
     * Student contain teacherID, userID, login, email, name, lastName fields
     */
    Teacher findTeacher(int userId);

    /**
     * @return Iterable of {@link Teacher} entities
     * Each Teacher contain studentID, userID, name, lastName fields
     */
    Iterable<Teacher> showAllTeachers();

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
     * @param newPassword   - new value of password
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
