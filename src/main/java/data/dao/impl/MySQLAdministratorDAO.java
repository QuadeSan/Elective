package data.dao.impl;

import data.DataSourcePool;

import application.dao.AdministratorDAO;
import application.dao.AlreadyExistException;
import application.dao.DAOException;
import application.dao.NotExistException;
import application.entity.Administrator;
import application.entity.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class MySQLAdministratorDAO implements AdministratorDAO {

    private static final Logger logger = LogManager.getLogger(MySQLAdministratorDAO.class);

    Connection con = DataSourcePool.getConnection();

    @Override
    public void createAdministrator(String login, String pass, String email, String name, String lastName) throws AlreadyExistException {
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
            stmt2 = con.prepareStatement("INSERT INTO administrators VALUES (DEFAULT, ?, ?, ?)");
            int l = 1;
            stmt2.setInt(l++, newUser.getUserID());
            stmt2.setString(l++, name);
            stmt2.setString(l++, lastName);
            stmt2.executeUpdate();
            con.commit();
            logger.info("Administrator created");
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
                throw new AlreadyExistException("Login already exist", ex);
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
    public Administrator findAdministrator(String login) {
        Administrator currentAdmin = new Administrator();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT administrators.administrator_id, administrators.user_id, administrators.name, administrators.last_name, " +
                    "users.login, users.password, users.email FROM administrators " +
                    "JOIN users ON users.user_id=administrators.user_id WHERE users.login =?");
            int k = 1;
            stmt.setString(k++, login);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.error("Administrator with login " + login + " does not exist");
                throw new NotExistException("Can't find administrator with Login = " + login);
            } else {
                currentAdmin.setAdministratorID(rs.getInt("administrator_id"));
                currentAdmin.setUserID(rs.getInt("user_id"));
                currentAdmin.setName(rs.getString("name"));
                currentAdmin.setLastName(rs.getString("last_name"));
                currentAdmin.setLogin(rs.getString("login"));
                currentAdmin.setPassword(rs.getString("password"));
                currentAdmin.setEmail(rs.getString("email"));
                return currentAdmin;
            }
        } catch (SQLException ex) {
            logger.error("can't find administrator because of ", ex);
            throw new DAOException(ex);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    @Override
    public Administrator findAdministrator(int userId) {
        Administrator currentAdmin = new Administrator();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT administrators.administrator_id, administrators.user_id, administrators.name, administrators.last_name, " +
                    "users.login, users.password, users.email FROM administrators " +
                    "JOIN users ON users.user_id=administrators.user_id WHERE users.user_id =?");
            int k = 1;
            stmt.setInt(k++, userId);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                logger.error("Administrator with ID = " + userId + " does not exist");
                throw new DAOException("Can't find administrator with ID = " + userId);
            } else {
                currentAdmin.setAdministratorID(rs.getInt("administrator_id"));
                currentAdmin.setUserID(rs.getInt("user_id"));
                currentAdmin.setName(rs.getString("name"));
                currentAdmin.setLastName(rs.getString("last_name"));
                currentAdmin.setLogin(rs.getString("login"));
                currentAdmin.setPassword(rs.getString("password"));
                currentAdmin.setEmail(rs.getString("email"));
                return currentAdmin;
            }
        } catch (SQLException ex) {
            logger.error("can't find administrator because of ", ex);
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
            stmt = con.prepareStatement("DELETE FROM users WHERE user_id=?");
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
            stmt = con.prepareStatement("UPDATE administrators " +
                    "JOIN users ON administrators.user_id = users.user_id SET name=? WHERE users.user_id=?");
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
            stmt = con.prepareStatement("UPDATE administrators " +
                    "JOIN users ON administrators.user_id = users.user_id SET last_name=? WHERE users.user_id=?");
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
    public void close() throws DAOException {
        try {
            con.close();
            logger.debug("connection was closed");
        } catch (SQLException ex) {
            logger.error("Can't close connection");
            throw new DAOException(ex);
        }
        logger.debug("AdministratorDAO was closed");
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
                logger.error("Can't close " + closeable);
                throw new DAOException(e);
            }
        }
    }
}
