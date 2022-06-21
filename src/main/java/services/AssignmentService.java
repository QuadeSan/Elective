package services;

import controller.Response;
import dataBaseLayer.entity.Course;
import dataBaseLayer.entity.Student;

import java.util.List;

public interface AssignmentService {

    Response assignTeacherToCourse(int courseId, int teacherId);

    void unassignTeacherFromCourse(int courseId, int teacherId);

    Response assignStudentToCourse(int courseId, int studentId);

    void unassignStudentFromCourse(int courseId, int studentId);

    List<Course> showTeacherCourses(int teacherId);

    List<Course> showStudentCourses(int studentId);

    List<Student> showStudentsOnCourse(int courseId);

    void setMarkForStudent(int courseId, int studentId, int mark);
}
