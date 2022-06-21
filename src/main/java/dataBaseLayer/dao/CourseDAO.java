package dataBaseLayer.dao;

import dataBaseLayer.entity.Course;

import java.util.List;

public interface CourseDAO extends AutoCloseable {

    void createCourse(String topic,String title);

    Course findCourse(int course_id);

    Course findCourse(String title);

    void deleteCourse(int course_id);

    void changeStatus(int id, String status);

    List<Course> showAllCourses();

}
