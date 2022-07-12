package presentation;

import application.services.AdministratorService;
import application.services.impl.AdministratorServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * Class used to check if database exist.
 * Method {@link #initializeDataBase(ServletContextEvent) initialization} check if
 * database exist and create a new database with main admin account if not
 */
public class DataBaseInitialize {
    private static final Logger logger = LogManager.getLogger(DataBaseInitialize.class);

    private static final String CHECK_DB = "SELECT COUNT(*) as base FROM information_schema.tables WHERE table_schema = 'test'";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER_NAME);
            logger.debug("Driver " + DRIVER_NAME + " loaded");
        } catch (Exception e) {
            logger.error("Error: " + e);
        }
    }

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "123581321";

    private DataBaseInitialize() {

    }

    public static void initializeDataBase(ServletContextEvent sce) {
        if (!dataBaseExist()) {
            createDataBase(sce);
            logger.debug("Data base was created");
            insertMainAdmin();
            logger.debug("First admin was created");
            return;
        }
        logger.debug("Data base already exist");
    }

    private static void createDataBase(ServletContextEvent sce) {
        String scriptPath = sce.getServletContext().getRealPath("/WEB-INF/classes/create-db.sql");
        String s;
        StringBuilder sb = new StringBuilder();

        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = c.createStatement();
             FileReader fr = new FileReader(scriptPath);
             BufferedReader br = new BufferedReader(fr)) {

            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            br.close();

            String[] queries = sb.toString().split(";");

            for (String query : queries) {
                if (!query.trim().equals("")) {
                    st.executeUpdate(query);
                    logger.debug("DB creation query " + query);
                }
            }
        } catch (SQLException | FileNotFoundException e) {
            logger.error("Can't find sql file or execute query" + e);
        } catch (IOException e) {
            logger.error("Error ", e);
        }
    }

    private static void insertMainAdmin() {
        AdministratorService administratorService = new AdministratorServiceImpl();
        administratorService.createAdministrator("mainadmin", "mainpass", "admin@gmail.com", "Admin", "Mainovsky");
    }

    private static boolean dataBaseExist() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(CHECK_DB);
             ResultSet rs = stmt.executeQuery()) {

            rs.next();
            return rs.getInt("base") > 0;
        } catch (SQLException ex) {
            logger.error("Can't close connection", ex);
            return false;
        }
    }
}
