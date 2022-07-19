package application.services.impl;

import application.OperationResult;
import application.PasswordHashing;
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

            String strongPassword = PasswordHashing.createStrongPassword(password);
            logger.debug("Strong password created");

            teacherDAO.createTeacher(login, strongPassword, email, name, lastName);
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
    public ValuedOperationResult<Teacher> authorizeTeacher(String login, String password) {
        logger.debug("Start of authorization");
        try (TeacherDAO teacherDAO = daoFactory.getTeacherDAO()) {
            logger.debug("TeacherDAO created");

            Teacher currentTeacher = teacherDAO.findTeacher(login);

            boolean match = PasswordHashing.validatePassword(password, currentTeacher.getPassword());
            if (!match) {
                return new ValuedOperationResult<>(false, "Wrong password", null);
            }
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

    @Override
    public ValuedOperationResult<Teacher> editAccount(int userId, String newLogin, String newEmail, String newPassword, String newName, String newLastName) {
        try (TeacherDAO teacherDAO = daoFactory.getTeacherDAO()) {
            logger.debug("AdministratorDAO created");
            StringBuilder whatChanged = new StringBuilder();

            if (!newLogin.equals("")) {
                teacherDAO.updateLogin(userId, newLogin);
                whatChanged.append("Login, ");
                logger.debug("updateLogin method used");
            }

            if (!newEmail.equals("")) {
                teacherDAO.updateEmail(userId, newEmail);
                whatChanged.append("Email, ");
                logger.debug("updateEmail method used");
            }

            if (!newPassword.equals("")) {
                String newStrongPassword = PasswordHashing.createStrongPassword(newPassword);
                teacherDAO.updatePassword(userId, newStrongPassword);
                whatChanged.append("Password, ");
                logger.debug("updatePassword method used");
            }

            if (!newName.equals("")) {
                teacherDAO.updateName(userId, newName);
                whatChanged.append("Name, ");
                logger.debug("updateName method used");
            }

            if (!newLastName.equals("")) {
                teacherDAO.updateLastName(userId, newLastName);
                whatChanged.append("Last name, ");
                logger.debug("updateLastName method used");
            }
            whatChanged.append("was changed");

            Teacher currentTeacher = teacherDAO.findTeacher(userId);

            return new ValuedOperationResult<>(true, whatChanged.toString(), currentTeacher);
        } catch (AlreadyExistException e) {
            logger.error("Login already exist", e);
            return new ValuedOperationResult<>(false, "Login already exist", null);
        } catch (DAOException e) {
            logger.error("Can't edit account", e);
            return new ValuedOperationResult<>(false, "Account information was not changed", null);
        } catch (Exception e) {
            logger.error("Can't close StudentDAO", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        }
    }

}
