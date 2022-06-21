package listeners;

import dataBaseLayer.dao.DAOFactory;
import dataBaseLayer.dao.impl.MySQLDAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

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
    }
}
