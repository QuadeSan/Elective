package application.services.impl;

import application.OperationRes;
import application.OperationResult;
import application.dao.AlreadyExistException;
import application.dao.NotExistException;
import application.dao.DAOFactory;
import application.dao.StudentDAO;
import application.dao.DAOException;
import application.entity.Student;
import application.services.StudentService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;

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
            return new OperationResult(false, "Something went wrong! Have no response from database");
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO",e);
            }
        }
    }

    @Override
    public Student findStudent(String login) {
        Student currentStudent;
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            currentStudent = studentDAO.findStudent(login);
            logger.debug("findStudent Method used");


            return currentStudent;
        } catch (DAOException e) {
            logger.error("Can't find Student by login", e);
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO", e);
            }
        }
        return new Student();
    }

    @Override
    public Student findStudent(String login, String password) {
        logger.debug("Start of authorization");
        Student currentStudent;
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            currentStudent = studentDAO.findStudent(login, password);
            return currentStudent;
        } catch (DAOException e) {
            logger.error("Can't authorize as student", e);
            return new Student();
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.error("Can't close StudentDAO");
        }
    }

    @Override
    public OperationRes<Student> findStudent(int studentId) {
        Student currentStudent;
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            currentStudent = studentDAO.findStudent(studentId);

            return new OperationRes<>(true,"Student was found",currentStudent);
        } catch (DAOException e) {
            logger.error("Can't find Student by ID", e);
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.error("Can't close StudentDAO");
        }
        return null;
    }

    @Override
    public void deleteAccount(int userId) {
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            studentDAO.deleteAccount(userId);
        } catch (DAOException e) {
            logger.error("Can't delete account", e);
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.error("Can't close StudentDAO");
        }
    }

    @Override
    public OperationResult lockStudent(int studentId, String status) {
        if (!status.equalsIgnoreCase("locked") &&
                !status.equalsIgnoreCase("unlocked")) {
            logger.info("Wrong status");
            return new OperationResult(false,"Something went wrong! " +
                    "Status is \"" + status + "\" but can be \"locked\" or \"unlocked\" only!");
        }
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            studentDAO.changeStatus(studentId, status);
            return new OperationResult(true, "Student was " + status);
        } catch (NotExistException e) {
            logger.error("Student with id = " + studentId + " does not exist");
            return new OperationResult(false, "Student with id = " + studentId + " does not exist");
        } catch (DAOException e) {
            logger.error("Can't lock student", e);
            return new OperationResult(false, "Can't connect to database");
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.error("Can't close StudentDAO");
        }
    }

    @Override
    public Iterable<Student> showAllStudents() {
        StudentDAO studentDAO = null;
        Iterable<Student> result = new ArrayList<>();
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            result = studentDAO.showAllStudents();
            logger.debug("showAllStudents Method used");

            return result;
        } catch (DAOException e) {
            logger.error("Can't show all students", e);
            return result;
        } finally {
            try {
                if (studentDAO != null) {
                    studentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO");
            }
        }
    }

    @Override
    public void editStudent(int id, String... params) {

    }

}
