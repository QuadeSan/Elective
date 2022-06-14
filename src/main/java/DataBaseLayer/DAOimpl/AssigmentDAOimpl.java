package DataBaseLayer.DAOimpl;

import DataBaseLayer.DAO.AssignmentDAO;
import DataBaseLayer.entity.Course;

public class AssigmentDAOimpl implements AssignmentDAO {

    @Override
    public void close() throws Exception {

    }

    @Override
    public void assignTeacherToCourse(int courseID, int teacherID) {

    }

    @Override
    public void unassignTeacherFromCourse(int courseID, int teacherID) {

    }

    @Override
    public void assignStudentToCourse(int courseID, int studentID) {

    }

    @Override
    public void unassignStudentFromCourse(int courseID, int studentID) {

    }

    @Override
    public void setMarkForStudent(int courseID, int studentID, int mark) {

    }

    @Override
    public Course getFullInfoAboutCourse(int courseID) {
        return null;
    }
}
