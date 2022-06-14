package DataBaseLayer.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySQLDAOFactory extends DAOFactory {

    private static final Logger logger = LogManager.getLogger(MySQLStudentDAO.class);

    @Override
    public AdministratorDAO getAdministratorDAO() {
        logger.debug("MySQLAdministratorDAO created");
        return new MySQLAdministratorDAO();
    }

    @Override
    public AssignmentDAO getAssigmentDAO() {
        return new MySQLAssignmentDAO();
    }

    @Override
    public CourseDAO getCourseDAO() {
        return new MySQLCourseDAO();
    }

    @Override
    public StudentDAO getStudentDAO() {
        logger.debug("MySQLStudentDAO created");
        return new MySQLStudentDAO();
    }

    @Override
    public TeacherDAO getTeacherDAO() {
        return new MySQLTeacherDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new MySQLUserDAO();
    }
}
