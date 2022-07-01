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
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
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
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO", e);
            }
        }
    }

    @Override
    public ValuedOperationResult<Student> findStudent(String login, String password) {
        logger.debug("Start of authorization");
        Student currentStudent;
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            currentStudent = studentDAO.findStudent(login, password);

            return new ValuedOperationResult<>(true, "You logged as Student", currentStudent);
        } catch (NotExistException e) {
            logger.error("Can't authorize as student");
            return new ValuedOperationResult<>(false,
                    "Student with login = " + login + " does not exist", null);
        } catch (DAOException e) {
            logger.error("Unhandled exception", e);
            return new ValuedOperationResult<>(false,
                    "Unhandled exception", null);
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO", e);
            }
            logger.debug("StudentDAO was closed");
        }
    }

    @Override
    public OperationResult lockStudent(int studentId, String status) {
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
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
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO");
            }
            logger.debug("StudentDAO was closed");
        }
    }

    @Override
    public ValuedOperationResult<Iterable<Student>> showAllStudents() {
        StudentDAO studentDAO = null;
        Iterable<Student> result;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            result = studentDAO.showAllStudents();
            logger.debug("showAllStudents Method used");

            return new ValuedOperationResult<>(true, "List of students", result);
        } catch (DAOException e) {
            logger.error("Can't show all students", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO");
            }
            logger.debug("StudentDAO was closed");
        }
    }

    @Override
    public ValuedOperationResult<Iterable<Student>> showAllStudents(int offSet, int limit) {
        StudentDAO studentDAO = null;
        Iterable<Student> result;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            result = studentDAO.showAllStudents(offSet, limit);
            logger.debug("showAllStudents Method used");

            return new ValuedOperationResult<>(true, "List of students", result);
        } catch (DAOException e) {
            logger.error("Can't show all students", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO");
            }
            logger.debug("StudentDAO was closed");
        }
    }

    @Override
    public ValuedOperationResult<Integer> studentCount() {
        StudentDAO studentDAO = null;
        int result;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            result = studentDAO.studentCount();
            logger.debug("studentCount Method used");

            if (result == 0) {
                return new ValuedOperationResult<>(true, "There are no students yet", 0);
            }
            return new ValuedOperationResult<>(true, "Count of students", result);
        } catch (DAOException e) {
            logger.error("Can't count all students", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", -1);
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO");
            }
            logger.debug("StudentDAO was closed");
        }
    }
}
