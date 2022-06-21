package services.impl;

import controller.Response;
import dataBaseLayer.AlreadyExistException;
import dataBaseLayer.NotExistException;
import dataBaseLayer.dao.DAOFactory;
import dataBaseLayer.dao.StudentDAO;
import dataBaseLayer.DAOException;
import dataBaseLayer.entity.Course;
import dataBaseLayer.entity.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.StudentService;

import java.util.List;

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
    public Response createStudent(String login, String password, String email, String name, String lastName) {
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            studentDAO.createStudent(login, password, email, name, lastName);
            logger.debug("CreateStudent Method used");


            return new Response(true, "Account was successfully created!");
        } catch (AlreadyExistException ex) {
            logger.error("Login already exist");
            return new Response(false, "Login already exist");
        } catch (DAOException e) {
            logger.error("Can't create new Student", e);
            return new Response(false, "Something went wrong! Have no response from database");
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
    public Student findStudent(int studentId) {
        Student currentStudent;
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            currentStudent = studentDAO.findStudent(studentId);

            return currentStudent;
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
    public Response lockStudent(int studentId, String status) {
        if (!status.equalsIgnoreCase("locked") &&
                !status.equalsIgnoreCase("unlocked")) {
            logger.info("Wrong status");
            return new Response(false,"Something went wrong! " +
                    "Status is \"" + status + "\" but can be \"locked\" or \"unlocked\" only!");
        }
        StudentDAO studentDAO = null;
        try {
            studentDAO = daoFactory.getStudentDAO();
            logger.debug("StudentDAO created");

            studentDAO.changeStatus(studentId, status);
            return new Response(true, "Student was " + status);
        } catch (NotExistException e) {
            logger.error("Student with id = " + studentId + " does not exist");
            return new Response(false, "Student with id = " + studentId + " does not exist");
        } catch (DAOException e) {
            logger.error("Can't lock student", e);
            return new Response(false, "Can't connect to database");
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
    public void editStudent(int id, String... params) {

    }

    @Override
    public List<Student> showAllStudents() {
        return null;
    }
}
