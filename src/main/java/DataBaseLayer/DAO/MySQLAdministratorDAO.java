package DataBaseLayer.DAO;

import DataBaseLayer.AlreadyExistException;
import DataBaseLayer.DAOException;
import DataBaseLayer.DataSourcePool;
import DataBaseLayer.entity.Administrator;
import DataBaseLayer.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class MySQLAdministratorDAO implements AdministratorDAO {

    private static final Logger logger = LogManager.getLogger(MySQLAdministratorDAO.class);

    Connection con = DataSourcePool.getConnection();

    @Override
    public void createAdministrator(String login, String pass, String email) {
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
            stmt2 = con.prepareStatement("INSERT INTO administrators VALUES (DEFAULT, ?, null, null)");
            stmt2.setInt(1, newUser.getUserID());
            stmt2.executeUpdate();
            con.commit();
            logger.info("Administrator with empty fields created");
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
                throw new AlreadyExistException("Login already exist",ex);
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
    public Administrator findAdministrator(int admin_id) {
        Administrator currentAdmin = new Administrator();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(
                    "SELECT administrators.administrator_id, administrators.user_id, administrators.name, administrators.last_name FROM students " +
                            "JOIN users ON users.user_id=administrators.user_id WHERE administrators.id =?");
            stmt.setInt(1, admin_id);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
//              throw new DAOException("Can't find administrator with ID" + admin_id);
            } else {
                currentAdmin.setAdministratorID(rs.getInt("administrator_id"));
                currentAdmin.setUserID(rs.getInt("user_id"));
                currentAdmin.setName(rs.getString("name"));
                currentAdmin.setLastName(rs.getString("last_name"));
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
    public Administrator findAdministrator(String login) {
        Administrator currentAdmin = new Administrator();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(
                    "SELECT administrators.administrator_id, administrators.user_id, administrators.name, administrators.last_name FROM students " +
                            "JOIN users ON users.user_id=administrators.user_id WHERE users.login =?");
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
//              throw new DAOException("Can't find administrator with Login" + login);
            } else {
                currentAdmin.setAdministratorID(rs.getInt("administrator_id"));
                currentAdmin.setUserID(rs.getInt("user_id"));
                currentAdmin.setName(rs.getString("name"));
                currentAdmin.setLastName(rs.getString("last_name"));
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
    public Administrator findAdministrator(String login, String password) {
        Administrator currentAdmin = new Administrator();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT administrators.administrator_id, administrators.user_id, administrators.name, administrators.last_name, " +
                    "users.login, users.password, users.email FROM administrators " +
                    "JOIN users ON users.user_id=administrators.user_id WHERE users.login =? AND users.password =?");
            int k = 1;
            stmt.setString(k++, login);
            stmt.setString(k++, password);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
//              throw new DAOException("Can't find administrator with Login = " + login + "and Password = " + password);
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
    public void editAdministrator(int id, String... params) {

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
    public List<Administrator> showAll() {
        return null;
    }

    ///////////////////////////////////////////////


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
    public void close() throws DAOException {
        try {
            con.close();
            logger.debug("connection was closed");
        } catch (SQLException ex) {
            logger.debug("Can't close connection");
            throw new DAOException(ex);
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
