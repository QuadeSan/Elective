package DataBaseLayer.DAO;

import DataBaseLayer.AlreadyExistException;
import DataBaseLayer.AssignmentException;
import DataBaseLayer.DAOException;
import DataBaseLayer.DataSourcePool;
import DataBaseLayer.entity.Student;
import DataBaseLayer.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLStudentDAO implements StudentDAO {

    private static final Logger logger = LogManager.getLogger(MySQLStudentDAO.class);

    private Connection con = DataSourcePool.getConnection();

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
                throw new DAOException(ex);
            }
            stmt2 = con.prepareStatement("INSERT INTO students VALUES (DEFAULT, ?, ?, ?,DEFAULT)");
            int l = 1;
            stmt2.setInt(l++, newUser.getUserID());
            stmt2.setString(l++, name);
            stmt2.setString(l++, lastName);
            stmt2.executeUpdate();
            con.commit();
            logger.info("Student with empty fields created");
        } catch (SQLException ex) {
            try {
                con.rollback();
                logger.debug("Transaction was rolled back");
            } catch (SQLException e) {
                logger.error("rollback failed");
                throw new DAOException(e);
            }
            if (ex instanceof java.sql.SQLIntegrityConstraintViolationException) {
                logger.debug("AlreadyExistException catch clause " + ex);
                throw new AlreadyExistException(ex);
            }
            logger.error("Can't create user", ex);
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
            close(stmt2);
        }
    }


    @Override
    public Student findStudent(String login) throws AlreadyExistException {
        Student currentStudent = new Student();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT students.student_id, students.user_id, students.name, students.last_name, students.status FROM students " +
                    "RIGHT JOIN users ON users.user_id=students.user_id WHERE users.login =?");
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
//              throw new DAOException("Can't find student with Login" + login);
            } else {
                currentStudent.setStudentID(rs.getInt("student_id"));
                currentStudent.setUserID(rs.getInt("user_id"));
                currentStudent.setName(rs.getString("name"));
                currentStudent.setLastName(rs.getString("last_name"));
                currentStudent.setStatus(rs.getString("status"));
                return currentStudent;
            }
        } catch (SQLException ex) {
            logger.error("can't find student because of ", ex);
            if (ex instanceof java.sql.SQLIntegrityConstraintViolationException) {
                logger.debug("AlreadyExistException catch clause " + ex);
                throw new AlreadyExistException(ex);
            }
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    public Student findStudent(String login, String password) {
        Student currentStudent = new Student();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT students.student_id, students.user_id, students.name, students.last_name, students.status, " +
                    "users.login, users.password, users.email FROM students " +
                    "JOIN users ON users.user_id=students.user_id WHERE users.login =? AND users.password =?");
            int k = 1;
            stmt.setString(k++, login);
            stmt.setString(k++, password);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
//              throw new DAOException("Can't find student with Login = " + login + "and Password = " + password);
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
    public Student findStudent(int student_id) {
        Student currentStudent = new Student();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM students WHERE student_id=?");
            stmt.setInt(1, student_id);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.info("Can't find student with ID " + student_id);
                return null;
//                throw new DAOException("Can't find student with ID " + student_id);
            } else {
                currentStudent.setStudentID(rs.getInt("student_id"));
                currentStudent.setUserID(rs.getInt("user_id"));
                currentStudent.setName(rs.getString("name"));
                currentStudent.setLastName(rs.getString("last_name"));
                currentStudent.setStatus(rs.getString("status"));
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
    public void deleteAccount(int user_id) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM users WHERE user_id=?");
            stmt.setInt(1, user_id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("can't delete user while deleting account", ex);
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public List<Student> showAllStudents() {
        List<Student> res = new ArrayList<>();
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
                current.setStatus(rs.getString("status"));
                res.add(current);
            }
        } catch (SQLException ex) {
            logger.debug("Can't show all students");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
        return res;
    }

    @Override
    public void editStudent(int id, String... params) {

    }


    public User findUser(String login) {
        User currentUser = new User();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM users WHERE login=?");
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new DAOException("Can't find user with login " + login);
            } else {
                currentUser.setUserID(rs.getInt("user_id"));
                currentUser.setLogin(rs.getString("login"));
                currentUser.setPassword(rs.getString("password"));
                currentUser.setEmail(rs.getString("email"));
                return currentUser;
            }
        } catch (SQLException ex) {
            logger.debug("Can't execute findUser by login query");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public void changeStatus(int student_id, String status) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE students SET status=? WHERE student_id=?");
            int k = 1;
            stmt.setString(k++, status);
            stmt.setInt(k++, student_id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute change status query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void changeLogin(User user, String newLogin) {
        User currentUser = findUser(user.getLogin());
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users SET login=? WHERE user_id=?");
            int k = 1;
            stmt.setString(k++, newLogin);
            stmt.setInt(k++, currentUser.getUserID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute change login query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void changePassword(User user, String newPassword) {
        User currentUser = findUser(user.getLogin());
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users SET password=? WHERE user_id=?");
            int k = 1;
            stmt.setString(k++, newPassword);
            stmt.setInt(k++, currentUser.getUserID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute change password query");
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
