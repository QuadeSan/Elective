package Services;

import DataBaseLayer.QueryResult;
import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Student;

import java.util.List;

public interface StudentService {

    QueryResult createStudent(String login, String password, String email);

    Student findStudent(int student_id);

    Student findStudent(String login);

    Student findStudent(String login,String password);

    void deleteAccount(int user_id);

    void lockStudent(int student_id, String status);

    List<Course> showAssignedCourses(Student student);

    void assignToCourse(Student student, Course course);

    void dropCourse(Student student, Course course);

    void editStudent(int id, String... params);

    List<Student> showAllStudents();
}
