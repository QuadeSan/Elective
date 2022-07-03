package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Course;
import application.services.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LogManager.getLogger(CourseServiceImpl.class);

    private final DAOFactory factory;

    public CourseServiceImpl() {
        this(DAOFactory.getInstance());
    }

    public CourseServiceImpl(DAOFactory daoFactory) {
        this.factory = daoFactory;
        logger.debug("DAOFactory created => " + daoFactory);
    }

    @Override
    public OperationResult createCourse(String topic, String title) {
        try (CourseDAO courseDAO = factory.getCourseDAO()) {
            logger.debug("CourseDAO created");

            courseDAO.createCourse(topic, title);
            logger.debug("createCourse Method used");

            logger.info("Course " + title + " was successfully created");
            return new OperationResult(true, "New course was successfully created");
        } catch (AlreadyExistException ex) {
            logger.error("Course with title " + title + " already exist");
            return new OperationResult(false, "Course with title " + title + " already exist");
        } catch (DAOException e) {
            logger.error("Can't create new Course", e);
            return new OperationResult(false, "Unhandled exception");
        } catch (Exception e) {
            logger.error("Can't close CourseDAO");
            return new OperationResult(false, "Unhandled exception");
        }
    }

    @Override
    public ValuedOperationResult<Course> findCourse(int courseId) {
        try (CourseDAO courseDAO = factory.getCourseDAO()) {
            logger.debug("CourseDAO created");

            Course currentCourse = courseDAO.findCourse(courseId);
            logger.debug("findCurse by ID Method used");

            return new ValuedOperationResult<>(true, "Course was found", currentCourse);
        } catch (NotExistException e) {
            logger.error("Course with ID = " + courseId + " does not exist", e);
            return new ValuedOperationResult<>(false,
                    "Course with ID = " + courseId + " does not exist", null);
        } catch (DAOException e) {
            return new ValuedOperationResult<>(false,
                    "Unhandled exception", null);
        } catch (Exception e) {
            logger.error("Can't close CourseDAO");
            return new ValuedOperationResult<>(false, "Unhandled exception",null);
        }
    }

    @Override
    public OperationResult deleteCourse(int courseId) {
        try (CourseDAO courseDAO = factory.getCourseDAO()) {
            logger.debug("CourseDAO created");

            courseDAO.deleteCourse(courseId);
            logger.debug("deleteCourse Method used");

            logger.info("Course #" + courseId + " was deleted");
            return new OperationResult(true, "Course was successfully deleted");
        } catch (NotExistException e) {
            logger.error("Course with ID = " + courseId + " does not exist", e);
            return new OperationResult(false,
                    "Course with ID = " + courseId + " does not exist");
        } catch (Exception e) {
            logger.error("Can't close CourseDAO");
            return new OperationResult(false, "Unhandled exception");
        }
    }

    @Override
    public OperationResult changeTopic(int courseId, String newTopic) {
        try (CourseDAO courseDAO = factory.getCourseDAO()) {
            logger.debug("CourseDAO created");

            courseDAO.changeTopic(courseId, newTopic);
            logger.debug("changeTopic Method used");
            return new OperationResult(true, "Topic was changed");
        } catch (DAOException e) {
            logger.error("Can't change status of course", e);
            return new OperationResult(false, "Unhandled exception");
        } catch (Exception e) {
            logger.error("Can't close CourseDAO");
            return new OperationResult(false, "Unhandled exception");
        }
    }

    @Override
    public OperationResult changeTitle(int courseId, String newTitle) {
        try (CourseDAO courseDAO = factory.getCourseDAO()) {
            logger.debug("CourseDAO created");

            courseDAO.changeTitle(courseId, newTitle);
            logger.debug("changeTopic Method used");
            return new OperationResult(true, "Title was changed");
        } catch (DAOException e) {
            logger.error("Can't change status of course", e);
            return new OperationResult(false, "Unhandled exception");
        } catch (Exception e) {
            logger.error("Can't close CourseDAO");
            return new OperationResult(false, "Unhandled exception");
        }
    }

    @Override
    public OperationResult changeStatus(int courseId, String status) {
        try (CourseDAO courseDAO = factory.getCourseDAO()) {
            logger.debug("CourseDAO created");

            courseDAO.changeStatus(courseId, status);
            logger.debug("changeStatus Method used");
            return new OperationResult(true, "Status was changed");
        } catch (NotExistException e) {
            logger.error("Course with ID = " + courseId + " does not exist");
            return new OperationResult(false, "Course with ID = " + courseId + " does not exist");
        } catch (DAOException e) {
            logger.error("Can't change status of course", e);
            return new OperationResult(false, "Unhandled exception");
        } catch (Exception e) {
            logger.error("Can't close CourseDAO");
            return new OperationResult(false, "Unhandled exception");
        }
    }

    @Override
    public ValuedOperationResult<Iterable<Course>> showAllCourses() {
        try (CourseDAO courseDAO = factory.getCourseDAO()) {
            logger.debug("CourseDAO created");

            Iterable<Course> result = courseDAO.showAllCourses();
            logger.debug("showAllCourses Method used");

            return new ValuedOperationResult<>(true, "List of courses", result);
        } catch (DAOException e) {
            logger.error("Can't show all courses", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        } catch (Exception e) {
            logger.error("Can't close CourseDAO");
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        }
    }
}
