package services.impl;

import dataBaseLayer.AlreadyExistException;
import dataBaseLayer.AssignmentException;
import dataBaseLayer.dao.AssignmentDAO;
import dataBaseLayer.dao.DAOFactory;
import dataBaseLayer.DAOException;
import controller.Response;
import dataBaseLayer.entity.Course;
import dataBaseLayer.entity.Student;
import services.AssignmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

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
    public Response assignTeacherToCourse(int courseId, int teacherId) {
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            assignmentDAO.assignTeacherToCourse(courseId, teacherId);

            logger.info("Teacher was assigned to a course");
            return new Response(true, "Teacher was assigned to course");
        } catch (AssignmentException ex) {
            logger.error("Assignment wasn't built");
            return new Response(false, "Teacher already assigned to this course");
        } finally {
            try {
                if (assignmentDAO != null) {
                    assignmentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO");
            }
        }
    }

    @Override
    public void unassignTeacherFromCourse(int courseId, int teacherId) {

    }

    @Override
    public Response assignStudentToCourse(int courseId, int studentId) {
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            assignmentDAO.assignStudentToCourse(courseId, studentId);

            logger.info("Student " + studentId + " was assigned to course" + courseId);
            return new Response(true, "You was assigned to course " + courseId);
        } catch (AlreadyExistException ex) {
            logger.error("Assignment wasn't built");
            return new Response(false, "You have been already assigned to this course");
        } finally {
            try {
                if (assignmentDAO != null) {
                    assignmentDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close StudentDAO");
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
                logger.error("Can't close StudentDAO");
            }
        }
    }

    @Override
    public List<Course> showTeacherCourses(int teacherId) {
        List<Course> result = new ArrayList<>();
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
                logger.error("Can't close StudentDAO");
            }
        }
    }

    @Override
    public List<Course> showStudentCourses(int studentId) {
        List<Course> result = new ArrayList<>();
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
                logger.error("Can't close StudentDAO");
            }
        }
    }

    @Override
    public List<Student> showStudentsOnCourse(int courseId) {
        List<Student> result = new ArrayList<>();
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
                logger.error("Can't close StudentDAO");
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
                logger.error("Can't close StudentDAO");
            }
        }
    }
}
