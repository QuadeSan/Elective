package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Course;
import application.entity.Student;
import application.services.AssignmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public OperationResult changeTeacherAssignment(int courseId, int newTeacherId) {
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            assignmentDAO.changeTeacherAssignment(courseId, newTeacherId);
            return new OperationResult(true, "New teacher was assigned to course");
        } catch (NotExistException e) {
            logger.error("Teacher with id " + newTeacherId + " does not exist");
            return new OperationResult(false, "Teacher with ID = " + newTeacherId + " does not exist");
        } catch (DAOException e) {
            logger.error("Can't cancel assignment of teacher "
                    + "from course " + courseId, e);
            return new OperationResult(false, "Unhandled exception");
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
            return new OperationResult(true, "You joined course # " + courseId);
        } catch (AlreadyExistException ex) {
            logger.error("Assignment wasn't built");
            return new OperationResult(false, "You are already enrolled in the course");
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
    public OperationResult unassignStudentFromCourse(int courseId, int studentId) {
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            assignmentDAO.unassignStudentFromCourse(courseId, studentId);
            logger.info("Student " + studentId + " left the course # " + courseId);
            return new OperationResult(true, "You left the course");
        } catch (DAOException e) {
            logger.error("Can't cancel assignment of Student "
                    + studentId + "from course " + courseId, e);
            return new OperationResult(false, "Unhandled exception");
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
    public ValuedOperationResult<Iterable<Course>> showTeacherCourses(int teacherId) {
        Iterable<Course> result;
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");
            result = assignmentDAO.showTeacherCourses(teacherId);
            logger.debug("showTeacherCourses Method used");

            return new ValuedOperationResult<>
                    (true, "List of courses of teacher " + teacherId, result);
        } catch (DAOException e) {
            logger.error("Can't show all courses", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
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
    public ValuedOperationResult<Iterable<Course>> showStudentCourses(int studentId) {
        Iterable<Course> result;
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            result = assignmentDAO.showStudentCourses(studentId);
            logger.debug("showStudentCourses Method used");

            return new ValuedOperationResult<>
                    (true, "List of courses of student " + studentId, result);
        } catch (DAOException e) {
            logger.error("Can't show all courses", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
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
    public ValuedOperationResult<Iterable<Student>> showJournal(int courseId) {
        Iterable<Student> result;
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            result = assignmentDAO.showStudentsOnCourse(courseId);
            logger.debug("showStudentsOnCourses Method used");

            return new ValuedOperationResult<>
                    (true, "List of students on course # " + courseId, result);
        } catch (DAOException e) {
            logger.error("Can't show all students", e);
            return new ValuedOperationResult<>(false, "Unhandled exception", null);
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
    public OperationResult setMarkForStudent(int courseID, int studentId, int mark) {
        AssignmentDAO assignmentDAO = null;
        try {
            assignmentDAO = daoFactory.getAssignmentDAO();
            logger.debug("AssignmentDAO created");

            assignmentDAO.setMarkForStudent(courseID, studentId, mark);
            logger.debug("setMarkForStudent Method used");
            return new OperationResult(true,
                    "Student # " + studentId + " got mark " + mark + " for course # " + courseID);
        } catch (DAOException e) {
            logger.error("Can't set mark for current student", e);
            return new OperationResult(false, "Unhandled exception");
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
