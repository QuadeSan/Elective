package presentation.listeners;

import application.dao.DAOFactory;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import data.dao.impl.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import presentation.DataBaseInitialize;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Listener that sets {@link DAOFactory} implementation
 * {@link Logger} path params and locales property for
 * website internationalization
 */
@WebListener
public class MyContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String path = ctx.getRealPath("/WEB-INF/");
        System.setProperty("logPath", path);

        final Logger logger = LogManager.getLogger(MyContextListener.class);
        logger.debug("path = " + path);

        DAOFactory.setInstance(new MySQLDAOFactory());
        logger.debug("DAOFactory was set to MySQL implementation");

        logger.debug("DataBase initialization");
        DataBaseInitialize.initializeDataBase(sce);

        String localesFileName = ctx.getInitParameter("locales");
        String localesFileRealPath = ctx.getRealPath(localesFileName);

        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(localesFileRealPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.setAttribute("locales", locales);
        logger.debug("Locales list " + locales);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        Driver driver;
        while (drivers.hasMoreElements()) {
            try {
                driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            } catch (SQLException ex) {
                sce.getServletContext().log("Can't deregister Driver", ex);
            }
        }
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
