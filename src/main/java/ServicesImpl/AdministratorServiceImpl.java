package ServicesImpl;

import DataBaseLayer.DAO.AdministratorDAO;
import DataBaseLayer.DAO.DAOFactory;
import DataBaseLayer.DAOException;
import DataBaseLayer.entity.Administrator;
import Services.AdministartorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdministratorServiceImpl implements AdministartorService {

    private static final Logger logger = LogManager.getLogger(AdministratorServiceImpl.class);

    @Override
    public void createAdministrator(String login, String password, String email) {
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AdministratorDAO administratorDAO = factory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");
            administratorDAO.createAdministrator(login, password, email);
            logger.debug("CreateAdministrator Method used");
            administratorDAO.close();
        } catch (DAOException e) {
            logger.debug("Can't create new Administrator", e);
        } catch (Exception e) {
            logger.debug("Can't close AdministratorDAO", e);
        }
    }

    @Override
    public Administrator findAdministrator(int admin_id) {
        Administrator currentAdmin;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AdministratorDAO administratorDAO = factory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");
            currentAdmin = administratorDAO.findAdministrator(admin_id);
            administratorDAO.close();
            return currentAdmin;
        } catch (DAOException e) {
            logger.error("Can't find Admin by id", e);
        } catch (Exception e) {
            logger.error("Can't close AdministratorDAO", e);
        }
        return null;
    }

    @Override
    public Administrator findAdministrator(String login) {
        Administrator currentAdmin;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AdministratorDAO administratorDAO = factory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");
            currentAdmin = administratorDAO.findAdministrator(login);
            administratorDAO.close();
            return currentAdmin;
        } catch (DAOException e) {
            logger.error("Can't find Admin by login", e);
        } catch (Exception e) {
            logger.error("Can't close AdministratorDAO", e);
        }
        return null;
    }

    @Override
    public Administrator findAdministrator(String login, String password) {
        logger.debug("Start of authorization");
        Administrator currentAdmin;
        try {
            DAOFactory factory = DAOFactory.getInstance();
            logger.debug("DAOFactory created => " + factory);
            AdministratorDAO administratorDAO = factory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");
            currentAdmin = administratorDAO.findAdministrator(login, password);
            administratorDAO.close();
            return currentAdmin;
        } catch (DAOException e) {
            logger.error("Can't authorize as Admin", e);
        } catch (Exception e) {
            logger.error("Can't close AdministratorDAO", e);
        }
        return null;
    }
}
