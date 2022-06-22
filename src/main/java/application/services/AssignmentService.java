package application.services;

import application.OperationResult;
import application.entity.Course;
import application.entity.Student;

public interface AssignmentService {

    OperationResult assignTeacherToCourse(int courseId, int teacherId);

    void unassignTeacherFromCourse(int courseId, int teacherId);

    OperationResult assignStudentToCourse(int courseId, int studentId);

    void unassignStudentFromCourse(int courseId, int studentId);

    Iterable<Course> showTeacherCourses(int teacherId);

    Iterable<Course> showStudentCourses(int studentId);

    Iterable<Student> showStudentsOnCourse(int courseId);

    void setMarkForStudent(int courseId, int studentId, int mark);
}
