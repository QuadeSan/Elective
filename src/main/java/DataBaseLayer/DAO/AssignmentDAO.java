package DataBaseLayer.DAO;

import DataBaseLayer.entity.Course;

public interface AssignmentDAO extends AutoCloseable {

    void assignTeacherToCourse(int courseID, int teacherID);

    void unassignTeacherFromCourse(int courseID, int teacherID);

    void assignStudentToCourse(int courseID, int studentID);

    void unassignStudentFromCourse(int courseID, int studentID);

    void setMarkForStudent(int courseID, int studentID, int mark);

    Course getFullInfoAboutCourse(int courseID);
}
