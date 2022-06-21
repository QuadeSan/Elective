package services.impl;

import dataBaseLayer.AlreadyExistException;
import dataBaseLayer.dao.CourseDAO;
import dataBaseLayer.dao.DAOFactory;
import dataBaseLayer.DAOException;
import dataBaseLayer.NotExistException;
import controller.Response;
import dataBaseLayer.entity.Course;
import services.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

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
    public Response createCourse(String topic, String title) {
        CourseDAO courseDAO = null;
        try {
            courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");

            courseDAO.createCourse(topic, title);
            logger.debug("createCourse Method used");

            logger.info("Course " + title + " was successfully created");
            return new Response(true, "New course was successfully created");
        } catch (AlreadyExistException ex) {
            logger.error("Course with title " + title + " already exist");
            return new Response(false, "Course with title " + title + " already exist");
        } catch (DAOException e) {
            logger.error("Can't create new Course", e);
            return new Response(false, "Something went wrong! Have no response from database");
        } finally {
            try {
                if (courseDAO != null) {
                    courseDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
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
        } catch (DAOException e) {
            logger.error("Can't find Course with ID " + courseId);
            return new Course();
        } finally {
            try {
                if (courseDAO != null) {
                    courseDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
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
        } catch (DAOException e) {
            logger.error("Can't find Course with title " + title, e);
            return new Course();
        } finally {
            try {
                if (courseDAO != null) {
                    courseDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
            }
        }
    }

    @Override
    public Response deleteCourse(int courseId) {
        CourseDAO courseDAO = null;
        try {
            courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");

            courseDAO.deleteCourse(courseId);
            logger.debug("deleteCourse Method used");

            logger.info("Course #" + courseId + " was deleted");
            return new Response(true,"Course was successfully deleted");
        } catch (NotExistException e) {
            logger.error("Course with id #" + courseId + "doesn't exist");
            return new Response(false,"Course with id #" + courseId + "doesn't exist");
        } catch (DAOException e) {
            logger.error("Can't delete Course with ID" + courseId, e);
            return new Response(false, "Something went wrong! Have no response from database");
        } finally {
            try {
                if (courseDAO != null) {
                    courseDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
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
                logger.error("Can't close TeacherDAO");
            }
        }
    }

    @Override
    public List<Course> showAllCourses() {
        List<Course> result = new ArrayList<>();
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
                logger.error("Can't close TeacherDAO");
            }
        }
    }
}
