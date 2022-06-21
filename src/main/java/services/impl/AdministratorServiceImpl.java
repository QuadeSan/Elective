package services.impl;

import controller.Response;
import dataBaseLayer.AlreadyExistException;
import dataBaseLayer.DAOException;
import dataBaseLayer.dao.AdministratorDAO;
import dataBaseLayer.dao.DAOFactory;
import dataBaseLayer.entity.Administrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.AdministartorService;

public class AdministratorServiceImpl implements AdministartorService {

    private static final Logger logger = LogManager.getLogger(AdministratorServiceImpl.class);

    private final DAOFactory daoFactory;

    public AdministratorServiceImpl() {
        this(DAOFactory.getInstance());
    }

    public AdministratorServiceImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        logger.debug("DAOFactory created => " + daoFactory);
    }

    @Override
    public Response createAdministrator(String login, String password, String email) {
        AdministratorDAO administratorDAO = null;
        try {
            administratorDAO = daoFactory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");

            administratorDAO.createAdministrator(login, password, email);
            logger.debug("CreateAdministrator Method used");

            return new Response(true, "Account was successfully created!");
        } catch (AlreadyExistException ex) {
            logger.error("Login already exist");
            return new Response(false, "Login already exist");
        } catch (DAOException e) {
            logger.error("Can't create new Administrator", e);
            return new Response(false, "Something went wrong! Have no response from database");
        } finally {
            try {
                if (administratorDAO != null) {
                    administratorDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AdministratorDAO");
            }
        }
    }

    @Override
    public Administrator findAdministrator(int adminId) {
        Administrator currentAdmin;
        AdministratorDAO administratorDAO = null;
        try {
            administratorDAO = daoFactory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");

            currentAdmin = administratorDAO.findAdministrator(adminId);

            return currentAdmin;
        } catch (DAOException e) {
            logger.error("Can't find Admin by id", e);
            return new Administrator();
        } finally {
            try {
                if (administratorDAO != null) {
                    administratorDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AdministratorDAO");
            }
        }
    }

    @Override
    public Administrator findAdministrator(String login) {
        Administrator currentAdmin;
        AdministratorDAO administratorDAO = null;
        try {
            administratorDAO = daoFactory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");

            currentAdmin = administratorDAO.findAdministrator(login);

            return currentAdmin;
        } catch (DAOException e) {
            logger.error("Can't find Admin by login", e);
            return new Administrator();
        } finally {
            try {
                if (administratorDAO != null) {
                    administratorDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AdministratorDAO");
            }
        }
    }

    @Override
    public Administrator findAdministrator(String login, String password) {
        logger.debug("Start of authorization");
        Administrator currentAdmin;
        AdministratorDAO administratorDAO = null;
        try {
            administratorDAO = daoFactory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");

            currentAdmin = administratorDAO.findAdministrator(login, password);

            return currentAdmin;
        } catch (DAOException e) {
            logger.error("Can't authorize as Admin", e);
            return new Administrator();
        } finally {
            try {
                if (administratorDAO != null) {
                    administratorDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AdministratorDAO");
            }
        }
    }
}
