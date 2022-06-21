package services;

import controller.Response;
import dataBaseLayer.entity.Administrator;

public interface AdministartorService {

    Response createAdministrator(String login, String password, String email);

    Administrator findAdministrator(int adminId);

    Administrator findAdministrator(String login);

    Administrator findAdministrator(String login, String password);

}
