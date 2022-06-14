package DataBaseLayer.TempDB;

import DataBaseLayer.entity.Administrator;

import java.util.ArrayList;
import java.util.List;

public class AdministratorInMemory {
    private static AdministratorInMemory instance;
    private List<Administrator> adminTable;

    private AdministratorInMemory() {
        adminTable = new ArrayList<>();
    }

    public static synchronized AdministratorInMemory getInstance() {
        if (instance == null) {
            instance = new AdministratorInMemory();
        }
        return instance;
    }

    public void addAdministrator(Administrator administrator) {
        adminTable.add(administrator);
    }

    public Administrator findTeacher(int id) {
        for (Administrator administrator :
                adminTable) {
            if (administrator.getAdministratorID() == id) {
                return administrator;
            }
        }
        return null;
    }

    public void deleteAdministrator(Administrator administrator) {
        adminTable.remove(administrator);
    }

    public List<Administrator> getAdminTable() {
        return adminTable;
    }
}
