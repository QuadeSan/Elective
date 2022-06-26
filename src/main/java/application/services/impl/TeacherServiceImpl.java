package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Student;
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
            return new OperationResult(false, "Something went wrong! Have no response from database");
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

            return new ValuedOperationResult<>(true, "Teacher found", currentTeacher);
        } catch (NotExistException e) {
            logger.error("Can't authorize as Teacher");
            return new ValuedOperationResult<>(false,
                    "Teacher with login " + login + " does not exist", null);
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
    public ValuedOperationResult<Teacher> findTeacher(String login) {
        Teacher currentTeacher;
        TeacherDAO teacherDAO = null;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            currentTeacher = teacherDAO.findTeacher(login);

            return new ValuedOperationResult<>(true, "Teacher found", currentTeacher);
        } catch (NotExistException e) {
            logger.error("Teacher with login " + login + "does not exist");
            return new ValuedOperationResult<>(false,
                    "Teacher with login " + login + " does not exist", null);
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
    public ValuedOperationResult<Teacher> findTeacher(int teacherId) {
        Teacher currentTeacher;
        TeacherDAO teacherDAO = null;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            currentTeacher = teacherDAO.findTeacher(teacherId);

            return new ValuedOperationResult<>(true, "Teacher found", currentTeacher);
        } catch (NotExistException e) {
            logger.error("Teacher with id " + teacherId + "does not exist");
            return new ValuedOperationResult<>(false,
                    "Teacher with id " + teacherId + " does not exist", null);
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
            logger.debug("StudentDAO created");

            result = teacherDAO.showAllTeachers();
            logger.debug("showAllStudents Method used");

            return new ValuedOperationResult<>(true, "List of students", result);
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

    @Override
    public OperationResult deleteAccount(int userId) {
        TeacherDAO teacherDAO = null;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            teacherDAO.deleteAccount(userId);
            return new OperationResult(true, "Account was deleted");
        } catch (DAOException e) {
            logger.error("Can't delete account", e);
            return new OperationResult(false, "Account was not deleted");
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
