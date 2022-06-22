package data.dao.impl;

import application.dao.AlreadyExistException;
import application.dao.AssignmentDAO;
import application.dao.DAOException;
import application.entity.Course;
import application.entity.Student;
import data.DataSourcePool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MySQLAssignmentDAO implements AssignmentDAO {

    private static final Logger logger = LogManager.getLogger(MySQLAssignmentDAO.class);

    private final Connection con = DataSourcePool.getConnection();


    @Override
    public void assignTeacherToCourse(int courseID, int teacherID) throws AlreadyExistException {
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
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            logger.debug("AlreadyExistException catch clause " + ex);
            throw new AlreadyExistException(ex);
        } catch (SQLException ex) {
            logger.error("Can't assign teacher to course");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void unassignTeacherFromCourse(int courseID, int teacherID) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM teachers_assignments " +
                    "WHERE courses_course_id=? AND teachers_teacher_id=?");
            int k = 1;
            stmt.setInt(k++, courseID);
            stmt.setInt(k++, teacherID);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Assignment cancellation failed, no rows affected.");
            }
            logger.info("Teacher was unassigned from course");
        } catch (SQLException ex) {
            logger.error("Can't cancel assignment of teacher from course",ex);
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void assignStudentToCourse(int courseID, int studentID) throws AlreadyExistException {
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
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            logger.debug("AlreadyExistException catch clause " + ex);
            throw new AlreadyExistException(ex);
        } catch (SQLException ex) {
            logger.error("Can't assign student to course");
            throw new DAOException("Can't assign student", ex);
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
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public Iterable<Course> showTeacherCourses(int teacherID) {
        Collection<Course> courses = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM courses " +
                    "JOIN teachers_assignments ON teachers_assignments.courses_course_id = courses.course_id " +
                    "WHERE teachers_teacher_id = ?;");
            int k = 1;
            stmt.setInt(k++, teacherID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Course currentCourse = new Course();
                currentCourse.setCourseID(rs.getInt("course_id"));
                currentCourse.setTopic(rs.getString("topic"));
                currentCourse.setTitle(rs.getString("title"));
                currentCourse.setStatus(rs.getString("status"));
                courses.add(currentCourse);
            }
            logger.debug("List of courses of teacher with ID " + teacherID);
            return courses;
        } catch (SQLException ex) {
            logger.debug("Can't execute showTeacherCourses query");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public Iterable<Course> showStudentCourses(int studentID) {
        Collection<Course> courses = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM courses " +
                    "JOIN students_assignments ON students_assignments.courses_course_id = courses.course_id " +
                    "WHERE students_student_id = ?;");
            int k = 1;
            stmt.setInt(k++, studentID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Course currentCourse = new Course();
                currentCourse.setCourseID(rs.getInt("course_id"));
                currentCourse.setTopic(rs.getString("topic"));
                currentCourse.setTitle(rs.getString("title"));
                currentCourse.setStatus(rs.getString("status"));
                currentCourse.setMark(rs.getInt("mark"));
                courses.add(currentCourse);
            }
            logger.debug("List of courses of student with ID " + studentID);
            return courses;
        } catch (SQLException ex) {
            logger.debug("Can't execute showStudentCourses query");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public Iterable<Student> showStudentsOnCourse(int courseID) {
        Collection<Student> students = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT student_id, name, last_name, mark FROM courses " +
                    "RIGHT JOIN students_assignments ON courses.course_id = students_assignments.courses_course_id " +
                    "LEFT JOIN students ON  students_assignments.students_student_id = students.student_id " +
                    "WHERE course_id = ?");
            int k = 1;
            stmt.setInt(k++, courseID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Student currentStudent = new Student();
                currentStudent.setStudentID(rs.getInt("student_id"));
                currentStudent.setName(rs.getString("name"));
                currentStudent.setLastName(rs.getString("last_name"));
                currentStudent.setMarkForCurrentCourse(rs.getInt("mark"));
                students.add(currentStudent);
            }
            logger.debug("List of students on course with ID " + courseID);
            return students;
        } catch (SQLException ex) {
            logger.debug("Can't execute showStudentsOnCourse query");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public void setMarkForStudent(int courseID, int studentID, int mark) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE students_assignments SET mark=? " +
                    "WHERE courses_course_id=? AND students_student_id=?;");
            int k = 1;
            stmt.setInt(k++, mark);
            stmt.setInt(k++, courseID);
            stmt.setInt(k++, studentID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute set mark query");
            throw new DAOException(ex);
        } finally {
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
