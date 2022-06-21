package services;

import controller.Response;
import dataBaseLayer.entity.Course;

import java.util.List;

public interface CourseService {

    Response createCourse(String topic, String title);

    Course findCourse(int courseId);

    Course findCourse(String title);

    Response deleteCourse(int courseId);

    void changeStatus(int courseId, String status);

    List<Course> showAllCourses();

}
