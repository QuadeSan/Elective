package DataBaseLayer.TempDB;

import DataBaseLayer.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserInMemory {
    private static UserInMemory instance;
    private List<User> userTable;

    private UserInMemory() {
        userTable = new ArrayList<>();
    }

    public static synchronized UserInMemory getInstance() {
        if (instance == null) {
            instance = new UserInMemory();
        }
        return instance;
    }

    public void addUser(User user) {
        userTable.add(user);
    }

    public User findUser(int id) {
        for (User user :
                userTable) {
            if (user.getUserID() == id) {
                return user;
            }
        }
        return null;
    }

    public User findUser(String login) {
        for (User user :
                userTable) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public void deleteUser(User user) {
        userTable.remove(user);
    }

    public List<User> getUserTable() {
        return userTable;
    }
}
