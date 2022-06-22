package application.dao;

import application.entity.Student;
import application.entity.User;

import java.util.List;

public interface StudentDAO extends AutoCloseable {

    void createStudent(String login, String password, String email, String name, String lastName) throws AlreadyExistException;

    Student findStudent(int id) throws NotExistException;

    Student findStudent(String login) throws NotExistException;

    Student findStudent(String login, String password) throws NotExistException;

    void changeStatus(int studentId, String status) throws NotExistException;

    Iterable<Student> showAllStudents();

    void deleteAccount(int userId);

    void changeLogin(int userId, String newLogin);

    void changePassword(int userId, String newPassword);

}
