package application.services.impl;

import application.OperationResult;
import application.PasswordHashing;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Student;
import application.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    private final DAOFactory daoFactory;

    public StudentServiceImpl() {
        this.daoFactory = DAOFactory.getInstance();
        logger.debug("DAOFactory created => " + daoFactory);
    }

    @Override
    public OperationResult createStudent(String login, String password, String email, String name, String lastName) {
        try (StudentDAO studentDAO = daoFactory.getStudentDAO()) {
            logger.debug("StudentDAO created");

            String strongPassword = PasswordHashing.createStrongPassword(password);
            logger.debug("Strong password created");

            studentDAO.createStudent(login, strongPassword, email, name, lastName);
            logger.debug("CreateStudent Method used");

            return new OperationResult(true, "Account was successfully created!");
        } catch (AlreadyExistException ex) {
            logger.error("Login already exist");
            return new OperationResult(false, "Login already exist");
        } catch (DAOException e) {
            logger.error("Can't create new Student", e);
            return new OperationResult(false, "Unhandled exception");
        } catch (Exception e) {
            logger.error("Can't close StudentDAO", e);
            return new OperationResult(false, "Unhandled exception");
        }
    }

    @Override
    public ValuedOperationResult<Student> authorizeStudent(String login, String password) {
        logger.debug("Start of authorization");
        try (StudentDAO studentDAO = daoFactory.getStudentDAO()) {
            logger.debug("StudentDAO created");

            Student currentStudent = studentDAO.findStudent(login);

            boolean match = PasswordHashing.validatePassword(password, currentStudent.getPassword());
            if (!match) {
                return new ValuedOperationResult<>(false, "Wrong password", null);
            }
            return new ValuedOperationResult<>(true, "You logged as Student", currentStudent);
        } catch (NotExistException e) {
            logger.error("Can't authorize as student");
            return new ValuedOperationResult<>(false,
                    "Student with login = " + login + " does not exist", null);
        } catch (DAOException e) {
            logger.error("Unhandled exception", e);
            return new ValuedOperationResult<>(false,
                    "Unhandled exception", null);
        } catch (Exception e) {
            logger.error("Can't close StudentDAO", e);
            return new ValuedOperationResult<>(false,
                    "Unhandled exception", null);
        }
    }

    @Override
    public OperationResult lockStudent(int studentId, String status) {
        try (StudentDAO studentDAO = daoFactory.getStudentDAO()) {
            logger.debug("StudentDAO created");

            studentDAO.changeStatus(studentId, status);
            logger.debug("Status was changed");
            return new OperationResult(true, "Student was " + status);
        } catch (NotExistException e) {
            logger.error("Student with ID = " + studentId + " does not exist");
            return new OperationResult(false, "Student with ID = " + studentId + " does not exist");
        } catch (DAOException e) {
            logger.error("Can't lock student", e);
            return new OperationResult(false, "Unhandled exception");
        } catch (Exception e) {
            logger.error("Can't close StudentDAO", e);
            return new OperationResult(false, "Unhandled exception");
        }
    }

    @Override
    public ValuedOperationResult<Iterable<Student>> showAllStudents() {
        try (StudentDAO studentDAO = daoFactory.getStudentDAO()) {
            logger.debug("StudentDAO created");

            Iterable<Student> result = studentDAO.showAllStudents();
            logger.debug("showAllStudents Method used");

            return new ValuedOperationResult<>(true, "List of students", result);
        } catch (DAOException e) {
            logger.error("Can't show all students", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        } catch (Exception e) {
            logger.error("Can't close StudentDAO", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        }
    }

    @Override
    public ValuedOperationResult<Iterable<Student>> showAllStudents(int offSet, int limit) {
        try (StudentDAO studentDAO = daoFactory.getStudentDAO()) {
            logger.debug("StudentDAO created");

            Iterable<Student> result = studentDAO.showAllStudents(offSet, limit);
            logger.debug("showAllStudents Method used");

            return new ValuedOperationResult<>(true, "List of students", result);
        } catch (DAOException e) {
            logger.error("Can't show all students", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        } catch (Exception e) {
            logger.error("Can't close StudentDAO", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        }
    }

    @Override
    public ValuedOperationResult<Integer> studentCount() {
        try (StudentDAO studentDAO = daoFactory.getStudentDAO()) {
            logger.debug("StudentDAO created");

            int result = studentDAO.studentCount();
            logger.debug("studentCount Method used");

            if (result == 0) {
                return new ValuedOperationResult<>(true, "There are no students yet", 0);
            }
            return new ValuedOperationResult<>(true, "Count of students", result);
        } catch (DAOException e) {
            logger.error("Can't count all students", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", -1);
        } catch (Exception e) {
            logger.error("Can't close StudentDAO", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", -1);
        }
    }

    @Override
    public ValuedOperationResult<Student> editAccount(int userId, String newLogin, String newEmail, String newPassword, String newName, String newLastName) {
        try (StudentDAO studentDAO = daoFactory.getStudentDAO()) {
            logger.debug("AdministratorDAO created");
            StringBuilder whatChanged = new StringBuilder();

            if (!newLogin.equals("")) {
                studentDAO.updateLogin(userId, newLogin);
                whatChanged.append("Login, ");
                logger.debug("updateLogin method used");
            }

            if(!newEmail.equals("")) {
                studentDAO.updateEmail(userId,newEmail);
                whatChanged.append("Email, ");
                logger.debug("updateEmail method used");
            }

            if(!newPassword.equals("")){
                String newStrongPassword = PasswordHashing.createStrongPassword(newPassword);
                studentDAO.updatePassword(userId,newStrongPassword);
                whatChanged.append("Password, ");
                logger.debug("updatePassword method used");
            }

            if(!newName.equals("")){
                studentDAO.updateName(userId,newName);
                whatChanged.append("Name, ");
                logger.debug("updateName method used");
            }

            if(!newLastName.equals("")){
                studentDAO.updateLastName(userId,newLastName);
                whatChanged.append("Last name, ");
                logger.debug("updateLastName method used");
            }
            whatChanged.append("was changed");

            Student currentStudent = studentDAO.findStudent(userId);

            return new ValuedOperationResult<>(true, whatChanged.toString(), currentStudent);
        } catch (AlreadyExistException e) {
            logger.error("Login already exist", e);
            return new ValuedOperationResult<>(false, "Login already exist",null);
        } catch (DAOException e) {
            logger.error("Can't edit account", e);
            return new ValuedOperationResult<>(false, "Account information was not changed",null);
        } catch (Exception e) {
            logger.error("Can't close StudentDAO", e);
            return new ValuedOperationResult<>(false, "Unhandled exception",null);
        }
    }
}
