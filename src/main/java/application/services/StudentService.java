package application.services;

import application.ValuedOperationResult;
import application.OperationResult;
import application.entity.Student;

/**
 * Business logic service, which work with
 * {@link Student} entities
 *
 */
public interface StudentService {

    OperationResult createStudent(String login, String password, String email, String name, String lastName);

    ValuedOperationResult<Student> findStudent(String login, String password);

    OperationResult lockStudent(int studentId, String status);

    ValuedOperationResult<Iterable<Student>> showAllStudents();

    void editStudent(int id, String... params);
}
