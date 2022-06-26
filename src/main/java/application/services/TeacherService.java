package application.services;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Teacher;

public interface TeacherService {

    OperationResult createTeacher(String login, String password, String email, String name, String lastName);

    ValuedOperationResult<Teacher> findTeacher(String login, String password);

    ValuedOperationResult<Teacher> findTeacher(String login);

    ValuedOperationResult<Teacher> findTeacher(int teacherId);

    ValuedOperationResult<Iterable<Teacher>> showAllTeachers();

    OperationResult deleteAccount(int userId);
}
