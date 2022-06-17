package ServicesImpl;

import DataBaseLayer.AlreadyExistException;
import DataBaseLayer.DAO.CourseDAO;
import DataBaseLayer.DAO.DAOFactory;
import DataBaseLayer.DAOException;
import DataBaseLayer.QueryResult;
import DataBaseLayer.entity.Course;
import Services.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LogManager.getLogger(CourseServiceImpl.class);

    @Override
    public QueryResult createCourse(String topic, String title) {
        QueryResult result = new QueryResult();
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            CourseDAO courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");
            courseDAO.createCourse(topic,title);
            logger.debug("createCourse Method used");
            courseDAO.close();
            return result;
        } catch (AlreadyExistException ex) {
            logger.info("Course with title " + title + " already exist");
            result.addException(ex);
            return result;
        } catch (DAOException e) {
            logger.debug("Can't create new Course", e);
            result.addException(e);
            return result;
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
            result.addException(e);
            return result;
        }
    }

    @Override
    public Course findCourse(int course_id) {
        Course currentCourse;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            CourseDAO courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");
            currentCourse = courseDAO.findCourse(course_id);
            logger.debug("findCurse by ID Method used");
            courseDAO.close();
            return currentCourse;
        } catch (DAOException e) {
            logger.debug("Can't find Course with ID " + course_id, e);
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
        }
        return null;
    }

    @Override
    public Course findCourse(String title) {
        Course currentCourse;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            CourseDAO courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");
            currentCourse = courseDAO.findCourse(title);
            logger.debug("findCurse by title Method used");
            courseDAO.close();
            return currentCourse;
        } catch (DAOException e) {
            logger.debug("Can't find Course with title " + title, e);
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
        }
        return null;
    }

    @Override
    public void deleteCourse(int course_id) {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            CourseDAO courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");
            courseDAO.deleteCourse(course_id);
            logger.debug("deleteCourse Method used");
            courseDAO.close();
        } catch (DAOException e) {
            logger.debug("Can't delete Course with ID" + course_id, e);
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
        }
    }

    @Override
    public void changeStatus(int courseID, String status) {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            CourseDAO courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");
            courseDAO.changeStatus(courseID, status);
            logger.debug("changeStatus Method used");
            courseDAO.close();
        } catch (DAOException e) {
            logger.debug("Can't change status of Course " + courseID, e);
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
        }
    }

    @Override
    public List<Course> showAllCourses() {
        List<Course> result;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            CourseDAO courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");
            result = courseDAO.showAllCourses();
            logger.debug("showAllCourses Method used");
            courseDAO.close();
            return result;
        } catch (DAOException e) {
            logger.debug("Can't show all courses", e);
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
        }
        return null;
    }
}
