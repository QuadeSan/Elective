package data;

import application.dao.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection pool configuration
 * {@link #getConnection() Get Connection} is main method
 * used to obtain connection with database
 */
public class DataSourcePool {

    private static final Logger logger = LogManager.getLogger(DataSourcePool.class);

    private static DataSource ds;

    public static Connection getConnection(){
        if(ds == null){
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:comp/env");
                ds = (DataSource) envContext.lookup("jdbc/Elective");
                logger.debug("Data source created: " + ds);
            } catch (NamingException ex){
                logger.debug("Can't create data source");
                throw new DAOException(ex);
            }
        }
        try {
            logger.debug("Connection created");
            return ds.getConnection();
        } catch (SQLException ex){
            logger.debug("Can't create connection");
            throw new DAOException(ex);
        }
    }
}
