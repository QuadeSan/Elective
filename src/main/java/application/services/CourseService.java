package application.services;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Course;

/**
 * Business logic service, which work with
 * {@link Course} entities
 * Most methods are for administrator
 * but {@link #showAllCourses() ShowAllCourses}
 * method used to show all courses for any user
 */
public interface CourseService {

    /**
     * @param topic - topic of new course
     * @param title - title of new course
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method failed
     */
    OperationResult createCourse(String topic, String title);

    /**
     * @param courseId - Course ID you are looking for
     * @return {@link ValuedOperationResult} entity which contain
     * success boolean and {@link Course} entity as a result
     * to set current course in session on success
     * and high-level error message if method failed
     */
    ValuedOperationResult<Course> findCourse(int courseId);

    /**
     * @param courseId - Course ID you are going to delete
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method failed
     */
    OperationResult deleteCourse(int courseId);

    /**
     * @param courseId - Course ID you are going to modify
     * @param status   - new status
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method failed
     */
    OperationResult changeStatus(int courseId, String status);

    /**
     * @return {@link ValuedOperationResult} entity which contain
     * success boolean and Iterable of {@link Course} entity as a result
     * to set view-only list of courses in session on success
     * and high-level error message if method failed
     */
    ValuedOperationResult<Iterable<Course>> showAllCourses();

    /**
     * @param courseId - Course ID you are going to modify
     * @param newTopic - new topic
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method failed
     */
    OperationResult changeTopic(int courseId, String newTopic);

    /**
     * @param courseId - Course ID you are going to modify
     * @param newTitle - new title
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method failed
     */
    OperationResult changeTitle(int courseId, String newTitle);
}
