package DataBaseLayer.DAO;

import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Teacher;

import java.util.List;
import java.util.StringTokenizer;

public interface CourseDAO extends AutoCloseable {

    void createCourse(String topic,String title);

    Course findCourse(int course_id);

    Course findCourse(String title);

    void deleteCourse(int course_id);

    void changeStatus(int id, String status);

    List<Course> showAllCourses();

}
