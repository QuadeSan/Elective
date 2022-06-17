package Services;

import DataBaseLayer.QueryResult;
import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Student;

import java.util.List;

public interface AssignmentService {

    QueryResult assignTeacherToCourse(int course_id, int teacher_id);

    void unassignTeacherFromCourse(int course_id, int teacher_id);

    QueryResult assignStudentToCourse(int course_id, int student_id);

    void unassignStudentFromCourse(int course_id, int student_id);

    List<Course> showTeacherCourses(int teacherID);

    List<Course> showStudentCourses(int studentID);

    List<Student> showStudentsOnCourse(int courseID);

    void setMarkForStudent(int courseID, int studentID, int mark);
}
