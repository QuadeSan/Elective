package ServicesImpl;

import DataBaseLayer.AssignmentException;
import DataBaseLayer.DAO.AssignmentDAO;
import DataBaseLayer.DAO.DAOFactory;
import DataBaseLayer.DAOException;
import DataBaseLayer.QueryResult;
import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Student;
import Services.AssignmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AssignmentServiceImpl implements AssignmentService {

    private static final Logger logger = LogManager.getLogger(AssignmentServiceImpl.class);

    @Override
    public QueryResult assignTeacherToCourse(int course_id, int teacher_id) {
        QueryResult result = new QueryResult();
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AssignmentDAO assignmentDAO = factory.getAssigmentDAO();
            logger.debug("AssignmentDAO created");
            assignmentDAO.assignTeacherToCourse(course_id, teacher_id);
            assignmentDAO.close();
            return result;
        } catch (AssignmentException ex) {
            logger.info("Assignment wasn't built");
            result.addException(ex);
            return result;
        } catch (Exception e) {
            logger.debug("Can't close AssignmentDAO", e);
            result.addException(e);
            return result;
        }
    }

    @Override
    public void unassignTeacherFromCourse(int course_id, int teacher_id) {

    }

    @Override
    public QueryResult assignStudentToCourse(int course_id, int student_id) {
        QueryResult result = new QueryResult();
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AssignmentDAO assignmentDAO = factory.getAssigmentDAO();
            logger.debug("AssignmentDAO created");
            assignmentDAO.assignStudentToCourse(course_id, student_id);
            assignmentDAO.close();
            return result;
        } catch (AssignmentException ex) {
            logger.info("Assignment wasn't built");
            result.addException(ex);
            return result;
        } catch (Exception e) {
            logger.debug("Can't close AssignmentDAO", e);
            result.addException(e);
            return result;
        }
    }


    @Override
    public void unassignStudentFromCourse(int course_id, int student_id) {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AssignmentDAO assignmentDAO = factory.getAssigmentDAO();
            logger.debug("AssignmentDAO created");
            assignmentDAO.unassignStudentFromCourse(course_id, student_id);
            assignmentDAO.close();
        } catch (DAOException e) {
            logger.debug("Can't cancel assignment of Student "
                    + student_id + "from course " + course_id, e);
        } catch (Exception e) {
            logger.debug("Can't close AssignmentDAO", e);
        }
    }

    @Override
    public List<Course> showTeacherCourses(int teacherID) {
        List<Course> result = new ArrayList<>();
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AssignmentDAO assignmentDAO = factory.getAssigmentDAO();
            logger.debug("AssignmentDAO created");
            result = assignmentDAO.showTeacherCourses(teacherID);
            logger.debug("showTeacherCourses Method used");
            assignmentDAO.close();
            return result;
        } catch (DAOException e) {
            logger.debug("Can't show all courses", e);
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
        }
        return result;
    }

    @Override
    public List<Course> showStudentCourses(int studentID) {
        List<Course> result = new ArrayList<>();
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AssignmentDAO assignmentDAO = factory.getAssigmentDAO();
            logger.debug("AssignmentDAO created");
            result = assignmentDAO.showStudentCourses(studentID);
            logger.debug("showStudentCourses Method used");
            assignmentDAO.close();
            return result;
        } catch (DAOException e) {
            logger.debug("Can't show all courses", e);
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
        }
        return result;
    }

    @Override
    public List<Student> showStudentsOnCourse(int courseID) {
        List<Student> result = new ArrayList<>();
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AssignmentDAO assignmentDAO = factory.getAssigmentDAO();
            logger.debug("AssignmentDAO created");
            result = assignmentDAO.showStudentsOnCourse(courseID);
            logger.debug("showStudentsOnCourses Method used");
            assignmentDAO.close();
            return result;
        } catch (DAOException e) {
            logger.debug("Can't show all courses", e);
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
        }
        return result;
    }

    @Override
    public void setMarkForStudent(int courseID, int studentID, int mark) {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AssignmentDAO assignmentDAO = factory.getAssigmentDAO();
            logger.debug("AssignmentDAO created");
            assignmentDAO.setMarkForStudent(courseID,studentID,mark);
            logger.debug("setMarkForStudent Method used");
            assignmentDAO.close();
        } catch (DAOException e) {
            logger.debug("Can't set mark for current student", e);
        } catch (Exception e) {
            logger.debug("Can't close CourseDAO", e);
        }
    }
}
