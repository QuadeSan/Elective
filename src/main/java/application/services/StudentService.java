package application.services;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Student;

/**
 * Business logic service, which work with
 * {@link Student} entities
 * {@link #findStudent(String, String) Find student} is
 * method for authorization as a student
 */
public interface StudentService {

    /**
     * @param login    - login new student
     * @param password - password of new student
     * @param email    - email of new student
     * @param name     - name of new student
     * @param lastName - last name of new student
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method fails
     */
    OperationResult createStudent(String login, String password, String email, String name, String lastName);

    /**
     * @param login    - login used during authorization process
     * @param password - password used during authorization process
     * @return {@link ValuedOperationResult} entity which contain
     * success boolean and {@link Student} entity as a result
     * to set current user in session on success
     * and high-level error message if method fails
     */
    ValuedOperationResult<Student> findStudent(String login, String password);

    /**
     * Method used by administrator to change
     * status of current student.
     * Locked student have no access to his profile
     * and can't sign up to any course
     *
     * @param studentId - ID of student whose status being changed
     * @param status    - new status for current student
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method fails
     */
    OperationResult lockStudent(int studentId, String status);

    /**
     * @return {@link ValuedOperationResult} entity which contain
     * success boolean and Iterable of {@link Student} entity as a result
     * to set view-only list of students in session on success
     * and high-level error message if method fails
     */
    ValuedOperationResult<Iterable<Student>> showAllStudents();

    /**
     * @param offSet - count of records to skip
     * @param limit  - count of records on page
     * @return {@link ValuedOperationResult} entity which contain
     * success boolean and limited Iterable of {@link Student} entity as a result
     * to set view-only list of students in session on success
     * and high-level error message if method fails
     */
    ValuedOperationResult<Iterable<Student>> showAllStudents(int offSet, int limit);

    /**
     * @return {@link ValuedOperationResult} entity which contain
     * success boolean and limited Iterable of {@link Integer} entity as a result
     * for pagination realization and high-level error message if method fails
     */
    ValuedOperationResult<Integer> studentCount();
}
