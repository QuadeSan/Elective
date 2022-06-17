package ServicesImpl;

import DataBaseLayer.AlreadyExistException;
import DataBaseLayer.DAO.DAOFactory;
import DataBaseLayer.DAO.TeacherDAO;
import DataBaseLayer.DAOException;
import DataBaseLayer.QueryResult;
import Services.TeacherService;
import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Student;
import DataBaseLayer.entity.Teacher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private static final Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

    @Override
    public QueryResult createTeacher(String login, String password, String email, String name, String lastName) {
        QueryResult result = new QueryResult();
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            TeacherDAO teacherDAO = factory.getTeacherDAO();
            logger.debug("TeacherDAO created");
            teacherDAO.createTeacher(login, password, email, name,lastName);
            logger.debug("CreateTeacher Method used");
            teacherDAO.close();
            return result;
        } catch (AlreadyExistException ex) {
            logger.info("Login already exist");
            result.addException(ex);
            return result;
        } catch (DAOException e) {
            logger.debug("Can't create new teacher", e);
            return null;
        } catch (Exception e) {
            logger.debug("Can't close TeacherDAO", e);
            return null;
        }
    }

    @Override
    public Teacher findTeacher(int teacher_id) {
        Teacher currentTeacher;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            TeacherDAO teacherDAO = factory.getTeacherDAO();
            logger.debug("TeacherDAO created");
            currentTeacher = teacherDAO.findTeacher(teacher_id);
            teacherDAO.close();
            return currentTeacher;
        } catch (DAOException e) {
            logger.error("Can't find teacher by ID", e);
        } catch (Exception e) {
            logger.error("Can't close TeacherDAO", e);
        }
        return null;
    }

    @Override
    public Teacher findTeacher(String login) {
        Teacher currentTeacher;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            TeacherDAO teacherDAO = factory.getTeacherDAO();
            logger.debug("TeacherDAO created");
            currentTeacher = teacherDAO.findTeacher(login);
            teacherDAO.close();
            return currentTeacher;
        } catch (DAOException e) {
            logger.error("Can't find teacher by login", e);
        } catch (Exception e) {
            logger.error("Can't close TeacherDAO", e);
        }
        return null;
    }

    @Override
    public Teacher findTeacher(String login, String password) {
        logger.debug("Start of authorization");
        Teacher currentTeacher;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            TeacherDAO teacherDAO = factory.getTeacherDAO();
            logger.debug("TeacherDAO created");
            currentTeacher = teacherDAO.findTeacher(login, password);
            teacherDAO.close();
            return currentTeacher;
        } catch (DAOException e) {
            logger.error("Can't authorize as teacher", e);
        } catch (Exception e) {
            logger.error("Can't close TeacherDAO", e);
        }
        return null;
    }

    @Override
    public void deleteAccount(int user_id) {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            TeacherDAO teacherDAO = factory.getTeacherDAO();
            logger.debug("TeacherDAO created");
            teacherDAO.deleteAccount(user_id);
            teacherDAO.close();
        } catch (DAOException e) {
            logger.debug("Can't delete account", e);
        } catch (Exception e) {
            logger.debug("Can't close TeacherDAO", e);
        }
    }

    @Override
    public List<Course> showAssignedCourses(Teacher teacher) {
        return null;
    }

    @Override
    public void setMarkForCourse(Student student, Course course) {

    }
}
