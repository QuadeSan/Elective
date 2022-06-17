package Services;

import DataBaseLayer.QueryResult;
import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Student;
import DataBaseLayer.entity.Teacher;

import java.util.List;

public interface TeacherService {

    QueryResult createTeacher(String login, String password, String email, String name, String lastName);

    Teacher findTeacher(int teacher_id);

    Teacher findTeacher(String login);

    Teacher findTeacher(String login, String password);

    void deleteAccount(int user_id);

    List<Course> showAssignedCourses(Teacher teacher);

    void setMarkForCourse(Student student, Course course);
}
