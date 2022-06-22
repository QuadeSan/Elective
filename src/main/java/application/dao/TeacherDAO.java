package application.dao;

import application.entity.Teacher;
import application.entity.User;

import java.util.List;

public interface TeacherDAO extends AutoCloseable {

    void createTeacher(String login, String password, String email, String name, String lastName) throws AlreadyExistException;

    Teacher findTeacher(int userId) throws NotExistException;

    Teacher findTeacher(String login) throws NotExistException;

    Teacher findTeacher(String login, String password) throws NotExistException;

    void deleteAccount(int userId);

    Iterable<Teacher> showAllTeachers();

    void changeLogin(int userId, String newLogin);

    void changePassword(int userId, String newPassword);
}
