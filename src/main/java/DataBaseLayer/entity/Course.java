package DataBaseLayer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course implements Serializable {
    private int courseID;
    private String title;
    private List<Student> students;
    private String status;
    private Teacher assignedTeacher;
    private static int k;

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Course() {
        this.courseID = k;
        k++;
        this.status = "In progress";
        this.students = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAssignedTeacher(Teacher assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public List<Student> showStudentsOnCourse() {
        return students;
    }

    public Teacher showAssignedTeacher() {
        return assignedTeacher;
    }

    public void changeStatus(String newStatus) {
        this.status = status;
    }

    public int getCourseID() {
        return courseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseID == course.courseID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseID);
    }
}
