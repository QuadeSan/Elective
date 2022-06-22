package application.services.impl;

import application.dao.AlreadyExistException;
import application.dao.AssignmentDAO;
import application.dao.DAOFactory;
import application.dao.DAOException;
import application.OperationResult;
import application.entity.Course;
import application.entity.Student;
import application.services.AssignmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AssignmentServiceImpl implements AssignmentService {

    private static final Logger logger = LogManager.getLogger(AssignmentServiceImpl.class);

    private final DAOFactory daoFactory;

    public AssignmentServiceImpl() {
        this(DAOFactory.getInstance());
    }

    public AssignmentServiceImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        logger.debug("DAOFactory created => " + daoFactory);
    }

    @Override
    public OperationResult assignTeacherToCourse(int courseId, int teacherId) {
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            assignmentDAO.assignTeacherToCourse(courseId, teacherId);

            logger.info("Teacher was assigned to a course");
            return new OperationResult(true, "Teacher was assigned to course");
        } catch (AlreadyExistException ex) {
            logger.error("Assignment wasn't built");
            return new OperationResult(false, "Teacher already assigned to this course");
        } finally {
            try {
                if (assignmentDAO != null) {
                    assignmentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AssignmentDAO");
            }
        }
    }

    @Override
    public void unassignTeacherFromCourse(int courseId, int teacherId) {
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            assignmentDAO.unassignStudentFromCourse(courseId, teacherId);

        } catch (DAOException e) {
            logger.error("Can't cancel assignment of Teacher "
                    + teacherId + "from course " + courseId, e);
        } finally {
            try {
                if (assignmentDAO != null) {
                    assignmentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AssignmentDAO");
            }
        }
    }

    @Override
    public OperationResult assignStudentToCourse(int courseId, int studentId) {
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            assignmentDAO.assignStudentToCourse(courseId, studentId);

            logger.info("Student " + studentId + " was assigned to course" + courseId);
            return new OperationResult(true, "You was assigned to course " + courseId);
        } catch (AlreadyExistException ex) {
            logger.error("Assignment wasn't built");
            return new OperationResult(false, "You have been already assigned to this course");
        } finally {
            try {
                if (assignmentDAO != null) {
                    assignmentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AssignmentDAO");
            }
        }
    }


    @Override
    public void unassignStudentFromCourse(int courseId, int studentId) {
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            assignmentDAO.unassignStudentFromCourse(courseId, studentId);

        } catch (DAOException e) {
            logger.error("Can't cancel assignment of Student "
                    + studentId + "from course " + courseId, e);
        } finally {
            try {
                if (assignmentDAO != null) {
                    assignmentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AssignmentDAO");
            }
        }
    }

    @Override
    public Iterable<Course> showTeacherCourses(int teacherId) {
        Iterable<Course> result = new ArrayList<>();
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");
            result = assignmentDAO.showTeacherCourses(teacherId);
            logger.debug("showTeacherCourses Method used");

            return result;
        } catch (DAOException e) {
            logger.error("Can't show all courses", e);
            return result;
        } finally {
            try {
                if (assignmentDAO != null) {
                    assignmentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AssignmentDAO");
            }
        }
    }

    @Override
    public Iterable<Course> showStudentCourses(int studentId) {
        Iterable<Course> result = new ArrayList<>();
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            result = assignmentDAO.showStudentCourses(studentId);
            logger.debug("showStudentCourses Method used");

            return result;
        } catch (DAOException e) {
            logger.error("Can't show all courses", e);
            return result;
        } finally {
            try {
                if (assignmentDAO != null) {
                    assignmentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AssignmentDAO");
            }
        }
    }

    @Override
    public Iterable<Student> showStudentsOnCourse(int courseId) {
        Iterable<Student> result = new ArrayList<>();
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            result = assignmentDAO.showStudentsOnCourse(courseId);
            logger.debug("showStudentsOnCourses Method used");

            return result;
        } catch (DAOException e) {
            logger.error("Can't show all courses", e);
            return result;
        } finally {
            try {
                if (assignmentDAO != null) {
                    assignmentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AssignmentDAO");
            }
        }
    }

    @Override
    public void setMarkForStudent(int courseID, int studentId, int mark) {
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            assignmentDAO.setMarkForStudent(courseID, studentId, mark);
            logger.debug("setMarkForStudent Method used");
        } catch (DAOException e) {
            logger.error("Can't set mark for current student", e);
        } finally {
            try {
                if (assignmentDAO != null) {
                    assignmentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AssignmentDAO");
            }
        }
    }
}
