package application.services;

import application.ValuedOperationResult;
import application.OperationResult;
import application.entity.Student;

public interface StudentService {

    OperationResult createStudent(String login, String password, String email, String name, String lastName);

    ValuedOperationResult<Student> findStudent(int studentId);

    ValuedOperationResult<Student> findStudent(String login);

    ValuedOperationResult<Student> findStudent(String login, String password);

    OperationResult deleteAccount(int userId);

    OperationResult lockStudent(int studentId, String status);

    ValuedOperationResult<Iterable<Student>> showAllStudents();

    void editStudent(int id, String... params);
}
