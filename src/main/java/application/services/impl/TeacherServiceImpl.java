package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Teacher;
import application.services.TeacherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TeacherServiceImpl implements TeacherService {

    private static final Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

    private final DAOFactory daoFactory;

    public TeacherServiceImpl() {
        this(DAOFactory.getInstance());
        logger.debug("DAOFactory created => " + daoFactory);
    }

    public TeacherServiceImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public OperationResult createTeacher(String login, String password, String email, String name, String lastName) {
        TeacherDAO teacherDAO = null;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            teacherDAO.createTeacher(login, password, email, name, lastName);
            logger.debug("CreateTeacher Method used");

            return new OperationResult(true, "Account was successfully created!");
        } catch (AlreadyExistException ex) {
            logger.error("Login already exist");
            return new OperationResult(false, "Login already exist");
        } catch (DAOException e) {
            logger.error("Can't create new teacher", e);
            return new OperationResult(false, "Unhandled exception");
        } finally {
            try {
                if (teacherDAO != null) {
                    teacherDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
            }
            logger.debug("TeacherDAO was closed");
        }
    }

    @Override
    public ValuedOperationResult<Teacher> findTeacher(String login, String password) {
        logger.debug("Start of authorization");
        Teacher currentTeacher;
        TeacherDAO teacherDAO = null;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            currentTeacher = teacherDAO.findTeacher(login, password);

            return new ValuedOperationResult<>(true, "You logged as Teacher", currentTeacher);
        } catch (NotExistException e) {
            logger.error("Can't authorize as Teacher");
            return new ValuedOperationResult<>(false,
                    "Teacher with login = " + login + " does not exist", null);
        } catch (DAOException e) {
            logger.error("Unhandled exception", e);
            return new ValuedOperationResult<>(false,
                    "Unhandled exception", null);
        } finally {
            try {
                if (teacherDAO != null) {
                    teacherDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
            }
            logger.debug("TeacherDAO was closed");
        }
    }

    @Override
    public ValuedOperationResult<Iterable<Teacher>> showAllTeachers() {
        TeacherDAO teacherDAO = null;
        Iterable<Teacher> result;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            result = teacherDAO.showAllTeachers();
            logger.debug("showAllTeachers Method used");

            return new ValuedOperationResult<>(true, "List of teachers", result);
        } catch (DAOException e) {
            logger.error("Can't show all teachers", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        } finally {
            try {
                if (teacherDAO != null) {
                    teacherDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
            }
            logger.debug("TeacherDAO was closed");
        }
    }
}
