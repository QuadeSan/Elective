package DataBaseLayer.DAOimpl;

import DataBaseLayer.DAO.TeacherDAO;
import DataBaseLayer.TempDB.TeacherInMemory;
import DataBaseLayer.TempDB.UserInMemory;
import DataBaseLayer.entity.Teacher;
import DataBaseLayer.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TeacherDAOImpl implements TeacherDAO {

    private static final Logger logger = LogManager.getLogger(TeacherDAOImpl.class);

    @Override
    public void createTeacher(String login, String password, String email, String name, String lastName) {

    }

    @Override
    public Teacher findTeacher(int user_id) {
        return null;
    }

    @Override
    public Teacher findTeacher(String login) {
        return null;
    }

    @Override
    public Teacher findTeacher(String login, String password) {
        return null;
    }

    @Override
    public void deleteAccount(int id) {
        TeacherInMemory teachers = TeacherInMemory.getInstance();
        Teacher teacher = teachers.findTeacher(id);
        teachers.deleteTeacher(teacher);
    }

    @Override
    public List<Teacher> showAll() {
        TeacherInMemory teachers = TeacherInMemory.getInstance();
        return teachers.getTeacherTable();
    }

    @Override
    public Teacher findTeacher(Teacher teacher) {
        TeacherInMemory teachers = TeacherInMemory.getInstance();
        return teachers.findTeacher(teacher.getTeacherID());
    }


    @Override
    public void editTeacher(int id, String... params) {

    }

    ///////////////////////////////////

//    @Override
//    public void createUser(String login, String password) {
//        User newUser = new User(login, password);
//        UserInMemory users = UserInMemory.getInstance();
//        users.addUser(newUser);
//    }
//
//    @Override
//    public User findUser(int id) {
//        UserInMemory users = UserInMemory.getInstance();
//        return users.findUser(id);
//    }
//
//    @Override
//    public User findUser(String login) {
//        UserInMemory users = UserInMemory.getInstance();
//        return users.findUser(login);
//    }
//
//    @Override
//    public void deleteUser(int id) {
//        UserInMemory users = UserInMemory.getInstance();
//        User currentUser = users.findUser(id);
//        if (currentUser != null) {
//            users.deleteUser(currentUser);
//        }
//    }

    @Override
    public void changeLogin(User user, String newLogin) {
        user.setLogin(newLogin);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }

    @Override
    public List<User> showAllUsers() {
        UserInMemory users = UserInMemory.getInstance();
        return users.getUserTable();
    }

    @Override
    public void close() throws Exception {

    }
}
