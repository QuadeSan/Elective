package application.entity;

import java.io.Serializable;
import java.util.Objects;

public class Teacher implements Serializable {
    private int teacherID;
    private int userID;
    private String login;
    private String password;
    private String email;
    private String name;
    private String lastName;
    private Iterable<Course> courses;
    private static int k;

    public Teacher() {
        this.teacherID = k;
        k++;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public void setCourses(Iterable<Course> courses) {
        this.courses = courses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public String getName() {
        return name;
    }

    public Iterable<Course> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return userID == teacher.userID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }
}
