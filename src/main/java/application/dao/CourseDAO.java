package application.dao;

import application.entity.Course;

/**
 * DAO interfaces uses connection to database
 * Extending AutoCloseable helps person, who will use current DAO to understand
 * that here are might be some recourses he needs to close
 * to have no resources leak
 */
public interface CourseDAO extends AutoCloseable {

    /**
     * Insert new row to course table with default status value
     *
     * @param topic - topic of current course
     * @param title - title of current course
     * @throws AlreadyExistException if database contain any row with same title value
     */
    void createCourse(String topic, String title) throws AlreadyExistException;

    /**
     * @param courseId - value of course_id field
     * Course contain courseId, topic, title, status and assignedTeacher fields
     * @return {@link Course} entity from courses table
     * @throws NotExistException if there are no records with current ID value
     */
    Course findCourse(int courseId) throws NotExistException;

    /**
     * @param title - value of title field
     * Course contain courseId, topic, title, status fields
     * @return {@link Course} entity from courses table
     * @throws NotExistException if there are no records with current title value
     */
    Course findCourse(String title) throws NotExistException;

    /**
     * Deleting record from courses table
     *
     * @param courseId - value of course_id field
     * @throws NotExistException if there are no records with current ID value
     */
    void deleteCourse(int courseId) throws NotExistException;

    /**
     * Updates status of current course
     *
     * @param courseId - value of course_id field
     * @throws NotExistException if there are no records with current ID value
     */
    void changeStatus(int courseId, String status) throws NotExistException;

    /**
     * Updates topic of current course
     *
     * @param newTopic - value of topic field
     */
    void changeTopic(int courseId, String newTopic);

    /**
     * Updates status of current course
     *
     * @param newTitle - value of title field
     */
    void changeTitle(int courseId, String newTitle);

    /**
     * @return Iterable of all {@link Course} entities
     * Each course contain course_id, topic, title, status,
     * assignedTeacher and studentCount fields
     */
    Iterable<Course> showAllCourses();
}
