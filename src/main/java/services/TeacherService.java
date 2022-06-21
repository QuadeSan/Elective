package services;

import controller.Response;
import dataBaseLayer.entity.Course;
import dataBaseLayer.entity.Student;
import dataBaseLayer.entity.Teacher;

import java.util.List;

public interface TeacherService {

    Response createTeacher(String login, String password, String email, String name, String lastName);

    Teacher findTeacher(int teacherId);

    Teacher findTeacher(String login);

    Teacher findTeacher(String login, String password);

    void deleteAccount(int userId);
}
