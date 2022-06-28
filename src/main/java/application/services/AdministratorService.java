package application.services;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Administrator;

public interface AdministratorService {

    OperationResult createAdministrator(String login, String password, String email);

    ValuedOperationResult<Administrator> findAdministrator(String login, String password);

    ValuedOperationResult<Administrator> findAdministrator(String login);

    ValuedOperationResult<Administrator> findAdministrator(int adminId);

    OperationResult deleteAccount(int userId);
}
