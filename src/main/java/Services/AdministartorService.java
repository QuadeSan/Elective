package Services;

import DataBaseLayer.entity.Administrator;
import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Student;
import DataBaseLayer.entity.Teacher;

public interface AdministartorService {

    void createAdministrator(String login, String password, String email);

    Administrator findAdministrator(int admin_id);

    Administrator findAdministrator(String login);

    Administrator findAdministrator(String login, String password);

}
