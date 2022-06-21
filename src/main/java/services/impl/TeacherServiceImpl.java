package services.impl;

import controller.Response;
import dataBaseLayer.AlreadyExistException;
import dataBaseLayer.dao.DAOFactory;
import dataBaseLayer.dao.TeacherDAO;
import dataBaseLayer.DAOException;
import dataBaseLayer.entity.Course;
import dataBaseLayer.entity.Student;
import dataBaseLayer.entity.Teacher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.TeacherService;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private static final Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

    private final DAOFactory daoFactory;

    public TeacherServiceImpl() {
        this(DAOFactory.getInstance());
    }

    public TeacherServiceImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        logger.debug("DAOFactory created => " + daoFactory);
    }

    @Override
    public Response createTeacher(String login, String password, String email, String name, String lastName) {
        TeacherDAO teacherDAO = null;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            teacherDAO.createTeacher(login, password, email, name, lastName);
            logger.debug("CreateTeacher Method used");

            return new Response(true, "Account was successfully created!");
        } catch (AlreadyExistException ex) {
            logger.error("Login already exist");
            return new Response(false, "Login already exist");
        } catch (DAOException e) {
            logger.error("Can't create new teacher", e);
            return new Response(false, "Something went wrong! Have no response from database");
        } finally {
            try {
                if (teacherDAO != null) {
                    teacherDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
            }
        }
    }

    @Override
    public Teacher findTeacher(int teacherId) {
        Teacher currentTeacher;
        TeacherDAO teacherDAO = null;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            currentTeacher = teacherDAO.findTeacher(teacherId);

            return currentTeacher;
        } catch (DAOException e) {
            logger.error("Can't find teacher by ID", e);
            return new Teacher();
        } finally {
            try {
                if (teacherDAO != null) {
                    teacherDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
            }
        }
    }

    @Override
    public Teacher findTeacher(String login) {
        Teacher currentTeacher;
        TeacherDAO teacherDAO = null;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            currentTeacher = teacherDAO.findTeacher(login);

            return currentTeacher;
        } catch (DAOException e) {
            logger.error("Can't find teacher by login", e);
            return new Teacher();
        } finally {
            try {
                if (teacherDAO != null) {
                    teacherDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
            }
        }
    }

    @Override
    public Teacher findTeacher(String login, String password) {
        logger.debug("Start of authorization");
        Teacher currentTeacher;
        TeacherDAO teacherDAO = null;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            currentTeacher = teacherDAO.findTeacher(login, password);

            return currentTeacher;
        } catch (DAOException e) {
            logger.error("Can't authorize as teacher", e);
            return new Teacher();
        } finally {
            try {
                if (teacherDAO != null) {
                    teacherDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
            }
        }
    }

    @Override
    public void deleteAccount(int userId) {
        TeacherDAO teacherDAO = null;
        try {
            teacherDAO = daoFactory.getTeacherDAO();
            logger.debug("TeacherDAO created");

            teacherDAO.deleteAccount(userId);
        } catch (DAOException e) {
            logger.error("Can't delete account", e);
        } finally {
            try {
                if (teacherDAO != null) {
                    teacherDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close TeacherDAO");
            }
        }
    }
}
