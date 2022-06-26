package application.services;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Course;
import application.entity.Student;

public interface AssignmentService {

    OperationResult assignTeacherToCourse(int courseId, int teacherId);

    OperationResult changeTeacherAssignment(int courseId, int newTeacherId);

    OperationResult assignStudentToCourse(int courseId, int studentId);

    OperationResult unassignStudentFromCourse(int courseId, int studentId);

    ValuedOperationResult<Iterable<Course>> showTeacherCourses(int teacherId);

    ValuedOperationResult<Iterable<Course>> showStudentCourses(int studentId);

    ValuedOperationResult<Iterable<Student>> showStudentsOnCourse(int courseId);

    void setMarkForStudent(int courseId, int studentId, int mark);
}
