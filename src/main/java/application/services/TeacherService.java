package application.services;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Teacher;

/**
 * Business logic service, which work with
 * {@link Teacher} entities
 * {@link #authorizeTeacher(String, String) Find student} is
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
    ValuedOperationResult<Teacher> authorizeTeacher(String login, String password);

    /**
     * @return {@link ValuedOperationResult} entity which contain
     * success boolean and Iterable of {@link Teacher} entity as a result
     * to set view-only list of teachers in session on success
     * and high-level error message if method fails
     */
    ValuedOperationResult<Iterable<Teacher>> showAllTeachers();
}
