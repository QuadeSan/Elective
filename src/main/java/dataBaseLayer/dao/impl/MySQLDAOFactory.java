package dataBaseLayer.dao.impl;

import dataBaseLayer.dao.*;
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
    public AssignmentDAO getAssignmentDAO() {
        logger.debug("MySQLAssignmentDAO created");
        return new MySQLAssignmentDAO();
    }

    @Override
    public CourseDAO getCourseDAO() {
        logger.debug("MySQLCourseDAO created");
        return new MySQLCourseDAO();
    }

    @Override
    public StudentDAO getStudentDAO() {
        logger.debug("MySQLStudentDAO created");
        return new MySQLStudentDAO();
    }

    @Override
    public TeacherDAO getTeacherDAO() {
        logger.debug("MySQLTeacherDAO created");
        return new MySQLTeacherDAO();
    }
}
