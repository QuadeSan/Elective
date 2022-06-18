package DataBaseLayer.DAO;

import DataBaseLayer.AlreadyExistException;
import DataBaseLayer.DAOException;
import DataBaseLayer.DataSourcePool;
import DataBaseLayer.NotExistException;
import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Teacher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCourseDAO implements CourseDAO {

    private static final Logger logger = LogManager.getLogger(MySQLCourseDAO.class);

    private Connection con = DataSourcePool.getConnection();

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
        } catch (SQLException ex) {
            if (ex instanceof java.sql.SQLIntegrityConstraintViolationException) {
                logger.debug("AlreadyExistException catch clause " + ex);
                throw new AlreadyExistException("Title already exist",ex);
            }
            logger.error("Can't create course", ex);
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public Course findCourse(int course_id) {
        Course currentCourse = new Course();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM courses WHERE course_id=?");
            stmt.setInt(1, course_id);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.debug("Can't find course with id " + course_id);
                return null;
//                throw new DAOException("Can't find course with id " + course_id);
            } else {
                currentCourse.setCourseID(rs.getInt("course_id"));
                currentCourse.setTitle(rs.getString("title"));
                currentCourse.setStatus(rs.getString("status"));
                return currentCourse;
            }
        } catch (SQLException ex) {
            logger.debug("Can't execute findCourse by id query");
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
                return null;
//                throw new DAOException("Can't find course with title " + title);
            } else {
                currentCourse.setCourseID(rs.getInt("course_id"));
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
    public void deleteCourse(int course_id) throws NotExistException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM courses WHERE course_id=?");
            int k = 1;
            stmt.setInt(k++, course_id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Course does not exist");
                throw new SQLException("Deleting course failed, no rows affected.");
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
    public void changeStatus(int id, String status) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE courses SET status=? WHERE course_id=?");
            int k = 1;
            stmt.setString(k++, status);
            stmt.setInt(k++, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute change status query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }


    @Override
    public List<Course> showAllCourses() {
        List<Course> courses = new ArrayList<>();
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
                currentCourse.setAssignedTeacher(rs.getString("name"),rs.getString("last_name"));
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
