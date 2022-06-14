package ServicesImpl;

import DataBaseLayer.DAO.CourseDAO;
import DataBaseLayer.DAO.DAOFactory;
import DataBaseLayer.entity.Course;
import Services.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LogManager.getLogger(CourseServiceImpl.class);

    @Override
    public void createCourse(String title) {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            CourseDAO courseDAO = factory.getCourseDAO();
            logger.debug("CourseDAO created");
            courseDAO.createCourse(title);
            logger.debug("createCourse Method used");
            courseDAO.close();
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
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
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
        }
        return null;
    }
}
