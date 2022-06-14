package DataBaseLayer.DAO;

import DataBaseLayer.DAOException;
import DataBaseLayer.DataSourcePool;
import DataBaseLayer.entity.Student;
import DataBaseLayer.entity.Teacher;
import DataBaseLayer.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class MySQLTeacherDAO implements TeacherDAO {

    private static final Logger logger = LogManager.getLogger(MySQLTeacherDAO.class);

    private Connection con = DataSourcePool.getConnection();

    @Override
    public void createTeacher(String login, String password, String email) {
        User newUser = new User();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;
        try {
            con.setAutoCommit(false);
            stmt = con.prepareStatement("INSERT INTO users VALUES (DEFAULT,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            stmt.setString(k++, login);
            stmt.setString(k++, password);
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
            stmt2 = con.prepareStatement("INSERT INTO teachers VALUES (DEFAULT, ?, null,null)");
            stmt2.setInt(1, newUser.getUserID());
            stmt2.executeUpdate();
            con.commit();
            logger.info("Teacher with empty fields created");
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

    @Override
    public Teacher findTeacher(String login) {
        Teacher currentTeacher = new Teacher();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT teachers.teacher_id, teachers.user_id, teachers.name, teachers.last_name FROM teachers " +
                    "RIGHT JOIN users ON users.user_id=teachers.user_id WHERE users.login =?");
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
//              throw new DAOException("Can't find teacher with Login" + login);
            } else {
                currentTeacher.setTeacherID(rs.getInt("teacher_id"));
                currentTeacher.setUserID(rs.getInt("user_id"));
                currentTeacher.setName(rs.getString("name"));
                currentTeacher.setLastName(rs.getString("last_name"));
                return currentTeacher;
            }
        } catch (SQLException ex) {
            logger.error("can't find teacher because of ", ex);
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public Teacher findTeacher(String login, String password) {
        Teacher currentTeacher = new Teacher();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT teachers.teacher_id, teachers.user_id, teachers.name, teachers.last_name, " +
                    "users.login, users.password, users.email FROM teachers " +
                    "JOIN users ON users.user_id=teachers.user_id WHERE users.login =? AND users.password =?");
            int k = 1;
            stmt.setString(k++, login);
            stmt.setString(k++,password);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
//              throw new DAOException("Can't find teacher with Login " + login + " and password " + password);
            } else {
                currentTeacher.setTeacherID(rs.getInt("teacher_id"));
                currentTeacher.setUserID(rs.getInt("user_id"));
                currentTeacher.setName(rs.getString("name"));
                currentTeacher.setLastName(rs.getString("last_name"));
                currentTeacher.setLogin(rs.getString("login"));
                currentTeacher.setPassword(rs.getString("password"));
                currentTeacher.setEmail(rs.getString("email"));
                return currentTeacher;
            }
        } catch (SQLException ex) {
            logger.error("can't find teacher because of ", ex);
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public Teacher findTeacher(int user_id) {
        Teacher currentTeacher = new Teacher();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT teachers.teacher_id, teachers.user_id, teachers.name, teachers.last_name FROM teachers " +
                    "RIGHT JOIN users ON users.user_id=teachers.user_id WHERE users.id =?");
            stmt.setInt(1, user_id);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
//              throw new DAOException("Can't find teacher with ID" + user_id);
            } else {
                currentTeacher.setTeacherID(rs.getInt("teacher_id"));
                currentTeacher.setUserID(rs.getInt("user_id"));
                currentTeacher.setName(rs.getString("name"));
                currentTeacher.setLastName(rs.getString("last_name"));
                return currentTeacher;
            }
        } catch (SQLException ex) {
            logger.error("can't find teacher because of ", ex);
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public void editTeacher(int id, String... params) {

    }

    @Override
    public void deleteAccount(int user_id) {

    }

    @Override
    public List<Teacher> showAll() {
        return null;
    }

    @Override
    public Teacher findTeacher(Teacher teacher) {
        return null;
    }


    @Override
    public void changeLogin(User user, String newLogin) {

    }

    @Override
    public void changePassword(User user, String newPassword) {

    }

    @Override
    public List<User> showAllUsers() {
        return null;
    }

    @Override
    public void close() throws Exception {
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
