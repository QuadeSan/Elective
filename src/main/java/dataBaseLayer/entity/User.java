package dataBaseLayer.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int userID;
    private String login;
    private String password;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private static int k;


    public User() {
        this.userID = k;
        k++;
    }

    public User(String login, String password) {
        this.userID = k;
        k++;
        this.login = login;
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public int getUserID() {
        return userID;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID && Objects.equals(login, user.login);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", login='" + login + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, login);
    }
}
