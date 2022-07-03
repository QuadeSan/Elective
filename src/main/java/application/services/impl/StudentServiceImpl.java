package application.services.impl;

import application.OperationResult;
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
        this(DAOFactory.getInstance());
    }

    public StudentServiceImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        logger.debug("DAOFactory created => " + daoFactory);
    }

    @Override
    public OperationResult createStudent(String login, String password, String email, String name, String lastName) {
        try (StudentDAO studentDAO = daoFactory.getStudentDAO()) {
            logger.debug("StudentDAO created");

            studentDAO.createStudent(login, password, email, name, lastName);
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
    public ValuedOperationResult<Student> findStudent(String login, String password) {
        logger.debug("Start of authorization");
        try (StudentDAO studentDAO = daoFactory.getStudentDAO()) {
            logger.debug("StudentDAO created");

            Student currentStudent = studentDAO.findStudent(login, password);

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
}
