package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Administrator;
import application.services.AdministratorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AdministratorServiceImpl implements AdministratorService {

    private static final Logger logger = LogManager.getLogger(AdministratorServiceImpl.class);

    private final DAOFactory daoFactory;

    public AdministratorServiceImpl() {
        this.daoFactory = DAOFactory.getInstance();
        logger.debug("DAOFactory created => " + daoFactory);
    }

    @Override
    public OperationResult createAdministrator(String login, String password, String email, String name, String lastName) {
        AdministratorDAO administratorDAO = null;
        try {
            administratorDAO = daoFactory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");

            administratorDAO.createAdministrator(login, password, email,name,lastName);
            logger.debug("CreateAdministrator Method used");

            return new OperationResult(true, "Account was successfully created!");
        } catch (AlreadyExistException ex) {
            logger.error("Login already exist");
            return new OperationResult(false, "Login already exist");
        } catch (DAOException e) {
            logger.error("Can't create new Administrator", e);
            return new OperationResult(false, "Something went wrong! Have no response from database");
        } finally {
            try {
                if (administratorDAO != null) {
                    administratorDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AdministratorDAO");
            }
            logger.debug("AdministratorDAO was closed");
        }
    }

    @Override
    public ValuedOperationResult<Administrator> findAdministrator(String login, String password) {
        logger.debug("Start of authorization");
        Administrator currentAdmin;
        AdministratorDAO administratorDAO = null;
        try {
            administratorDAO = daoFactory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");

            currentAdmin = administratorDAO.findAdministrator(login, password);

            return new ValuedOperationResult<>(true, "Administrator found", currentAdmin);
        } catch (NotExistException e) {
            logger.error("Can't authorize as administrator");
            return new ValuedOperationResult<>(false,
                    "Administrator with login = " + login + " does not exist", null);
        } catch (DAOException e) {
            logger.error("Unhandled exception", e);
            return new ValuedOperationResult<>(false,
                    "Unhandled exception", null);
        } finally {
            try {
                if (administratorDAO != null) {
                    administratorDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AdministratorDAO");
            }
            logger.debug("AdministratorDAO was closed");
        }
    }

    @Override
    public OperationResult deleteAccount(int userId) {
        AdministratorDAO administratorDAO = null;
        try {
            administratorDAO = daoFactory.getAdministratorDAO();
            logger.debug("AdministratorDAO created");

            administratorDAO.deleteAccount(userId);
            return new OperationResult(true, "Account was deleted");
        } catch (DAOException e) {
            logger.error("Can't delete account", e);
            return new OperationResult(false, "Account was not deleted");
        } finally {
            try {
                if (administratorDAO != null) {
                    administratorDAO.close();
                }
            } catch (Exception e) {
                logger.error("Can't close AdministratorDAO");
            }
            logger.debug("AdministratorDAO was closed");
        }
    }
}
