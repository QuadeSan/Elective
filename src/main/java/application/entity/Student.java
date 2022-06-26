package application.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student implements Serializable {
    private int studentID;
    private int userID;
    private String login;
    private String password;
    private String email;
    private String name;
    private String lastName;
    private Iterable<Course> courses;
    private String status;
    private int markForCurrentCourse;
    private static int k;

    public Student() {
        this.studentID = k;
        k++;
        courses = new ArrayList<>();
    }

    public void setCourses(Iterable<Course> courses) {
        this.courses = courses;
    }

    public int getUserID() {
        return userID;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public Iterable<Course> getCourses() {
        return courses;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMarkForCurrentCourse(int markForCurrentCourse) {
        this.markForCurrentCourse = markForCurrentCourse;
    }

    public int getMarkForCurrentCourse() {
        return markForCurrentCourse;
    }

    public boolean isLocked() {
        return status.equals("locked");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return userID == student.userID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }
}
