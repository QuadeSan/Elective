package DataBaseLayer.DAOimpl;

import DataBaseLayer.DAO.CourseDAO;
import DataBaseLayer.TempDB.CourseInMemory;
import DataBaseLayer.entity.Course;

import java.util.List;

abstract public class CourseDAOimpl implements CourseDAO {


    @Override
    public void createCourse(String topic,String name) {
        CourseInMemory courses = CourseInMemory.getInstance();
        Course newCourse = new Course();
        newCourse.setTitle(name);
        courses.addCourse(newCourse);
    }

    @Override
    public Course findCourse(int course_id) {
        return null;
    }

    @Override
    public Course findCourse(String title) {
        return null;
    }

    @Override
    public void deleteCourse(int id) {
        CourseInMemory courses = CourseInMemory.getInstance();
        Course forDelete = courses.findCourse(id);
        courses.removeCourse(forDelete);
    }

    @Override
    public List<Course> showAllCourses() {
        return null;
    }


    @Override
    public void changeStatus(int id, String status) {

    }

    @Override
    public void close() throws Exception {

    }
}
