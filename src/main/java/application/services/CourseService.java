package application.services;

import application.OperationResult;
import application.entity.Course;

public interface CourseService {

    OperationResult createCourse(String topic, String title);

    Course findCourse(int courseId);

    Course findCourse(String title);

    OperationResult deleteCourse(int courseId);

    void changeStatus(int courseId, String status);

    Iterable<Course> showAllCourses();

}
