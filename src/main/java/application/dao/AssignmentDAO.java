package application.dao;

import application.entity.Course;
import application.entity.Student;

import java.util.List;

public interface AssignmentDAO extends AutoCloseable {

    void assignTeacherToCourse(int courseID, int teacherID) throws AlreadyExistException;

    void unassignTeacherFromCourse(int courseID, int teacherID);

    void assignStudentToCourse(int courseID, int studentID) throws AlreadyExistException;

    void unassignStudentFromCourse(int courseID, int studentID);

    Iterable<Course> showTeacherCourses(int teacherID);

    Iterable<Course> showStudentCourses(int studentID);

    Iterable<Student> showStudentsOnCourse(int courseID);

    void setMarkForStudent(int courseID, int studentID, int mark);

}
