package DataBaseLayer.DAOimpl;

import DataBaseLayer.DAO.StudentDAO;
import DataBaseLayer.TempDB.StudentInMemory;
import DataBaseLayer.TempDB.UserInMemory;
import DataBaseLayer.entity.Student;
import DataBaseLayer.entity.User;

import java.util.List;

public class StudentDAOimpl implements StudentDAO {

    private static StudentDAOimpl instance;

    private StudentDAOimpl() {

    }

    public static synchronized StudentDAOimpl getInstance() {
        if (instance == null) {
            instance = new StudentDAOimpl();
        }
        return instance;
    }

    @Override
    public void createStudent(String login, String password, String email) {
        StudentInMemory students = StudentInMemory.getInstance();
        Student student = new Student();
        students.addStudent(student);
    }

    @Override
    public Student findStudent(int id) {
        StudentInMemory students = StudentInMemory.getInstance();
        return students.findStudent(id);
    }

    @Override
    public Student findStudent(String login) {
        return null;
    }

    @Override
    public Student findStudent(String login, String password) {
        return null;
    }

    @Override
    public void changeStatus(int student_id, String status) {

    }

    @Override
    public List<Student> showAllStudents() {
        StudentInMemory students = StudentInMemory.getInstance();
        return students.getStudentTable();
    }

    @Override
    public void editStudent(int id, String... params) {

    }

    //////////////////////////////////


//    @Override
//    public User findUser(int id) {
//        UserInMemory users = UserInMemory.getInstance();
//        return users.findUser(id);
//    }

    @Override
    public void deleteAccount(int id) {
        UserInMemory users = UserInMemory.getInstance();
        User currentUser = users.findUser(id);
        if (currentUser != null) {
            users.deleteUser(currentUser);
        }
    }

    @Override
    public void changeLogin(User user, String newLogin) {
        user.setLogin(newLogin);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }

    @Override
    public void close() throws Exception {

    }
}
