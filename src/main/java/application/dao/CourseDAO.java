package application.dao;

import application.entity.Course;

import java.util.List;

public interface CourseDAO extends AutoCloseable {

    void createCourse(String topic, String title) throws AlreadyExistException;

    Course findCourse(int courseId) throws NotExistException;

    Course findCourse(String title) throws NotExistException;

    void deleteCourse(int courseId)throws NotExistException;

    void changeStatus(int id, String status)throws NotExistException;

    void changeTopic(int courseId, String newTopic);

    void changeTitle(int courseId, String newTitle);

    Iterable<Course> showAllCourses();
}