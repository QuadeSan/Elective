package application.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course implements Serializable {
    private int courseID;
    private String topic;
    private String title;
    private String status;
    private String assignedTeacher;
    private int studentCount;
    private int mark;
    private final List<Student> students;
    private static int k;


    public Course() {
        this.courseID = k;
        k++;
        this.status = "In progress";
        this.students = new ArrayList<>();
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAssignedTeacher(String name, String last_name) {
        if (name != null) {
            this.assignedTeacher = name + " " + last_name;
        } else this.assignedTeacher = null;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getTopic() {
        return topic;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getAssignedTeacher() {
        return assignedTeacher;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public int getMark() {
        return mark;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(title, course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
