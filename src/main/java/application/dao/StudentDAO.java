package application.dao;

import application.entity.Student;

/**
 * DAO interfaces uses connection to database
 * Extending AutoCloseable helps person, who will use current DAO to understand
 * that here are might be some recourses he needs to close
 * to have no resources leak
 */
public interface StudentDAO extends AutoCloseable {

    /**
     * Inserting new record to users table and new record to students table
     *
     * @param login    - login of new student.
     * @param password - password of new student.
     * @param email    - email of new student
     * @param name     - name of new student
     * @param lastName - last name of new student.
     * @throws AlreadyExistException - if there is user with same login
     *                               when exception is thrown rollback users table insert
     */
    void createStudent(String login, String password, String email, String name, String lastName) throws AlreadyExistException;

    /**
     * @param login    - login value from users table
     * @return {@link Student} entity for authorization
     * Student contain studentID, userID, name,
     * lastName, status, login, password, email fields
     * @throws NotExistException - if there are no users with current
     *                           combination of login and password.
     */
    Student findStudent(String login) throws NotExistException;

    /**
     * Updating status of current student
     *
     * @param studentId - student_id value from students table
     * @param status    - new status
     * @throws NotExistException - if there is no student with current ID
     */
    void changeStatus(int studentId, String status) throws NotExistException;

    /**
     * @return Iterable of {@link Student} entities
     * Each Student contain studentID, userID, name, lastName, status fields
     */
    Iterable<Student> showAllStudents();

    /**
     * @param offSet - count of records to skip
     * @param limit  - count of records user see on webpage
     * @return limited Iterable of {@link Student} entities
     * Each Student contain studentID, userID, name, lastName, status fields
     */
    Iterable<Student> showAllStudents(int offSet, int limit);

    /**
     * @return count of students in database
     * Method need for pagination realization
     */
    int studentCount();
}
