package DataBaseLayer.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Journal implements Serializable {
    private int id;
    private Course course;
    private Map<Student, Integer> students;
    private Teacher assignedTeacher;
    private static List<Journal> fullJournal;
    private static int k;

    public Journal() {
        this.id = k;
        k++;
        this.students = new HashMap<>();
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setAssignedTeacher(Teacher assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }

    public void addStudent(Student student) {
        students.put(student, -1);
    }

    public void setMarkForStudent(Student student, int mark) {
        students.put(student, mark);
    }

    public void applyJournal(Journal journal) {
        fullJournal.add(journal);
    }

    public List<Journal> getFullJournal() {
        return fullJournal;
    }


}
