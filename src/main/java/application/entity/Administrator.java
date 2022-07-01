package application.entity;

import java.io.Serializable;
import java.util.Objects;

public class Administrator implements Serializable {
    private int administratorID;
    private int userID;
    private String login;
    private String password;
    private String email;
    private String name;
    private String lastName;
    private static int k;

    public Administrator() {
        this.administratorID = k;
        k++;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdministratorID(int administratorID) {
        this.administratorID = administratorID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdministratorID() {
        return administratorID;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return userID == that.userID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }
}
