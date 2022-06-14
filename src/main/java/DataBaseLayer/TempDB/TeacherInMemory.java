package DataBaseLayer.TempDB;

import DataBaseLayer.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherInMemory {
    private static TeacherInMemory instance;
    private List<Teacher> teacherTable;

    private TeacherInMemory() {
        teacherTable = new ArrayList<>();
    }

    public static synchronized TeacherInMemory getInstance() {
        if (instance == null) {
            instance = new TeacherInMemory();
        }
        return instance;
    }

    public void addTeacher(Teacher teacher) {
        teacherTable.add(teacher);
    }

    public Teacher findTeacher(int id) {
        for (Teacher teacher :
                teacherTable) {
            if (teacher.getTeacherID() == id) {
                return teacher;
            }
        }
        return null;
    }

    public void deleteTeacher(Teacher teacher) {
        teacherTable.remove(teacher);
    }

    public List<Teacher> getTeacherTable() {
        return teacherTable;
    }
}
