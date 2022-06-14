package Services;

import DataBaseLayer.entity.Course;

import java.util.List;

public interface CourseService {

    void createCourse(String title);

    Course findCourse(int course_id);

    Course findCourse(String title);

    void deleteCourse(int course_id);

    void changeStatus(int courseID, String status);

    List<Course> showAllCourses();

}
