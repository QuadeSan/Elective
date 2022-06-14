package ServicesImpl;

import DataBaseLayer.AssignmentException;
import DataBaseLayer.DAO.AssignmentDAO;
import DataBaseLayer.DAO.DAOFactory;
import DataBaseLayer.QueryResult;
import Services.AssignmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        } catch (Exception e) {
            logger.debug("Can't close AssignmentDAO", e);
        }
    }
}
