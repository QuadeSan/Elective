package DataBaseLayer.TempDB;

import DataBaseLayer.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentInMemory {
    private static StudentInMemory instance;
    private List<Student> studentTable;

    private StudentInMemory() {
        studentTable = new ArrayList<>();
    }

    public static synchronized StudentInMemory getInstance() {
        if (instance == null) {
            instance = new StudentInMemory();
        }
        return instance;
    }

    public void addStudent(Student student) {
        studentTable.add(student);
    }

    public void deleteStudent(Student student) {
        studentTable.remove(student);
    }

    public Student findStudent(int id) {
        for (Student student :
                studentTable) {
            if (student.getStudentID() == id) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getStudentTable() {
        return studentTable;
    }
}
