package ServicesImpl;

import DataBaseLayer.AlreadyExistException;
import DataBaseLayer.AssignmentException;
import DataBaseLayer.DAO.DAOFactory;
import DataBaseLayer.DAO.MySQLStudentDAO;
import DataBaseLayer.DAO.StudentDAO;
import DataBaseLayer.DAOException;
import DataBaseLayer.DAOimpl.StudentDAOimpl;
import DataBaseLayer.QueryResult;
import Services.StudentService;
import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @Override
    public void assignToCourse(Student student, Course course) {
        student.addCourse(course);
        course.addStudent(student);
    }

    @Override
    public void dropCourse(Student student, Course course) {
        student.dropCourse(course);
        course.removeStudent(student);
    }


    @Override
    public QueryResult createStudent(String login, String password, String email) {
        QueryResult result = new QueryResult();
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            StudentDAO studentDAO = factory.getStudentDAO();
            logger.debug("StudentDAO created");
            studentDAO.createStudent(login, password, email);
            logger.debug("CreateStudent Method used");
            studentDAO.close();
            return result;
        } catch (AlreadyExistException ex) {
            logger.info("Login already exist");
            result.addException(ex);
            return result;
        } catch (DAOException e) {
            logger.debug("Can't create new Student", e);
            return null;
        } catch (Exception e) {
            logger.debug("Can't close StudentDAO", e);
            return null;
        }
    }

    @Override
    public Student findStudent(String login) {
        Student currentStudent;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            StudentDAO studentDAO = factory.getStudentDAO();
            logger.debug("StudentDAO created");
            currentStudent = studentDAO.findStudent(login);
            studentDAO.close();
            return currentStudent;
        } catch (Exception e) {
            logger.error("Can't close StudentDAO", e);
        }
        return null;
    }

    @Override
    public Student findStudent(String login, String password) {
        logger.debug("Start of authorization");
        Student currentStudent;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            StudentDAO studentDAO = factory.getStudentDAO();
            logger.debug("StudentDAO created");
            currentStudent = studentDAO.findStudent(login, password);
            studentDAO.close();
            return currentStudent;
        } catch (Exception e) {
            logger.error("Can't close StudentDAO", e);
        }
        return null;
    }

    @Override
    public Student findStudent(int student_id) {
        Student currentStudent;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            StudentDAO studentDAO = factory.getStudentDAO();
            logger.debug("StudentDAO created");
            currentStudent = studentDAO.findStudent(student_id);
            studentDAO.close();
            return currentStudent;
        } catch (Exception e) {
            logger.debug("Can't close StudentDAO", e);
        }
        return null;
    }

    @Override
    public void deleteAccount(int user_id) {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            StudentDAO studentDAO = factory.getStudentDAO();
            logger.debug("StudentDAO created");
            studentDAO.deleteAccount(user_id);
            studentDAO.close();
        } catch (Exception e) {
            logger.debug("Can't close StudentDAO", e);
        }
    }

    @Override
    public void lockStudent(int student_id, String status) {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            StudentDAO studentDAO = factory.getStudentDAO();
            logger.debug("StudentDAO created");
            studentDAO.changeStatus(student_id, status);
            studentDAO.close();
        } catch (DAOException e) {
            logger.debug("Can't lock student", e);
        } catch (Exception e) {
            logger.debug("Can't close StudentDAO", e);
        }
    }

    @Override
    public List<Course> showAssignedCourses(Student student) {
        return student.getCourses();
    }

    @Override
    public void editStudent(int id, String... params) {

    }

    @Override
    public List<Student> showAllStudents() {
        return null;
    }
}
