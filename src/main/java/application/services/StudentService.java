package application.services;

import application.OperationRes;
import application.OperationResult;
import application.entity.Student;

public interface StudentService {

    OperationResult createStudent(String login, String password, String email, String name, String lastName);

    OperationRes<Student> findStudent(int studentId);

    Student findStudent(String login);

    Student findStudent(String login, String password);

    void deleteAccount(int userId);

    OperationResult lockStudent(int studentId, String status);

    void editStudent(int id, String... params);

    Iterable<Student> showAllStudents();
}
