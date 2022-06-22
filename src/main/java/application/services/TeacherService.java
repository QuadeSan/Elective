package application.services;

import application.OperationResult;
import application.entity.Teacher;

public interface TeacherService {

    OperationResult createTeacher(String login, String password, String email, String name, String lastName);

    Teacher findTeacher(int teacherId);

    Teacher findTeacher(String login);

    Teacher findTeacher(String login, String password);

    void deleteAccount(int userId);
}
