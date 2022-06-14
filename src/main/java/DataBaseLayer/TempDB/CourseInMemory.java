package DataBaseLayer.TempDB;

import DataBaseLayer.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseInMemory {
    private static CourseInMemory instance;
    private List<Course> courseTable;

    private CourseInMemory() {
        courseTable = new ArrayList<>();
    }

    public static synchronized CourseInMemory getInstance() {
        if (instance == null) {
            instance = new CourseInMemory();
        }
        return instance;
    }

    public void addCourse(Course course) {
        courseTable.add(course);
    }

    public Course findCourse(int id) {
        for (Course course :
                courseTable) {
            if (course.getCourseID() == id) {
                return course;
            }
        }
        return null;
    }


    public void removeCourse(Course course) {
        courseTable.remove(course);
    }

    public List<Course> getCourseTable() {
        return courseTable;
    }
}
