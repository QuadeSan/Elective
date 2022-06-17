package DataBaseLayer.DAO;

import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Student;

import java.util.List;

public interface AssignmentDAO extends AutoCloseable {

    void assignTeacherToCourse(int courseID, int teacherID);

    void unassignTeacherFromCourse(int courseID, int teacherID);

    void assignStudentToCourse(int courseID, int studentID);

    void unassignStudentFromCourse(int courseID, int studentID);

    List<Course> showTeacherCourses(int teacherID);

    List<Course> showStudentCourses(int studentID);

    void setMarkForStudent(int courseID, int studentID, int mark);

    List<Student> showStudentsOnCourse(int courseID);
}
