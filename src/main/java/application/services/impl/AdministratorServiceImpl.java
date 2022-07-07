package application.services.impl;

import application.OperationResult;
import application.PasswordHashing;
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
        try (AdministratorDAO administratorDAO = daoFactory.getAdministratorDAO()) {
            logger.debug("AdministratorDAO created");

            String strongPassword = PasswordHashing.createStrongPassword(password);
            logger.debug("Strong password created");

            administratorDAO.createAdministrator(login, strongPassword, email, name, lastName);
            logger.debug("CreateAdministrator Method used");

            return new OperationResult(true, "Account was successfully created!");
        } catch (AlreadyExistException ex) {
            logger.error("Login already exist");
            return new OperationResult(false, "Login already exist");
        } catch (DAOException e) {
            logger.error("Can't create new Administrator", e);
            return new OperationResult(false, "Unhandled exception");
        } catch (Exception e) {
            logger.error("Can't close AdministratorDAO", e);
            return new OperationResult(false, "Unhandled exception");
        }
    }

    @Override
    public ValuedOperationResult<Administrator> authorizeAdministrator(String login, String password) {
        logger.debug("Start of authorization");
        try (AdministratorDAO administratorDAO = daoFactory.getAdministratorDAO()) {

            logger.debug("AdministratorDAO created");

            Administrator currentAdmin = administratorDAO.findAdministrator(login);

            boolean match = PasswordHashing.validatePassword(password,currentAdmin.getPassword());
            if (!match) {
                return new ValuedOperationResult<>(false, "Wrong password", null);
            }
            return new ValuedOperationResult<>(true, "You logged as Administrator", currentAdmin);
        } catch (NotExistException e) {
            logger.error("Can't authorize as administrator");
            return new ValuedOperationResult<>(false,
                    "Administrator with login = " + login + " does not exist", null);
        } catch (DAOException e) {
            logger.error("Unhandled exception", e);
            return new ValuedOperationResult<>(false,
                    "Unhandled exception", null);
        } catch (Exception e) {
            logger.error("Can't close AdministratorDAO", e);
            return new ValuedOperationResult<>(false,
                    "Unhandled exception", null);
        }
    }

    @Override
    public OperationResult deleteAccount(int userId) {
        try (AdministratorDAO  administratorDAO = daoFactory.getAdministratorDAO()){
            logger.debug("AdministratorDAO created");

            administratorDAO.deleteAccount(userId);
            return new OperationResult(true, "Account was deleted");
        } catch (DAOException e) {
            logger.error("Can't delete account", e);
            return new OperationResult(false, "Account was not deleted");
        } catch (Exception e) {
            logger.error("Can't close AdministratorDAO",e);
            return new OperationResult(false, "Unhandled exception");
        }
    }
}
