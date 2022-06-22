package application.services.impl;

import application.OperationResult;
import application.dao.*;
import application.entity.Course;
import application.services.CourseService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

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
        CourseDAO courseDAO = null;
        try {
            courseDAO = factory.getCourseDAO();
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
            return new OperationResult(false, "Something went wrong! Have no response from database");
        } finally {
            try {
                if (courseDAO != null) {
                    courseDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close CourseDAO");
            }
        }
    }

    @Override
    public Course findCourse(int courseId) {
        Course currentCourse;
        CourseDAO courseDAO = null;
        try {
            courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");

            currentCourse = courseDAO.findCourse(courseId);
            logger.debug("findCurse by ID Method used");

            return currentCourse;
        } catch (NotExistException e) {
            logger.error("Course with ID " + courseId + " does not exist", e);
            return new Course();
        } catch (DAOException e) {
            logger.error("Can't find Course with ID " + courseId);
            return new Course();
        } finally {
            try {
                if (courseDAO != null) {
                    courseDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close CourseDAO");
            }
        }
    }

    @Override
    public Course findCourse(String title) {
        Course currentCourse;
        CourseDAO courseDAO = null;
        try {
            courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");

            currentCourse = courseDAO.findCourse(title);
            logger.debug("findCurse by title Method used");

            return currentCourse;
        } catch (NotExistException e) {
            logger.error("Course with title " + title + " does not exist", e);
            return new Course();
        } catch (DAOException e) {
            logger.error("Can't find Course with title " + title, e);
            return new Course();
        } finally {
            try {
                if (courseDAO != null) {
                    courseDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close CourseDAO");
            }
        }
    }

    @Override
    public OperationResult deleteCourse(int courseId) {
        CourseDAO courseDAO = null;
        try {
            courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");

            courseDAO.deleteCourse(courseId);
            logger.debug("deleteCourse Method used");

            logger.info("Course #" + courseId + " was deleted");
            return new OperationResult(true,"Course was successfully deleted");
        } catch (NotExistException e) {
            logger.error("Course with id #" + courseId + "does not exist");
            return new OperationResult(false,"Course with id #" + courseId + "doesn't exist");
        } catch (DAOException e) {
            logger.error("Can't delete Course with ID" + courseId, e);
            return new OperationResult(false, "Something went wrong! Have no response from database");
        } finally {
            try {
                if (courseDAO != null) {
                    courseDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close CourseDAO");
            }
        }
    }

    @Override
    public void changeStatus(int courseId, String status) {
        CourseDAO courseDAO = null;
        try {
            courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");

            courseDAO.changeStatus(courseId, status);
            logger.debug("changeStatus Method used");
        } catch (DAOException e) {
            logger.error("Can't change status of Course " + courseId, e);
        } finally {
            try {
                if (courseDAO != null) {
                    courseDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close CourseDAO");
            }
        }
    }

    @Override
    public Iterable<Course> showAllCourses() {
        Iterable<Course> result = new ArrayList<>();
        CourseDAO courseDAO = null;
        try {
            courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");

            result = courseDAO.showAllCourses();
            logger.debug("showAllCourses Method used");

            return result;
        } catch (DAOException e) {
            logger.error("Can't show all courses", e);
            return result;
        } finally {
            try {
                if (courseDAO != null) {
                    courseDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close CourseDAO");
            }
        }
    }
}
