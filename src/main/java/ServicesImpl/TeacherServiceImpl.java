package ServicesImpl;

import DataBaseLayer.DAO.DAOFactory;
import DataBaseLayer.DAO.StudentDAO;
import DataBaseLayer.DAO.TeacherDAO;
import DataBaseLayer.DAOimpl.TeacherDAOImpl;
import Services.TeacherService;
import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Journal;
import DataBaseLayer.entity.Student;
import DataBaseLayer.entity.Teacher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private static final Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

    @Override
    public void createTeacher(String login, String password, String email) {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            TeacherDAO teacherDAO = factory.getTeacherDAO();
            logger.debug("TeacherDAO created");
            teacherDAO.createTeacher(login, password, email);
            logger.debug("CreateTeacher Method used");
            teacherDAO.close();
        } catch (Exception e) {
            logger.debug("Can't close TeacherDAO", e);
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

    @Override
    public Journal showTeacherJournal() {
        return null;
    }
}
