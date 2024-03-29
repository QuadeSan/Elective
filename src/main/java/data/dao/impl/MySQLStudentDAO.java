package data.dao.impl;

import application.dao.AlreadyExistException;
import application.dao.DAOException;
import application.dao.NotExistException;
import application.dao.StudentDAO;
import application.entity.Student;
import application.entity.User;
import data.DataSourcePool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MySQLStudentDAO implements StudentDAO {

    private static final Logger logger = LogManager.getLogger(MySQLStudentDAO.class);

    private final Connection con = DataSourcePool.getConnection();

    @Override
    public void createStudent(String login, String pass, String email, String name, String lastName) throws AlreadyExistException {
        User newUser = new User();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
            stmt = con.prepareStatement("INSERT INTO users VALUES (DEFAULT,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            stmt.setString(k++, login);
            stmt.setString(k++, pass);
            stmt.setString(k++, email);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    newUser.setUserID(rs.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            } catch (SQLException ex) {
                logger.debug("Creating user failed");
                throw new DAOException("no ID obtained", ex);
            }
            stmt2 = con.prepareStatement("INSERT INTO students VALUES (DEFAULT, ?, ?, ?,DEFAULT)");
            int l = 1;
            stmt2.setInt(l++, newUser.getUserID());
            stmt2.setString(l++, name);
            stmt2.setString(l++, lastName);
            stmt2.executeUpdate();
            con.commit();
            logger.info("Student created");
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            logger.debug("AlreadyExistException catch clause " + ex);
            throw new AlreadyExistException("Login already exist", ex);
        } catch (SQLException ex) {
            try {
                con.rollback();
                logger.debug("Transaction was rolled back");
            } catch (SQLException e) {
                logger.error("rollback failed");
                throw new DAOException(e);
            }
            logger.error("Can't create user", ex);
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
            close(stmt2);
        }
    }

    public Student findStudent(String login) throws NotExistException {
        Student currentStudent = new Student();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT students.student_id, students.user_id, students.name, students.last_name, students.status, " +
                    "users.login, users.password, users.email FROM students " +
                    "JOIN users ON users.user_id=students.user_id WHERE users.login =?");
            int k = 1;
            stmt.setString(k++, login);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.error("Student with login= " + login + " does not exist");
                throw new NotExistException("Can't find student with Login = " + login);
            } else {
                currentStudent.setStudentID(rs.getInt("student_id"));
                currentStudent.setUserID(rs.getInt("user_id"));
                currentStudent.setName(rs.getString("name"));
                currentStudent.setLastName(rs.getString("last_name"));
                currentStudent.setStatus(rs.getString("status"));
                currentStudent.setLogin(rs.getString("login"));
                currentStudent.setPassword(rs.getString("password"));
                currentStudent.setEmail(rs.getString("email"));
                return currentStudent;
            }
        } catch (SQLException ex) {
            logger.error("can't find student because of ", ex);
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public Student findStudent(int userId) {
        Student currentStudent = new Student();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT students.student_id, students.user_id, students.name, students.last_name, students.status, " +
                    "users.login, users.password, users.email FROM students " +
                    "JOIN users ON users.user_id=students.user_id WHERE users.user_id =?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.error("Can't find student with ID " + userId);
                throw new DAOException("Can't find student with ID " + userId);
            } else {
                currentStudent.setStudentID(rs.getInt("student_id"));
                currentStudent.setUserID(rs.getInt("user_id"));
                currentStudent.setName(rs.getString("name"));
                currentStudent.setLastName(rs.getString("last_name"));
                currentStudent.setStatus(rs.getString("status"));
                currentStudent.setLogin(rs.getString("login"));
                currentStudent.setPassword(rs.getString("password"));
                currentStudent.setEmail(rs.getString("email"));
                return currentStudent;
            }
        } catch (SQLException ex) {
            logger.error("Can't execute findStudent query", ex);
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public Iterable<Student> showAllStudents() {
        Collection<Student> students = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM students");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Student current = new Student();
                current.setStudentID(rs.getInt("student_id"));
                current.setUserID(rs.getInt("user_id"));
                current.setName(rs.getString("name"));
                current.setLastName(rs.getString("last_name"));
                current.setStatus(rs.getString("status"));
                students.add(current);
            }
        } catch (SQLException ex) {
            logger.debug("Can't show all students");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
        return students;
    }

    @Override
    public Iterable<Student> showAllStudents(int offSet, int limit) {
        Collection<Student> students = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM students " +
                    "LEFT JOIN (SELECT AVG(mark) as avg_mark, students_student_id " +
                    "FROM students_assignments " +
                    "GROUP BY students_student_id) AS avg_mark  " +
                    "ON students.student_id = avg_mark.students_student_id " +
                    "LIMIT ? OFFSET ?;");
            int k = 1;
            stmt.setInt(k++, limit);
            stmt.setInt(k++, offSet);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Student current = new Student();
                current.setStudentID(rs.getInt("student_id"));
                current.setUserID(rs.getInt("user_id"));
                current.setName(rs.getString("name"));
                current.setLastName(rs.getString("last_name"));
                current.setStatus(rs.getString("status"));
                current.setAverageMark(rs.getDouble("avg_mark"));
                students.add(current);
            }
        } catch (SQLException ex) {
            logger.debug("Can't show all students");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
        return students;
    }

    @Override
    public int studentCount() {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT COUNT(*) as count FROM students");
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException ex) {
            logger.debug("Can't count all students");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
        return -1;
    }

    @Override
    public void changeStatus(int studentId, String status) throws NotExistException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE students SET status=? WHERE student_id=?");
            int k = 1;
            stmt.setString(k++, status);
            stmt.setInt(k++, studentId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Student does not exist");
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
    public void updateLogin(int userId, String newLogin) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users SET login=? WHERE user_id=?");
            int k = 1;
            stmt.setString(k++, newLogin);
            stmt.setInt(k++, userId);
            stmt.executeUpdate();
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            logger.debug("AlreadyExistException catch clause " + ex);
            throw new AlreadyExistException("Login already exist", ex);
        } catch (SQLException ex) {
            logger.debug("Can't execute update login query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void updateEmail(int userId, String newEmail) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users SET email=? WHERE user_id=?");
            int k = 1;
            stmt.setString(k++, newEmail);
            stmt.setInt(k++, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute update email query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void updatePassword(int userId, String newPassword) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users SET password=? WHERE user_id=?");
            int k = 1;
            stmt.setString(k++, newPassword);
            stmt.setInt(k++, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute change password query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void updateName(int userId, String newName) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE students " +
                    "JOIN users ON students.user_id = users.user_id SET name=? WHERE users.user_id=?");
            int k = 1;
            stmt.setString(k++, newName);
            stmt.setInt(k++, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute change name query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void updateLastName(int userId, String newLastName) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE students " +
                    "JOIN users ON students.user_id = users.user_id SET last_name=? WHERE users.user_id=?");
            int k = 1;
            stmt.setString(k++, newLastName);
            stmt.setInt(k++, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute change last name query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void close() {
        if (con != null) {
            try {
                con.close();
                logger.debug("connection was closed");
            } catch (SQLException ex) {
                logger.debug("Can't close connection", ex);
            }
        }
        logger.debug("StudentDAO was closed");
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
