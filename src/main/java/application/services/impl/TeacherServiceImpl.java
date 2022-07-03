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
        this.daoFactory = DAOFactory.getInstance();
        logger.debug("DAOFactory created => " + daoFactory);
    }

    @Override
    public OperationResult createTeacher(String login, String password, String email, String name, String lastName) {
        try (TeacherDAO teacherDAO = daoFactory.getTeacherDAO()) {
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
        } catch (Exception e) {
            logger.error("Can't close TeacherDAO");
            return new OperationResult(false, "Unhandled exception");
        }
    }

    @Override
    public ValuedOperationResult<Teacher> findTeacher(String login, String password) {
        logger.debug("Start of authorization");
        try (TeacherDAO teacherDAO = daoFactory.getTeacherDAO()) {
            logger.debug("TeacherDAO created");

            Teacher currentTeacher = teacherDAO.findTeacher(login, password);

            return new ValuedOperationResult<>(true, "You logged as Teacher", currentTeacher);
        } catch (NotExistException e) {
            logger.error("Can't authorize as Teacher");
            return new ValuedOperationResult<>(false,
                    "Teacher with login = " + login + " does not exist", null);
        } catch (DAOException e) {
            logger.error("Unhandled exception", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        } catch (Exception e) {
            logger.error("Can't close TeacherDAO");
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        }
    }

    @Override
    public ValuedOperationResult<Iterable<Teacher>> showAllTeachers() {
        try (TeacherDAO teacherDAO = daoFactory.getTeacherDAO()) {
            logger.debug("TeacherDAO created");

            Iterable<Teacher> result = teacherDAO.showAllTeachers();
            logger.debug("showAllTeachers Method used");

            return new ValuedOperationResult<>(true, "List of teachers", result);
        } catch (DAOException e) {
            logger.error("Can't show all teachers", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        } catch (Exception e) {
            logger.error("Can't close TeacherDAO");
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        }
    }
}
