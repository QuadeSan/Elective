package application.services;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Administrator;

/**
 * Business logic service, which work with
 * {@link Administrator} entities
 * Main goal is authorization as administrator
 * One main administrator always exist when database created
 */
public interface AdministratorService {

    /**
     * @param login    - login new Administrator
     * @param password - password of new Administrator
     * @param email    - email of new Administrator
     * @param name     - name of new Administrator
     * @param lastName - last name of new Administrator
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method failed
     */
    OperationResult createAdministrator(String login, String password, String email, String name, String lastName);

    /**
     * @param login    - login used during authorization process
     * @param password - password used during authorization process
     * @return {@link ValuedOperationResult} entity which contain
     * success boolean and {@link Administrator} entity as a result
     * to set current user in session on success
     * and high-level error message if method failed
     */
    ValuedOperationResult<Administrator> findAdministrator(String login, String password);

    /**
     * Method used for deleting all users, no matter for current role
     *
     * @param userId - value of userID field of current entity
     * @return {@link OperationResult} entity which contain
     * success boolean and high-level error message if method fails
     */
    OperationResult deleteAccount(int userId);
}
