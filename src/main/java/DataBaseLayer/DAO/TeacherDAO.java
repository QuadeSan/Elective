package DataBaseLayer.DAO;

import DataBaseLayer.entity.Teacher;
import DataBaseLayer.entity.User;

import java.util.List;

public interface TeacherDAO extends AutoCloseable {

    void createTeacher(String login, String password, String email, String name, String lastName);

    Teacher findTeacher(int user_id);

    Teacher findTeacher(String login);

    Teacher findTeacher(String login, String password);

    void editTeacher(int id, String... params);

    void deleteAccount(int user_id);

    List<Teacher> showAll();

    Teacher findTeacher(Teacher teacher);

    ////////////////////////////////////////

    void changeLogin(User user, String newLogin);

    void changePassword(User user, String newPassword);

    List<User> showAllUsers();
}
