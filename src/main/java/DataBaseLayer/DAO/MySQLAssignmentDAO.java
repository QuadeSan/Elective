package DataBaseLayer.DAO;

import DataBaseLayer.AssignmentException;
import DataBaseLayer.DAOException;
import DataBaseLayer.DataSourcePool;
import DataBaseLayer.entity.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLAssignmentDAO implements AssignmentDAO {

    private static final Logger logger = LogManager.getLogger(MySQLAssignmentDAO.class);

    private Connection con = DataSourcePool.getConnection();


    @Override
    public void assignTeacherToCourse(int courseID, int teacherID) throws AssignmentException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO teachers_assignments VALUES (?,?)");
            int k = 1;
            stmt.setInt(k++, courseID);
            stmt.setInt(k++, teacherID);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Assignment failed, no rows affected.");
            }
            logger.info("Teacher was assigned to a course");
        } catch (SQLException ex) {
            logger.error("Can't assign teacher to course");
            if (ex instanceof java.sql.SQLIntegrityConstraintViolationException) {
                logger.debug("AssignmentException catch clause " + ex);
                throw new AssignmentException(ex);
            }
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void unassignTeacherFromCourse(int courseID, int teacherID) {

    }

    @Override
    public void assignStudentToCourse(int courseID, int studentID) throws AssignmentException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO students_assignments VALUES (?,?,NULL)");
            int k = 1;
            stmt.setInt(k++, courseID);
            stmt.setInt(k++, studentID);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Assignment failed, no rows affected.");
            }
            logger.info("Student was assigned to a course");
        } catch (SQLException ex) {
            logger.error("Can't assign student to course");
            if (ex instanceof java.sql.SQLIntegrityConstraintViolationException) {
                logger.debug("AssignmentException catch clause " + ex);
                throw new AssignmentException(ex);
            }
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void unassignStudentFromCourse(int courseID, int studentID) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM students_assignments " +
                    "WHERE courses_course_id=? AND students_student_id=?");
            int k = 1;
            stmt.setInt(k++, courseID);
            stmt.setInt(k++, studentID);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Assignment cancellation failed, no rows affected.");
            }
            logger.info("Student was unassigned from course");
        } catch (SQLException ex) {
            logger.error("Can't cancel assignment of student from course");
            if (ex instanceof java.sql.SQLIntegrityConstraintViolationException) {
                logger.debug("AssignmentException catch clause " + ex);
                throw new AssignmentException(ex);
            }
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void setMarkForStudent(int courseID, int studentID, int mark) {

    }

    @Override
    public Course getFullInfoAboutCourse(int courseID) {
        return null;
    }

    @Override
    public void close() throws DAOException {
        if (con != null) {
            try {
                con.close();
                logger.debug("connection was closed");
            } catch (SQLException ex) {
                logger.debug("Can't close connection", ex);
                throw new DAOException(ex);
            }
        } else {
            throw new DAOException("Connection was not created");
        }
    }


    private void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logger.debug("Can't close" + closeable);
                throw new DAOException(e);
            }
        }
    }
}
