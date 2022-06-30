package application.dao;

import application.entity.Course;
import application.entity.Student;

/**
 * DAO interfaces uses connection to database
 * Extending AutoCloseable helps person, who will use current DAO to understand
 * that here are might be some recourses he needs to close
 * to have no resources leak
 */
public interface AssignmentDAO extends AutoCloseable {

    /**
     * Inserting new record to teachers_assignments table
     *
     * @param courseId  - foreign key refer to courses table
     * @param teacherId - foreign key refer to teachers table
     * @throws AlreadyExistException - if there are any record with same courseId
     */
    void assignTeacherToCourse(int courseId, int teacherId) throws AlreadyExistException;

    /**
     * Deleting a record from teachers_assignments table with current courseId
     * then adding a new record to teachers_assignments table
     *
     * @param courseId     - foreign key refer to courses table
     * @param newTeacherId - foreign key refer to teachers table
     * @throws NotExistException - if there are no records with current courseId
     */
    void changeTeacherAssignment(int courseId, int newTeacherId) throws NotExistException;

    /**
     * Inserting new record to students_assignments table
     *
     * @param courseId  - foreign key refer to courses table
     * @param studentId - foreign key refer to students table
     * @throws AlreadyExistException - if there are any record with combination of
     *                               current courseId and current studentId
     */
    void assignStudentToCourse(int courseId, int studentId) throws AlreadyExistException;

    /**
     * Deleting record from students_assignments table
     *
     * @param courseId  - foreign key refer to courses table
     * @param studentId - foreign key refer to students table
     */
    void unassignStudentFromCourse(int courseId, int studentId);

    /**
     * @param teacherId - foreign key refer to teachers table
     * Each Course contain course_id, topic, title, status fields
     * @return Iterable of {@link Course} entities assigned to current teacher
     */
    Iterable<Course> showTeacherCourses(int teacherId);

    /**
     * @param studentId - foreign key refer to student table
     * Each Course contain courseId, topic, title, status, mark fields
     * @return Iterable of {@link Course} entities current student is assigned for
     */
    Iterable<Course> showStudentCourses(int studentId);

    /**
     * @param courseId - foreign key refer to courses table
     * Each Student contain studentId, name, lastName, mark fields
     * @return Iterable of {@link Student} entities assigned to current course
     */
    Iterable<Student> showStudentsOnCourse(int courseId);

    /**
     * Updating mark field for current student
     *
     * @param courseId  - foreign key refer to courses table
     * @param studentId - ID of student whose mark is being changed
     * @param mark      - new value of mark field
     */
    void setMarkForStudent(int courseId, int studentId, int mark);

}
