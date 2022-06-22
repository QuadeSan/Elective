package data.dao.impl;

import application.dao.AlreadyExistException;
import application.dao.DAOException;
import application.dao.NotExistException;
import application.dao.TeacherDAO;
import application.entity.Teacher;
import application.entity.User;
import data.DataSourcePool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MySQLTeacherDAO implements TeacherDAO {

    private static final Logger logger = LogManager.getLogger(MySQLTeacherDAO.class);

    private final Connection con = DataSourcePool.getConnection();

    @Override
    public void createTeacher(String login, String password, String email, String name, String lastName) throws AlreadyExistException {
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
            stmt2 = con.prepareStatement("INSERT INTO teachers VALUES (DEFAULT, ?, ?,?)");
            int l = 1;
            stmt2.setInt(l++, newUser.getUserID());
            stmt2.setString(l++, name);
            stmt2.setString(l++, lastName);
            stmt2.executeUpdate();
            con.commit();
            logger.info("Teacher with empty fields created");
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

    @Override
    public Teacher findTeacher(String login) throws NotExistException {
        Teacher currentTeacher = new Teacher();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT teachers.teacher_id, teachers.user_id, teachers.name, teachers.last_name FROM teachers " +
                    "RIGHT JOIN users ON users.user_id=teachers.user_id WHERE users.login =?");
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.error("Teacher with login = " + login + " does not exist");
                throw new NotExistException("Can't find teacher with Login" + login);
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
    public Teacher findTeacher(String login, String password) throws NotExistException {
        Teacher currentTeacher = new Teacher();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT teachers.teacher_id, teachers.user_id, teachers.name, teachers.last_name, " +
                    "users.login, users.password, users.email FROM teachers " +
                    "JOIN users ON users.user_id=teachers.user_id WHERE users.login =? AND users.password =?");
            int k = 1;
            stmt.setString(k++, login);
            stmt.setString(k++, password);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.error("Teacher with login = " + login + " does not exist");
                throw new NotExistException("Can't find teacher with login= " + login + " and password= " + password);
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
    public Teacher findTeacher(int userId) throws NotExistException {
        Teacher currentTeacher = new Teacher();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT teachers.teacher_id, teachers.user_id, teachers.name, teachers.last_name FROM teachers " +
                    "RIGHT JOIN users ON users.user_id=teachers.user_id WHERE users.id =?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.error("Teacher with ID " + userId + " does not exist");
                throw new NotExistException("Can't find teacher with ID " + userId);
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
    public void deleteAccount(int userId) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM users WHERE userId=?");
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("can't delete user while deleting account", ex);
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public Iterable<Teacher> showAllTeachers() {
        Collection<Teacher> teachers = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM teachers");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Teacher current = new Teacher();
                current.setTeacherID(rs.getInt("teacher_id"));
                current.setUserID(rs.getInt("user_id"));
                current.setName(rs.getString("name"));
                current.setLastName(rs.getString("last_name"));
                teachers.add(current);
            }
        } catch (SQLException ex) {
            logger.debug("Can't show all teachers");
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
        return teachers;
    }

    @Override
    public void changeLogin(int userId, String newLogin) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users SET login=? WHERE user_id=?");
            int k = 1;
            stmt.setString(k++, newLogin);
            stmt.setInt(k++, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.debug("Can't execute change login query");
            throw new DAOException(ex);
        } finally {
            close(stmt);
        }
    }

    @Override
    public void changePassword(int userId, String newPassword) {
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
