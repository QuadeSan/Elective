package application.services;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Course;

public interface CourseService {

    OperationResult createCourse(String topic, String title);

    ValuedOperationResult<Course> findCourse(int courseId);

    ValuedOperationResult<Course> findCourse(String title);

    OperationResult deleteCourse(int courseId);

    OperationResult changeStatus(int courseId, String status);

    ValuedOperationResult<Iterable<Course>> showAllCourses();

}
