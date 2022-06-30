package application.services;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Teacher;

/**
 * Business logic service, which work with
 * {@link Teacher} entities
 * {@link #findTeacher(String, String) Find student} is
 * method for authorization as a teacher
 */
public interface TeacherService {

    /**
     * @param login    - login new teacher
     * @param password - password of new teacher
     * @param email    - email of new teacher
     * @param name     - name of new teacher
     * @param lastName - last name of new teacher
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method fails
     */
    OperationResult createTeacher(String login, String password, String email, String name, String lastName);

    /**
     * @param login    - login used during authorization process
     * @param password - password used during authorization process
     * @return {@link ValuedOperationResult} entity which contain
     * success boolean and {@link Teacher} entity as a result
     * to set current user in session on success
     * and high-level error message if method fails
     */
    ValuedOperationResult<Teacher> findTeacher(String login, String password);

    ValuedOperationResult<Teacher> findTeacher(String login);

    ValuedOperationResult<Teacher> findTeacher(int teacherId);

    ValuedOperationResult<Iterable<Teacher>> showAllTeachers();

    OperationResult deleteAccount(int userId);
}
