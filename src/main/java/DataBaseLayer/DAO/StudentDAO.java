package DataBaseLayer.DAO;

import DataBaseLayer.entity.Student;
import DataBaseLayer.entity.User;

import java.util.List;

public interface StudentDAO extends AutoCloseable {

    void createStudent(String login, String password, String email);

    Student findStudent(int id);

    Student findStudent(String login);

    Student findStudent(String login, String password);

    void changeStatus(int student_id, String status);

    List<Student> showAllStudents();

    /////////////////////////////////

    void editStudent(int id, String... params);

    void deleteAccount(int id);

    void changeLogin(User user, String newLogin);

    void changePassword(User user, String newPassword);
}
