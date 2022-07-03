package data.dao.impl;

import application.dao.AlreadyExistException;
import application.dao.CourseDAO;
import application.dao.DAOException;
import application.dao.NotExistException;
import application.entity.Course;
import data.DataSourcePool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySQLCourseDAO implements CourseDAO {

    private static final Logger logger = LogManager.getLogger(MySQLCourseDAO.class);

    private final Connection con = DataSourcePool.getConnection();

    @Override
    public void createCourse(String topic, String title) throws AlreadyExistException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO courses VALUES (DEFAULT,?,?,DEFAULT)");
            int k = 1;
            stmt.setString(k++, topic);
            stmt.setString(k++, title);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Creating course failed");
                throw new SQLException("Creating course failed, no rows affected.");
            }
            logger.info("Course was created");
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            logger.debug("AlreadyExistException catch clause " + ex);
            throw new AlreadyExistException("Title already exist", ex);
        } catch (SQLException ex) {
            logger.error("Can't create course", ex);
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public Course findCourse(int courseId) {
        Course currentCourse = new Course();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT course_id, topic, title, status, name, last_name FROM courses " +
                    "LEFT JOIN teachers_assignments ON courses.course_id = teachers_assignments.courses_course_id " +
                    "LEFT JOIN teachers ON teachers_assignments.teachers_teacher_id = teachers.teacher_id " +
                    "WHERE course_id = ?;");
            stmt.setInt(1, courseId);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.debug("Can't find course with id " + courseId);
                throw new NotExistException("Can't find course with id " + courseId);
            } else {
                currentCourse.setCourseID(rs.getInt("course_id"));
                currentCourse.setTopic(rs.getString("topic"));
                currentCourse.setTitle(rs.getString("title"));
                currentCourse.setStatus(rs.getString("status"));
                currentCourse.setAssignedTeacher(
                        rs.getString("name"), rs.getString("last_name"));
                return currentCourse;
            }
        } catch (SQLException ex) {
            logger.debug("Can't execute findCourse by id query", ex);
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public Course findCourse(String title) {
        Course currentCourse = new Course();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM courses WHERE title=?");
            stmt.setString(1, title);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.info("Can't find course with title" + title);
                throw new NotExistException("Can't find course with title " + title);
            } else {
                currentCourse.setCourseID(rs.getInt("course_id"));
                currentCourse.setTopic(rs.getString("topic"));
                currentCourse.setTitle(rs.getString("title"));
                currentCourse.setStatus(rs.getString("status"));
                return currentCourse;
            }
        } catch (SQLException ex) {
            logger.debug("Can't execute findCourse by title query");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public void deleteCourse(int courseId) throws NotExistException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM courses WHERE course_id=?");
            int k = 1;
            stmt.setInt(k++, courseId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Course does not exist");
                throw new NotExistException("Deleting course failed, no rows affected.");
            }
            logger.info("Course was deleted");
        } catch (SQLException ex) {
            logger.error("Can't delete course", ex);
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void changeStatus(int courseId, String status) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE courses SET status=? WHERE course_id=?");
            int k = 1;
            stmt.setString(k++, status);
            stmt.setInt(k++, courseId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Course does not exist");
                throw new NotExistException("Changing status failed, no rows affected.");
            }
        } catch (SQLException ex) {
            logger.debug("Can't execute change status query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void changeTopic(int courseId, String newTopic) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE courses SET topic=? WHERE course_id=?");
            int k = 1;
            stmt.setString(k++, newTopic);
            stmt.setInt(k++, courseId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute change status query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void changeTitle(int courseId, String newTitle) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE courses SET title=? WHERE course_id=?");
            int k = 1;
            stmt.setString(k++, newTitle);
            stmt.setInt(k++, courseId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute change status query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }


    @Override
    public Iterable<Course> showAllCourses() {
        Collection<Course> courses = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT course_id, topic, title, status, name, last_name, " +
                    "COUNT(students_assignments.courses_course_id) AS student_count FROM courses " +
                    "LEFT JOIN teachers_assignments ON courses.course_id = teachers_assignments.courses_course_id " +
                    "LEFT JOIN teachers ON teachers.teacher_id = teachers_assignments.teachers_teacher_id " +
                    "LEFT JOIN students_assignments ON courses.course_id = students_assignments.courses_course_id " +
                    "GROUP BY courses.course_id;");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Course currentCourse = new Course();
                currentCourse.setCourseID(rs.getInt("course_id"));
                currentCourse.setTopic(rs.getString("topic"));
                currentCourse.setTitle(rs.getString("title"));
                currentCourse.setStatus(rs.getString("status"));
                currentCourse.setAssignedTeacher(rs.getString("name"), rs.getString("last_name"));
                currentCourse.setStudentCount(rs.getInt("student_count"));
                courses.add(currentCourse);
            }
            if (courses.isEmpty()) {
                logger.info("There are no courses yet");
            }
            return courses;
        } catch (SQLException ex) {
            logger.debug("Can't execute showAllCourses query");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
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
        logger.debug("CourseDAO was closed");
    }

    /**
     * Method for closing all autocloseable resources
     * like statement, prepared statement, result set
     *
     * @param closeable - resource needed to close
     */
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
