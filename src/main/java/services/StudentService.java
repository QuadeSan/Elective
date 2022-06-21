package services;

import controller.Response;
import dataBaseLayer.entity.Course;
import dataBaseLayer.entity.Student;

import java.util.List;

public interface StudentService {

    Response createStudent(String login, String password, String email, String name, String lastName);

    Student findStudent(int studentId);

    Student findStudent(String login);

    Student findStudent(String login, String password);

    void deleteAccount(int userId);

    Response lockStudent(int studentId, String status);

    void editStudent(int id, String... params);

    List<Student> showAllStudents();
}
