package application.services;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Administrator;
import application.entity.Course;
import application.entity.Student;
import application.entity.Teacher;

/**
 * Business logic service, which work with
 * all relations between {@link Course}, {@link Student} and {@link Teacher}
 */
public interface AssignmentService {

    /**
     * Method used for assignment teacher to new course or reassign
     * new teacher for course with assigned teacher
     *
     * @param courseId     - Course ID to build new assignment
     * @param newTeacherId - ID of teacher assigned to the current course
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method fails
     */
    OperationResult changeTeacherAssignment(int courseId, int newTeacherId);

    /**
     * Method used by students to enroll in course
     *
     * @param courseId  - Course ID to build new assignment
     * @param studentId - ID of the student who is trying to enroll in the current course
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method fails
     */
    OperationResult assignStudentToCourse(int courseId, int studentId);

    /**
     * Method used by students to leave the course
     *
     * @param courseId  - Course ID, which student wants to leave
     * @param studentId - ID of the student who is trying to leave the course
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method fails
     */
    OperationResult unassignStudentFromCourse(int courseId, int studentId);

    /**
     * Method for teachers to see all their assignments
     *
     * @param teacherId - ID of current teacher
     * @return success boolean and Iterable of {@link Course} entities as a result
     * to set view-only list of all courses associated with the current teacher in session on success
     * and high-level error message if method fails
     */
    ValuedOperationResult<Iterable<Course>> showTeacherCourses(int teacherId);

    /**
     * Method for students to see all their assignments
     *
     * @param studentId - ID of current student
     * @return success boolean and Iterable of {@link Course} entities as a result
     * to set view-only list of all courses associated with the current student in session on success
     * and high-level error message if method fails
     */
    ValuedOperationResult<Iterable<Course>> showStudentCourses(int studentId);

    /**
     * Method for teachers, represents a journal for the selected course
     *
     * @param courseId - ID of current course
     * @return success boolean and Iterable of {@link Student} entities as a result
     * to set view-only list of all students signed for current course in session on success
     * and high-level error message if method fails
     */
    ValuedOperationResult<Iterable<Student>> showJournal(int courseId);

    /**
     * Method for teachers to set mark in journal
     *
     * @param courseId  - ID of current course
     * @param studentId - ID of student to change current mark
     * @param mark      - new mark for student
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method fails
     */
    OperationResult setMarkForStudent(int courseId, int studentId, int mark);
}
