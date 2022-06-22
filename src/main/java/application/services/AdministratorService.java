package application.services;

import application.OperationResult;
import application.entity.Administrator;

public interface AdministratorService {

    OperationResult createAdministrator(String login, String password, String email);

    Administrator findAdministrator(int adminId);

    Administrator findAdministrator(String login);

    Administrator findAdministrator(String login, String password);

}
